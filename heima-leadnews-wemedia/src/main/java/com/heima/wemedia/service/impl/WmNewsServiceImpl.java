package com.heima.wemedia.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
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
import com.heima.model.wemedia.pojos.WmNewsMaterial;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.threadlocal.WmThreadLocalUtils;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.mapper.WmNewsMapper;
import com.heima.wemedia.mapper.WmNewsMaterialMapper;
import com.heima.wemedia.service.WmNewsService;
import com.mchange.lang.ShortUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        //处理图片地址
        String url = getString(dto);
        dto.setUserId(wmUser.getId());
        dto.setCreatedTime(DateUtil.now());
        //TODO  提交时间
        dto.setSubmitedTime(DateUtil.now());
        WmNews wmNews = new WmNews();
        BeanUtil.copyProperties(dto,wmNews,"images");
        wmNews.setImages(url);
        wmNews.setPublishTime(DateUtil.parseUTC(wmNews.getPublishTime()).toString("yyyy-MM-dd HH:mm:ss"));
        int res = wmNewsMapper.save(wmNews);
        if(res == 0){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.FAILED);
        }
        //保存封面图片与素材的引用

        //保存内容图片与素材的引用
        return null;
    }

    /**
     * 保存图片与素材关系
     * @param id
     * @param image
     * @return
     */
    private int saveMaterialAndNews(Integer id, String image) {
        return 0;
    }

    /**
     * 处理图片地址
     * @param dto
     * @return
     */
    private String getString(WmNewsDto dto) {
        String url = "";
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
        if(dto.getType().equals(WemediaContans.WM_NEWS_SINGLE_IMAGE)){
            url = dto.getImages().get(0).replace(prefixUrl,"");
        }else if(dto.getType().equals(WemediaContans.WM_NEWS_MANY_IMAGE)){
            List<String> list = dto.getImages().stream().map(img -> img.replace(prefixUrl,"")).collect(Collectors.toList());
            url = StringUtils.join(list,",");
        }else if(dto.getType().equals(WemediaContans.WM_NEWS_NONE_IMAGE)){
            url = "";
        }else if(dto.getType().equals(WemediaContans.WM_NEWS_TYPE_AUTO)){

            String content = dto.getContent();
            JSONArray jsonArray = JSONUtil.parseArray(content);
            Object[] objects = jsonArray.stream().toArray();
            List<String> res = Arrays.stream(objects).filter(obj -> JSONUtil.parseObj(obj).get(WemediaContans.WM_NEWS_TYPE).equals(WemediaContans.WM_NEWS_TYPE_IMAGE)).map(obj -> {
                String s = JSONUtil.parseObj(obj).get(WemediaContans.WM_NEWS_TYPE_VALUE).toString();
//                s.replace(s,prefixUrl);
//                s = s.substring(s.lastIndexOf("group"));
                s = s.replace(prefixUrl, "");
                return s;
            }).collect(Collectors.toList());
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
        }
        return url;
    }


}
