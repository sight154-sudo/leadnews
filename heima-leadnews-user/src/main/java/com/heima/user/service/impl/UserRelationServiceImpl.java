package com.heima.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.heima.common.constants.message.FollowBehaviorConstants;
import com.heima.model.article.pojos.ApAuthor;
import com.heima.model.behavior.dtos.ApFollowBehaviorDto;
import com.heima.model.behavior.pojos.ApFollowBehavior;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.dtos.UserRelationDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.model.user.pojos.ApUserFan;
import com.heima.model.user.pojos.ApUserFollow;
import com.heima.user.feign.ArticleFeign;
import com.heima.user.mapper.ApUserFanMapper;
import com.heima.user.mapper.ApUserFollowMapper;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.service.UserRelationService;
import com.heima.utils.threadlocal.WmThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author: tang
 * @date: Create in 19:27 2021/9/11
 * @description:
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserRelationServiceImpl implements UserRelationService {


    @Autowired
    private ArticleFeign articleFeign;

    @Autowired
    private ApUserFollowMapper apUserFollowMapper;

    @Autowired
    private ApUserFanMapper apUserFanMapper;

    @Autowired
    private ApUserMapper apUserMapper;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    /**
     * 用户关注或取消关注作者
     * @param dto
     * @return
     */
    @Override
    public ResponseResult follow(UserRelationDto dto) {
        //校验参数
        if(!ObjectUtil.isAllNotEmpty(dto,dto.getArticleId(),dto.getOperation(),dto.getAuthorId())){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        //查询作者是否存在
        ApAuthor apAuthor = articleFeign.findById(dto.getAuthorId());
        if(ObjectUtil.isEmpty(apAuthor)){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        Integer userId = WmThreadLocalUtils.get().getId();
        ApUser apUser = apUserMapper.findById(userId);
        if(ObjectUtil.isEmpty(apUser)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"先登陆后再操作");
        }
        ResponseResult result;
        //查询用户是否已经关注作者
        if(dto.getOperation() == 0){
            //关注作者
            result = saveApUserFollow(userId, apAuthor);
            if(null != result){
                return result;
            }
            //查询该用户是否已经是作者的粉丝
            result = saveApUserFans(apUser, apAuthor);
            if(null != result){
                return result;
            }
            //TODO 调用kafka保存用户的关注行为信息
            ApFollowBehaviorDto apFollowBehaviorDto = new ApFollowBehaviorDto();
            apFollowBehaviorDto.setFollowId(apAuthor.getUserId());
            apFollowBehaviorDto.setArticleId(dto.getArticleId());
            apFollowBehaviorDto.setEntryId(userId);
            kafkaTemplate.send(FollowBehaviorConstants.FOLLOW_BEHAVIOR_TOPIC, JSON.toJSONString(apFollowBehaviorDto));
        }else if(dto.getOperation() == 1){
            //取消关注
            result = followCancelByUserId(userId,apAuthor.getUserId());
            if(null != result){
                return result;
            }
        }
        return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 取消关注
     * @param userId
     * @param followId
     */
    private ResponseResult followCancelByUserId(Integer userId, Integer followId) {
        //是否未关注
        ApUserFollow apUserFollow = apUserFollowMapper.findByUserIdAndArticleId(userId, followId);
        if(ObjectUtil.isEmpty(apUserFollow)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"未关注，不能取消");
        }
        //是否未是粉丝
        ApUserFan apUserFan = apUserFanMapper.findByUserIdAndFansId(followId, userId);
        if(ObjectUtil.isEmpty(apUserFan)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"不是该作者的粉丝，不能取消");
        }
        //删除粉丝
        apUserFanMapper.delete(followId,userId);
        //删除关注作者
        apUserFollowMapper.delete(userId,followId);
        return null;
    }

    private ResponseResult saveApUserFollow(Integer userId,ApAuthor apAuthor){
        ApUserFollow apUserFollow = apUserFollowMapper.findByUserIdAndArticleId(userId, apAuthor.getUserId());
        if(ObjectUtil.isNotEmpty(apUserFollow)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST,"已经关注该作者");
        }
        //添加用户关注作者信息
        apUserFollow = new ApUserFollow();
        apUserFollow.setUserId(userId);
        apUserFollow.setFollowId(apAuthor.getUserId());
        apUserFollow.setFollowName(apAuthor.getName());
        apUserFollow.setLevel((short)0);
        apUserFollow.setIsNotice(true);
        apUserFollow.setCreatedTime(new Date());
        apUserFollowMapper.save(apUserFollow);
        return null;
    }

    private ResponseResult saveApUserFans(ApUser apUser,ApAuthor apAuthor){
        ApUserFan apUserFan = apUserFanMapper.findByUserIdAndFansId(apUser.getId(), apAuthor.getUserId());
        if(ObjectUtil.isNotEmpty(apUserFan)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST,"已经成为粉丝");
        }
        //添加作者添加粉丝信息
        apUserFan = new ApUserFan();
        apUserFan.setUserId(apAuthor.getUserId());
        apUserFan.setFansId(apUser.getId());
        apUserFan.setFansName(apUser.getName());
        apUserFan.setLevel((short)0);
        apUserFan.setCreatedTime(new Date());
        apUserFan.setIsDisplay(false);
        apUserFan.setIsShieldLetter(false);
        apUserFan.setIsShieldComment(false);
        apUserFanMapper.insert(apUserFan);
        return null;
    }


    @Override
    public Boolean isFollow(Integer userId, Integer followId) {
        return apUserFollowMapper.findByUserIdAndArticleId(userId,followId)!=null;
    }
}
