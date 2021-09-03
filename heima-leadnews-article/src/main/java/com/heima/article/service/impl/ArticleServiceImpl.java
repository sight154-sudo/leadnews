package com.heima.article.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.heima.article.mapper.ArticleMapper;
import com.heima.article.service.ArticleService;
import com.heima.model.article.pojos.ApAuthor;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.pojos.WmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: tang
 * @date: Create in 22:17 2021/8/30
 * @description:
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public ApAuthor findByUserId(Integer id) {
        if(null != id){
            return articleMapper.findByUserId(id);
        }
        return null;
    }

    @Override
    public ResponseResult save(ApAuthor apAuthor) {
        if(ObjectUtil.isEmpty(apAuthor)){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
//        apAuthor.setCreatedTime(new Date());
        int res = articleMapper.save(apAuthor);
        return res>0?ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS):ResponseResult.errorResult(AppHttpCodeEnum.SAVE_FAILED);
    }

}
