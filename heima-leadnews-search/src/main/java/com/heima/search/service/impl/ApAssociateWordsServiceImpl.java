package com.heima.search.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.model.search.pojos.ApAssociateWords;
import com.heima.search.mapper.ApAssociateWordsMapper;
import com.heima.search.model.Trie;
import com.heima.search.service.ApAssociateWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tang
 * @date: Create in 18:27 2021/9/16
 * @description: 搜索联想词
 */
@Service
public class ApAssociateWordsServiceImpl implements ApAssociateWordsService {

    @Autowired
    private ApAssociateWordsMapper apAssociateWordsMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public ResponseResult search(UserSearchDto dto) {
        //校验参数
        if(!ObjectUtil.isAllNotEmpty(dto,dto.getSearchWords(),dto.getPageSize())){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        //查询数据库
        List<ApAssociateWords> list = apAssociateWordsMapper.search(dto.getSearchWords(),dto.getPageSize());
        return ResponseResult.okResult(list);
    }

    @Override
    public ResponseResult searchV2(UserSearchDto dto) {
        //校验参数
        if(!ObjectUtil.isAllNotEmpty(dto,dto.getSearchWords(),dto.getPageSize())){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        //先查询缓存 若命中缓存 则直接返回
        String associateList = redisTemplate.opsForValue().get("associate_list");
        List<ApAssociateWords> apAssociateWords = null;
        if(StrUtil.isNotBlank(associateList)){
            //存在
            apAssociateWords = JSON.parseArray(associateList, ApAssociateWords.class);
        }else{
            //不存在 查询数据库 并添加到缓存中
            apAssociateWords = apAssociateWordsMapper.findAll();
            redisTemplate.opsForValue().set("associate_list",JSON.toJSONString(apAssociateWords));
        }
        //构建 trie树
        //4.构建trie数据结构，从trie中获取数据，封装返回
        Trie t = new Trie();
        for (ApAssociateWords apAssociateWord : apAssociateWords) {
            t.insert(apAssociateWord.getAssociateWords());
        }

        List<String> ret = t.startWith(dto.getSearchWords());
        List<ApAssociateWords> resultList  = new ArrayList<>();
        for (String s : ret) {
            ApAssociateWords aaw = new ApAssociateWords();
            aaw.setAssociateWords(s);
            resultList.add(aaw);
        }

        return ResponseResult.okResult(resultList);

    }
}
