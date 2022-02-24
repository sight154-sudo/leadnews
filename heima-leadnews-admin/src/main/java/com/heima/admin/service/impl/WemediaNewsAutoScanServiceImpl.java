package com.heima.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.heima.admin.feign.ArticleFeign;
import com.heima.admin.feign.WmNewsFeign;
import com.heima.admin.mapper.AdChannelMapper;
import com.heima.admin.mapper.AdSensitiveMapper;
import com.heima.admin.service.WemediaNewsAutoScanService;
import com.heima.common.aliyun.GreenImageScan;
import com.heima.common.aliyun.GreenTextScan;
import com.heima.common.fastdfs.FastDFSClient;
import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.article.pojos.ApArticleConfig;
import com.heima.model.article.pojos.ApArticleContent;
import com.heima.model.article.pojos.ApAuthor;
import com.heima.model.wemedia.enumstatus.WmNewsStatusEnum;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.common.SensitiveWordUtil;
import com.mysql.fabric.HashShardMapping;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.nntp.Article;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: tang
 * @date: Create in 14:01 2021/9/8
 * @description:  文章内容自动审核
 */
@Service
@Slf4j
public class WemediaNewsAutoScanServiceImpl implements WemediaNewsAutoScanService {

    @Autowired
    private WmNewsFeign wmNewsFeign;

    @Autowired
    private ArticleFeign articleFeign;

    @Autowired
    private AdChannelMapper adChannelMapper;

    @Value("${fdfs.url}")
    private String prefixUrl;

    @Autowired
    private FastDFSClient fastDFSClient;


    @Autowired
    private GreenImageScan greenImageScan;

    @Autowired
    private GreenTextScan greenTextScan;

    @Autowired
    private AdSensitiveMapper adSensitiveMapper;
    /**
     * 文章内容审核
     * @param id
     */
    @GlobalTransactional
    @Override
    public void autoScanByMediaNewsId(Integer id) {
        //1参数校验
        if(ObjectUtil.isEmpty(id)){
            log.error("参数不合法,方法为{}"," autoScanByMediaNewsId(Integer id)");
            return;
        }
        //远程调用查询文章信息
        WmNews wmNews = wmNewsFeign.findById(id);
        if(ObjectUtil.isEmpty(wmNews)){
            log.error("文章信息不存在{}",id);
            return;
        }
        //2如果状态信息为4  说明审核通过 直接则新增文章
        if(wmNews.getStatus().equals((short)4)){
            saveAppArticle(wmNews);
            return;
        }
        //3如果状态信息为8 则比较发布时间与当前时间 如果发布时间小于当前时间则新增文章
        if(wmNews.getStatus().equals((short)8) && wmNews.getPublishTime().getTime() < System.currentTimeMillis()){
            saveAppArticle(wmNews);
            return;
        }
        //4如果状态为1  则需要自动审核
        if(wmNews.getStatus().equals((short)1)){
            //抽取文本与图片
            Map<String,Object> map = handlerTextAndImg(wmNews);
            //4.1  进行文本审核
            Boolean textRes = handlerContentReview((String)map.get("content"),wmNews);
            if(!textRes){
                return;
            }
            //4.2  进行图片审核
            Boolean imgRes = handlerImgReview((Set<String>)map.get("images"),wmNews);
            if(!imgRes){
                return;
            }
            //审核结果判断  如果是review 则需要人工审核    block 审核失败
            //5 自动审核通过后 进行敏感词审核
            Boolean sensitiveRes = handleSensitive((String)map.get("content"),wmNews);
            if(!sensitiveRes){
                return;
            }
        }
        //如果发布时间大于当前时间 若状态为8
        if(wmNews.getPublishTime().getTime() > System.currentTimeMillis()){
            wmNews.setStatus((short)8);
            wmNews.setReason("审核通过，待发布");
            wmNewsFeign.updateWmNews(wmNews);
            return;
        }
        //审核通过，且发布时间小于当前时间 则远程调用添加文章信息
        saveAppArticle(wmNews);
    }

    @Autowired
    private RestHighLevelClient client;

    /**
     * 保存文章信息
     * @param wmNews
     */
    private void saveAppArticle(WmNews wmNews) {
        try {
            ApArticle apArticle = this.saveArticle(wmNews);
            if(null != apArticle.getId()){
                this.saveArticelConfig(apArticle.getId());
                this.saveArticleContent(wmNews.getContent(),apArticle.getId());
            }
            //添加成功后，修改文章信息
            wmNews.setStatus(WmNewsStatusEnum.PUBLISHED.getCode());
            wmNews.setArticleId(apArticle.getId());
            wmNews.setReason("审核通过，已发布");
            this.wmNewsFeign.updateWmNews(wmNews);
            //同步数据到es中
            Map<String, Object> map = new HashMap<>();
            map.put("authorId", apArticle.getAuthorId());
            map.put("images", apArticle.getImages());
            map.put("layout", apArticle.getLayout());
            map.put("publishTime", apArticle.getPublishTime());
            map.put("title", apArticle.getTitle());
            map.put("content", wmNews.getContent());
            IndexRequest indexRequest = new IndexRequest("app_info_article").id(apArticle.getId().toString()).source(map);
            client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("保存文章信息失败,文章id为{}",wmNews.getId(),e);
            throw  new RuntimeException("保存文章失败");
        }
    }

    private void saveArticelConfig(Long id) {
        ApArticleConfig apArticleConfig = new ApArticleConfig();
        apArticleConfig.setArticleId(id);
        apArticleConfig.setIsComment(false);
        apArticleConfig.setIsForward(false);
        apArticleConfig.setIsDelete(false);
        apArticleConfig.setIsDown(false);
        this.articleFeign.saveArticleConfig(apArticleConfig);
    }
    private void saveArticleContent(String content, Long id) {
        ApArticleContent articleContent = new ApArticleContent();
        articleContent.setArticleId(id);
        articleContent.setContent(content);
        this.articleFeign.saveArticleContent(articleContent);
    }
    /**
     * 对敏感词进行审核
     * @param wmNews
     * @return
     */
    private Boolean handleSensitive(String content,WmNews wmNews) {
        Boolean flag = true;
        try {
            if(ObjectUtil.isEmpty(wmNews)){
                return flag;
            }
            //查询敏感词
            List<String> words = adSensitiveMapper.findSensitive();
            SensitiveWordUtil.initMap(words);
            Map<String, Integer> mapRes = SensitiveWordUtil.matchWords(content);
            if(mapRes.size()>0){
                //内容中存在敏感词，审核不通过
                wmNews.setStatus((short)3);
                wmNews.setReason("内容中存在敏感词");
                wmNewsFeign.updateWmNews(wmNews);
                flag = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /***
     * 自动审核内容
     * @param content
     * @return
     */
    private Boolean handlerContentReview(String content, WmNews wmNews) {
        Boolean flag = true;
        try {
            if(StrUtil.isBlank(content)){
                return flag;
            }
            Map map = greenTextScan.greeTextScan(content);
            //review block
            if(map.get("suggestion").equals("review")){
                //内容中有不确定因素 修改文章状态 并说明原因
                wmNews.setStatus((short)3);
                wmNews.setReason("内容中有不确定因素");
                wmNewsFeign.updateWmNews(wmNews);
                flag = false;
            }
            if(map.get("suggestion").equals("block")){
                //内容违规
                wmNews.setStatus((short)2);
                wmNews.setReason("内容审核失败");
                wmNewsFeign.updateWmNews(wmNews);
                flag = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 自动审核图片
     * @param wmNews
     * @return
     */
    private Boolean handlerImgReview(Set<String> imgs, WmNews wmNews) {
        Boolean flag = true;
        try {
            if(CollUtil.isEmpty(imgs) || ObjectUtil.isEmpty(wmNews)){
                return flag;
            }
            List<byte[]> imageList = new ArrayList<>();
            for (String img : imgs) {
                int index = img.indexOf("/");
                String groupName = img.substring(0,index) ;
                String path = img.substring(index+1);
                byte[] bys = fastDFSClient.download(groupName, path);
                imageList.add(bys);
            }

            Map map = greenImageScan.imageScan(imageList);
            //review block
            if(map.get("suggestion").equals("review")){
                //内容中有不确定因素 修改文章状态 并说明原因
                wmNews.setStatus((short)3);
                wmNews.setReason("图片中有不确定因素");
                wmNewsFeign.updateWmNews(wmNews);
                flag = false;
            }
            if(map.get("suggestion").equals("block")){
                //内容违规
                wmNews.setStatus((short)2);
                wmNews.setReason("图片审核失败");
                wmNewsFeign.updateWmNews(wmNews);
                flag = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 抽取文章内容与图片
     * @param wmNews
     * @return
     */
    private Map<String, Object> handlerTextAndImg(WmNews wmNews) {
        if(ObjectUtil.isEmpty(wmNews)){
            return null;
        }
        Map<String,Object> map = new HashMap<>();
        //提取内容
        String content = wmNews.getContent();
        List<Map> contentMap = JSON.parseArray(content, Map.class);
        //[{"type":"text","value":"afaawefw"},{"type":"text","value":"请在这里输入正文"}]
        //[{"type":"image","value":"http://120.27.131.73:8888/group1/M00/00/00/rBMQdWEzjJOAXbQlAABaoNRAL-c685.jpg"}]
        String articleContent = contentMap.stream().filter(contentMp -> contentMp.get("type").equals("text")).map(s -> String.valueOf(s.get("value"))).collect(Collectors.joining(","));
        //提取图片
        Set<String> picSet = Arrays.stream(wmNews.getImages().split(",")).collect(Collectors.toSet());
        contentMap.stream().filter(contentMp -> contentMp.get("type").equals("image")).forEach(s->{
            picSet.add(String.valueOf(s.get("value")).replace(prefixUrl,""));
        });
        map.put("content",articleContent);
        map.put("images",picSet);
        return map;
    }

    /**
     * 保存文章信息到文章表
     * @param wmNews
     */
    private ApArticle saveArticle(WmNews wmNews) {
        WmUser wmUser = wmNewsFeign.findWmUserById(wmNews.getUserId());
        if(null == wmUser){
            return null;
        }
        AdChannel adChannel = adChannelMapper.findById(wmNews.getChannelId());
        if(null == adChannel){
            return null;
        }
        //保存文章详情信息
        ApArticle apArticle = new ApArticle();

        ApAuthor apAuthor = articleFeign.selectAuthorByName(wmUser.getName());
        apArticle.setAuthorId(apAuthor.getId());
        apArticle.setAuthorName(apAuthor.getName());
        apArticle.setChannelId(wmNews.getChannelId());
        apArticle.setChannelName(adChannel.getName());
        apArticle.setLabels(wmNews.getLabels());
        //封面图片
        apArticle.setLayout(wmNews.getType());
        //文章标记
        apArticle.setFlag((short)0);
        apArticle.setImages(wmNews.getImages());
        apArticle.setTitle(wmNews.getTitle());
        apArticle.setCreatedTime(new Date());
        apArticle.setPublishTime(wmNews.getPublishTime());
        apArticle.setSyncStatus(false);
        apArticle.setOrigin(0);
        ApArticle apArticle1 = articleFeign.saveArticle(apArticle);
        return apArticle1;
    }
}
