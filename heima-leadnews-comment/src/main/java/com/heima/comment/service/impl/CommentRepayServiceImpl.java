package com.heima.comment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.heima.comment.feign.ArticleFeign;
import com.heima.comment.feign.UserFeign;
import com.heima.comment.service.CommentRepayService;
import com.heima.common.aliyun.GreenTextScan;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.comment.dtos.CommentRepayDto;
import com.heima.model.comment.dtos.CommentRepayLikeDto;
import com.heima.model.comment.dtos.CommentRepaySaveDto;
import com.heima.model.comment.pojos.ApComment;
import com.heima.model.comment.pojos.ApCommentLike;
import com.heima.model.comment.pojos.ApCommentRepay;
import com.heima.model.comment.pojos.ApCommentRepayLike;
import com.heima.model.comment.vo.ApCommentRepayVo;
import com.heima.model.comment.vo.ApCommentVo;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.threadlocal.WmThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: tang
 * @date: Create in 11:16 2021/9/14
 * @description:
 */
@Service
@Slf4j
public class CommentRepayServiceImpl implements CommentRepayService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GreenTextScan greenTextScan;

    @Autowired
    private UserFeign userFeign;

    @Value("${fdfs.url}")
    private String prefixUrl;

    /**
     * 加载评论
     * @param dto
     * @return
     */
    @Override
    public ResponseResult loadCommentRepay(CommentRepayDto dto) {
        if(!ObjectUtil.isAllNotEmpty(dto,dto.getCommentId())){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        if(null == dto.getSize() || 0 == dto.getSize()){
            dto.setSize(20);
        }
        //查询文章的评论信息
        Query query = Query.query(Criteria.where("commentId").is(dto.getCommentId()).and("likes").lt(dto.getMinLikes())).limit(dto.getSize());
        query.with(Sort.by(Sort.Order.desc("likes")));
        List<ApCommentRepay> apCommentRepays = this.mongoTemplate.find(query, ApCommentRepay.class);
        if(CollUtil.isEmpty(apCommentRepays)){
            return ResponseResult.okResult(null);
        }
        //收集评论的用户id信息
        List<Object> commentRepayIds = CollUtil.getFieldValues(apCommentRepays, "id");
        Integer userId = WmThreadLocalUtils.get().getId();
        //如果用户未登陆，直接返回数据
        if(0 == userId){
            return ResponseResult.okResult(apCommentRepays);
        }
        //查询当前用户是否点赞过哪些评论
        //查询点赞文档信息
        Query queryCommentLike = Query.query(Criteria.where("authorId").is(userId).and("commentRepayId").in(commentRepayIds));
        Map<String, ApCommentRepayLike> map = this.mongoTemplate.find(queryCommentLike, ApCommentRepayLike.class).stream().collect(Collectors.toMap(k -> k.getCommentRepayId(), v -> v));
        //封装结果
        List<ApCommentRepayVo> collect = apCommentRepays.stream().map(apCommentRepay -> {
            ApCommentRepayVo vo = new ApCommentRepayVo();
            ApCommentRepayLike apCommentLike = map.get(apCommentRepay.getId());
            BeanUtil.copyProperties(apCommentRepay, vo);
            vo.setOperation((short)1);
            if (apCommentLike != null) {
                vo.setOperation(apCommentLike.getOperation());
            }
            return vo;
        }).collect(Collectors.toList());
        collect.sort((o1,o2)->o2.getLikes()-o1.getLikes());
        return ResponseResult.okResult(collect);
    }

    /**
     * 保存评论
     * @param dto
     * @return
     */
    @Override
    public ResponseResult saveCommentRepay(CommentRepaySaveDto dto) {
        //参数校验
        if(!ObjectUtil.isAllNotEmpty(dto,dto.getContent())){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        if(dto.getContent().length() > 140){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"评论字数过长");
        }
        //内容审核
        Map map = null;
        try {
            map = greenTextScan.greeTextScan(dto.getContent());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("评论文本审核失败{}",dto.getContent());
        }
        if(map.get("suggestion").equals("review")){
            return ResponseResult.errorResult(AppHttpCodeEnum.FAILED,"评论中有可疑文字");
        }
        if(map.get("suggestion").equals("block")){
            return ResponseResult.errorResult(AppHttpCodeEnum.FAILED,"评论中有非法文字");
        }
        Integer userId = WmThreadLocalUtils.get().getId();
        if(0 == userId){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN,"请先登陆！！");
        }
        //查询用户信息
        ApUser apUser = userFeign.findUserById(userId);
        if(ObjectUtil.isEmpty(apUser)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"用户信息不存在");
        }
        //保存评论
        ApCommentRepay apCommentRepay = new ApCommentRepay();
        apCommentRepay.setAuthorId(userId);
        apCommentRepay.setAuthorName(apUser.getName());
        apCommentRepay.setContent(dto.getContent());
        apCommentRepay.setCommentId(dto.getCommentId());
        apCommentRepay.setImage(prefixUrl+apUser.getImage());
        apCommentRepay.setLikes(0);
        //TODO 设置评论类型
        apCommentRepay.setCreatedTime(new Date());
        apCommentRepay.setUpdatedTime(new Date());
        mongoTemplate.insert(apCommentRepay);
        return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult saveCommentRepayLike(CommentRepayLikeDto dto) {
        if(!ObjectUtil.isAllNotEmpty(dto,dto.getCommentRepayId(),dto.getOperation())){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        //查询评论信息  并修改
        ApCommentRepay apCommentRepay = this.mongoTemplate.findById(dto.getCommentRepayId(), ApCommentRepay.class);
        if(ObjectUtil.isEmpty(apCommentRepay)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"评论数据不存在");
        }
        Integer userId = WmThreadLocalUtils.get().getId();
        if(0 == userId){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN,"请先登陆！！");
        }
        Query query = Query.query(Criteria.where("commentRepayId").is(dto.getCommentRepayId()).and("authorId").is(userId));
        ApCommentRepayLike apCommentRepayLike = this.mongoTemplate.findOne(query, ApCommentRepayLike.class);
        if(dto.getOperation().equals((short)0)){
            //点赞
            apCommentRepay.setLikes(apCommentRepay.getLikes()+1);
            //保存评论点赞文档数据
            if(null == apCommentRepayLike){
                apCommentRepayLike = new ApCommentRepayLike();
            }
            apCommentRepayLike.setCommentRepayId(dto.getCommentRepayId());
            apCommentRepayLike.setOperation((short)0);
            apCommentRepayLike.setAuthorId(userId);
            this.mongoTemplate.save(apCommentRepayLike);
        }else{
            //取消点赞
            Integer likes = apCommentRepay.getLikes()-1;
            apCommentRepay.setLikes(likes>0?likes:0);
            //直接根据评论id与用户id删除点赞文档信息
            if(apCommentRepayLike != null){
                apCommentRepayLike.setOperation((short)1);
                this.mongoTemplate.save(apCommentRepayLike);
            }
        }
        mongoTemplate.save(apCommentRepay);
        //返回点赞数给前台
        return ResponseResult.okResult(apCommentRepay.getLikes());
    }
}
