package com.heima.wemedia.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heima.common.constants.wemedia.WemediaContans;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.threadlocal.WmThreadLocalUtils;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.mapper.WmNewsMapper;
import com.heima.wemedia.mapper.WmNewsMaterialMapper;
import com.heima.wemedia.service.WmNewsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: tang
 * @date: Create in 10:21 2021/9/4
 * @description:
 */
@Service
@Slf4j
public class WmNewsServiceImpl implements WmNewsService {

    @Autowired
    private WmNewsMapper wmNewsMapper;

    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;

    @Autowired
    private WmMaterialMapper wmMaterialMapper;

    @Value("${fdfs.url}")
    private String prefixUrl;
    /**
     * 条件查询文章列表
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findAll(WmNewsPageReqDto dto) {
        if(ObjectUtil.isEmpty(dto)){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        //初始化分页参数
        dto.checkParam();
        WmUser wmUser = WmThreadLocalUtils.get();
        dto.setUserId(wmUser.getId());
        PageHelper.startPage(dto.getPage(),dto.getSize());
        List<WmNews> list = wmNewsMapper.findAll(dto);
        PageInfo<WmNews> pageInfo = new PageInfo<>(list);
        ResponseResult result = new PageResponseResult(dto.getPage(),dto.getSize(), Convert.toInt(pageInfo.getTotal()));
        result.setData(pageInfo.getList());
        result.setHost(prefixUrl);
        return result;
    }

    /**
     * 发布文章
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public ResponseResult submitNews(WmNewsDto dto) {
        if(ObjectUtil.isEmpty(dto)){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        if(!ObjectUtil.equals(dto.getStatus(), WemediaContans.WM_CONTENT_REFERENCE)){
            //菲草稿 则需要对参数进行校验
            if(!ObjectUtil.isAllNotEmpty(dto,dto.getContent(),dto.getTitle(),dto.getType(),dto.getChannelId())){
                return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
            }
        }
        WmUser wmUser = WmThreadLocalUtils.get();
        dto.setUserId(wmUser.getId());
        WmNews wmNews = new WmNews();
        BeanUtil.copyProperties(dto,wmNews,"images");
        int res =  saveOrUpdateWmNews(dto, wmNews);;
        //处理图片地址
        if(res == 0){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.FAILED);
        }
        //如果是草稿 则不保存图片与素材的引用 直接返回
        if(dto.getStatus().equals(WemediaContans.WM_CONTENT_REFERENCE)){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
        }
        //如果是提交审核  则保存封面图片与素材的引用
        //获取封面图片的地址
        ResponseResult result = null;
        String coverUrl = wmNews.getImages();
        if(StrUtil.isNotBlank(coverUrl)){
            result = saveCoverImgReference(wmNews.getId(),coverUrl);
        }
        if(result!=null){
            return result;
        }
        //保存内容图片与素材的引用
        List<String> contentImgs = this.getContentUrl(dto);
        if(CollUtil.isNotEmpty(contentImgs)){
            result = saveContentImgReference(wmNews.getId(),contentImgs);
        }
        if(result!=null){
            return result;
        }
        return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 保存文章信息
     * @param dto
     * @param wmNews
     * @return
     */
    private int saveOrUpdateWmNews(WmNewsDto dto, WmNews wmNews) {
        String url = getString(dto);
        if(null == dto.getId()){
            wmNews.setCreatedTime(DateUtil.now());
        }
        //TODO  提交时间
        wmNews.setSubmitedTime(DateUtil.now());
        wmNews.setImages(url);
        wmNews.setType(dto.getType());
        if(wmNews.getPublishTime().contains("T")){
            wmNews.setPublishTime(DateUtil.parseUTC(wmNews.getPublishTime()).toString("yyyy-MM-dd HH:mm:ss"));
        }else{
            wmNews.setPublishTime(DateUtil.parse(wmNews.getPublishTime()).toString("yyyy-MM-dd HH:mm:ss"));
        }
        int res = null == dto.getId()?wmNewsMapper.save(wmNews):wmNewsMapper.update(wmNews);
        return res;
    }

    /**
     * 处理素材的引用
     * @param newsId
     * @param materialIds
     * @param type
     */
    private void handlerWmNewsMaterial(Integer newsId, List<Object> materialIds,short type) {
        wmNewsMaterialMapper.deleteBatch(newsId,type);
        wmNewsMaterialMapper.saveBatch(newsId,materialIds,type);
    }
    /**
     * 保存内容图片的引用
     * @param newsId
     * @param images
     * @return
     */
    private ResponseResult saveContentImgReference(Integer newsId,List<String> images){
        if(null == newsId || CollUtil.isEmpty(images)){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        //获取素材id
        List<Object> materialIds = this.getMaterialIds(WmThreadLocalUtils.get().getId(), this.handleImgUrl(images));
        //先删除关联图片的数据 再新增
        handlerWmNewsMaterial(newsId, materialIds,(short)0);
        return null;
    }
    /**
     * 保存封面图片的引用
     * @param newsId
     * @param images
     * @return
     */
    private ResponseResult saveCoverImgReference(Integer newsId,String images){
        if(null == newsId || StrUtil.isBlank(images)){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        List<String> urls = Arrays.stream(images.split(",")).collect(Collectors.toList());
        List<Object> materialIds = this.getMaterialIds(WmThreadLocalUtils.get().getId(), urls);
        handlerWmNewsMaterial(newsId, materialIds,(short)1);
        return null;
    }

    /**
     * 根据素材路径获取素材id
     * @param userId
     * @param images
     * @return
     */
    private List<Object> getMaterialIds(Integer userId,List<String> images){
        List<WmMaterial> list = wmMaterialMapper.findAllByUrl(userId, images);
        return CollUtil.getFieldValues(list,"id");
    }
    /**
     * 处理封面图片地址
     * @param dto
     * @return
     */
    private String getString(WmNewsDto dto) {
        //对封面进行处理
        /**
         * 如果选择单图  type:1 从素材列表中选出一张图 存放到wmnews表中的images字段上
         * 如果选择三图  type:3 从素材列表中选择三张图片对图片地址进行拼接 存放到数据库
         * 如果选择无图  type:0 则images字段 为""
         * 如果选择自动  type:-1 会从发布文章的内容中获取图片 作为封面图
         *     1。如果文章内容没有图片，则images为”“
         *     2。如果文章内容的图片数量小于2，则以第一张图为封面
         *     3。如果文章内容中的图片个数在3个以上，认为是多图，则从文章内容中选择前三个图片作为封面
         */
        //自动
        if(dto.getType().equals(WemediaContans.WM_NEWS_TYPE_AUTO)){
            return getAutoCoverImgUrl(dto);
        }
        //单图 三图  无图
        return getCoverImgUrl(dto);
    }

    /**
     * 获取封面图片中的Url中的图片
     * @param dto
     * @return
     */
    private String getCoverImgUrl(WmNewsDto dto) {
        String url = "";
        if(dto.getType().equals(WemediaContans.WM_NEWS_SINGLE_IMAGE)){
            url = dto.getImages().get(0).replace(prefixUrl,"");
        }else if(dto.getType().equals(WemediaContans.WM_NEWS_MANY_IMAGE)){
            List<String> list = handleImgUrl(dto.getImages());
            url = StringUtils.join(list,",");
        }else if(dto.getType().equals(WemediaContans.WM_NEWS_NONE_IMAGE)){
            url = "";
        }
        return url;
    }

    /**
     * 处理图片ip
     * @param images
     * @return
     */
    private List<String> handleImgUrl(List<String> images){
        return images.stream().map(img -> img.replace(prefixUrl,"")).collect(Collectors.toList());
    }

    /**
     * 获取为自动封面的图片地址
     * @param dto
     * @return
     */
    private String getAutoCoverImgUrl(WmNewsDto dto) {
        String url;
        List<String> res = getContentUrl(dto);
        if(res.size() == 0){
            //自动 无图
            dto.setType(WemediaContans.WM_NEWS_NONE_IMAGE);
            url = "";
        }else if(res.size() < 3){
            //自动 单图
            dto.setType(WemediaContans.WM_NEWS_SINGLE_IMAGE);
            url = res.get(0);
        }else{
            //自动 多图
            dto.setType(WemediaContans.WM_NEWS_MANY_IMAGE);
            List<String> collect = res.stream().limit(3).collect(Collectors.toList());
            url = StringUtils.join(collect,",");
        }
        return url;
    }

    /**
     * 获取内容中的图片
     * @param dto
     * @return
     */
    private List<String> getContentUrl(WmNewsDto dto) {
        String content = dto.getContent();
        List<Map> mapList = JSON.parseArray(content, Map.class);
        List<String> res = mapList.stream().filter(map -> map.get(WemediaContans.WM_NEWS_TYPE).equals(WemediaContans.WM_NEWS_TYPE_IMAGE)).map(map -> {
            String s = map.get(WemediaContans.WM_NEWS_TYPE_VALUE).toString();
            return s.replace(prefixUrl, "");
        }).collect(Collectors.toList());
        return res;
    }


    /**
     * 根据文章id查询详情信息
     * @param newsId
     * @return
     */
    @Override
    public ResponseResult findOne(Integer newsId) {
        if(ObjectUtil.isEmpty(newsId)){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        //查询数据
        WmNews wmNews = this.wmNewsMapper.findById(newsId);
        //封装数据
        ResponseResult result = ResponseResult.okResult(wmNews);
        result.setHost(prefixUrl);
        return result;
    }


    /**
     * 根据id删除文章
     * @param newsId
     * @return
     */
    @Override
    public ResponseResult deleteOne(Integer newsId) {
        /**
         * 分析: 已上架的文章不能删除，其他可以删除 且删除时，需要解除关联素材的关系
         */
        if(ObjectUtil.isEmpty(newsId)){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        //查询文章信息
        WmNews wmNews = wmNewsMapper.findById(newsId);
        if(ObjectUtil.isEmpty(wmNews)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        //是否上架
        if(wmNews.getEnable()){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"文章已上架，不可删除!!!");
        }
        //删除关联素材关系
        wmNewsMaterialMapper.deleteById(wmNews.getId());
        //删除数据
        wmNewsMapper.deleteById(wmNews.getId());
        return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
    }


    /**
     * 文章的上下架
     * @param dto
     * @return
     */
    @Override
    public ResponseResult downOrUp(WmNewsDto dto) {
        if(!ObjectUtil.isAllNotEmpty(dto,dto.getId(),dto.getEnable())){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        //查询文章详情信息
        WmNews wmNews = wmNewsMapper.findById(dto.getId());
        if(!wmNews.getStatus().equals(WemediaContans.WM_ALREADY_PUBLISHED)){
            return ResponseResult.errorResult(AppHttpCodeEnum.FAILED,"文章还未发布");
        }
        wmNewsMapper.updateEnableById(dto.getId(),dto.getEnable());
        return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
    }
}
