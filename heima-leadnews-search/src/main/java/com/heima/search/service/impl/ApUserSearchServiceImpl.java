package com.heima.search.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.heima.model.behavior.dtos.ApBehaviorEntryDto;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.model.search.pojos.ApUserSearch;
import com.heima.search.feign.BehaviorFeign;
import com.heima.search.mapper.ApUserSearchMapper;
import com.heima.search.service.ApUserSearchService;
import com.heima.utils.threadlocal.WmThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: tang
 * @date: Create in 20:48 2021/9/14
 * @description:
 */
@Service
@Slf4j
public class ApUserSearchServiceImpl implements ApUserSearchService {

    @Autowired
    private BehaviorFeign behaviorFeign;

    @Autowired
    private ApUserSearchMapper apUserSearchMapper;

    /**
     * 加载用户搜索历史
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findUserSearch(UserSearchDto dto) {
        //校验参数
        if(ObjectUtil.isEmpty(dto)){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        //查询当前用户行为实体
        ApBehaviorEntry entry = getApBehaviorEntry();
        if(ObjectUtil.isEmpty(entry)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"用户实体行为不存在");
        }
        //根据实体id查询数据库中的数据
        List<ApUserSearch> apUserSearches = apUserSearchMapper.findAll(entry.getId(), 1,dto.getPageSize());
        return ResponseResult.okResult(apUserSearches);
    }

    /**
     * 删除用户历史
     * @param dto
     * @return
     */
    @Override
    public ResponseResult delUserSearch(UserSearchDto dto) {
        //校验参数
        if(!ObjectUtil.isAllNotEmpty(dto,dto.getId())){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        ApBehaviorEntry entry = getApBehaviorEntry();
        if(ObjectUtil.isEmpty(entry)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"用户实体行为不存在");
        }
        apUserSearchMapper.update(entry.getId(),0);
        return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 添加用户搜索记录
     * @param dto
     */
    @Async("taskExecutor")
    public void insertUserSearch(UserSearchDto dto){
        if(!ObjectUtil.isAllNotEmpty(dto,dto.getSearchWords())){
            return;
        }
        //查询数据库中是否存在
        ApUserSearch apUserSearch = apUserSearchMapper.findByEntryAndKeyWord(dto.getId(), dto.getSearchWords());
        if(ObjectUtil.isNotEmpty(apUserSearch) && apUserSearch.getStatus() == 0){
            //存在 则修改
            apUserSearch.setStatus(1);
            apUserSearchMapper.update(apUserSearch.getEntryId(), apUserSearch.getStatus());
            return;
        }
        //不存在
        apUserSearch = new ApUserSearch();
        apUserSearch.setStatus(1);
        apUserSearch.setEntryId(dto.getId());
        apUserSearch.setKeyword(dto.getSearchWords());
        apUserSearch.setCreatedTime(new Date());
        apUserSearchMapper.insert(apUserSearch);
    }

    private ApBehaviorEntry getApBehaviorEntry(){
        Integer userId = WmThreadLocalUtils.get().getId();
        int type = userId == 0?0:1;
        ApBehaviorEntryDto apBehaviorEntryDto = new ApBehaviorEntryDto();
        apBehaviorEntryDto.setEntryId(userId);
        apBehaviorEntryDto.setType((short)type);
        ApBehaviorEntry entry = behaviorFeign.findByTypeAndEntryId(apBehaviorEntryDto);
        return entry;
    }
}
