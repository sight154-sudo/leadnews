package com.heima.article.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.heima.article.mapper.ApAuthorMapper;
import com.heima.article.service.ApAuthorService;
import com.heima.model.article.pojos.ApAuthor;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author: tang
 * @date: Create in 22:17 2021/8/30
 * @description:
 */
@Service
public class ApAuthorServiceImpl implements ApAuthorService {

    @Autowired
    private ApAuthorMapper articleMapper;

    @Override
    public ApAuthor findByUserId(Integer id) {
        if(null != id){
            return articleMapper.findByUserId(id);
        }
        return null;
    }

    @Override
    @Transactional
    public ResponseResult save(ApAuthor apAuthor) {
        if(ObjectUtil.isEmpty(apAuthor)){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
//        apAuthor.setCreatedTime(DateUtil.now());
        apAuthor.setCreatedTime(new Date());
        int res = articleMapper.save(apAuthor);
//        int i = 1/0;
        return res!=0?ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS):ResponseResult.errorResult(AppHttpCodeEnum.SAVE_FAILED);
    }

    /**
     * 通过name查询文章作者信息
     * @param name
     * @return
     */
    @Override
    public ApAuthor findByName(String name) {
        if(StrUtil.isBlank(name)){
            return null;
        }
        return articleMapper.findByName(name);
    }
}
