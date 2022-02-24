package com.heima.comment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.heima.comment.feign.ArticleFeign;
import com.heima.comment.feign.UserFeign;
import com.heima.comment.service.CommentService;
import com.heima.common.aliyun.GreenTextScan;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.comment.dtos.CommentDto;
import com.heima.model.comment.dtos.CommentLikeDto;
import com.heima.model.comment.dtos.CommentSaveDto;
import com.heima.model.comment.pojos.ApComment;
import com.heima.model.comment.pojos.ApCommentLike;
import com.heima.model.comment.vo.ApCommentVo;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.threadlocal.WmThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
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
 * @date: Create in 19:42 2021/9/13
 * @description:
 */
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GreenTextScan greenTextScan;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private ArticleFeign articleFeign;

    @Value("${fdfs.url}")
    private String prefixUrl;
    /**
     * 保存用户评论
     * @param dto
     * @return
     */
    @Override
    public ResponseResult saveComment(CommentSaveDto dto) {
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
        //查询文章详情信息
        ApArticle apArticle = articleFeign.findById(dto.getArticleId());
        if(ObjectUtil.isEmpty(apArticle)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"文章信息不存在");
        }
        //保存评论
        ApComment apComment = new ApComment();
        apComment.setAuthorId(userId);
        apComment.setAuthorName(apUser.getName());
        apComment.setContent(dto.getContent());
        apComment.setImage(prefixUrl+apUser.getImage());
        apComment.setEntryId(dto.getArticleId());
        apComment.setLikes(0);
        apComment.setChannelId(apArticle.getChannelId());
        //TODO 设置评论类型
        apComment.setType((short)0);
        apComment.setReply(0);
        apComment.setFlag((short)0);
        apComment.setOrd(1);
        apComment.setCreatedTime(new Date());
        apComment.setUpdatedTime(new Date());
        mongoTemplate.insert(apComment);
        return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 点赞评论
     * @param dto
     * @return
     */
    @Override
    public ResponseResult like(CommentLikeDto dto) {
        if(!ObjectUtil.isAllNotEmpty(dto,dto.getCommentId(),dto.getOperation())){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        //查询评论信息  并修改
        ApComment apComment = this.mongoTemplate.findById(dto.getCommentId(), ApComment.class);
        if(ObjectUtil.isEmpty(apComment)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"评论数据不存在");
        }
        Integer userId = WmThreadLocalUtils.get().getId();
        //查询该用户是否点赞过
        Query query = Query.query(Criteria.where("commentId").is(dto.getCommentId()).and("authorId").is(userId));
        ApCommentLike apCommentLike= this.mongoTemplate.findOne(query, ApCommentLike.class);
        if(dto.getOperation().equals((short)0)){
            //点赞
            apComment.setLikes(apComment.getLikes()+1);
            //保存评论点赞文档数据
            if(null == apCommentLike){
                apCommentLike = new ApCommentLike();
            }
            apCommentLike.setCommentId(dto.getCommentId());
            apCommentLike.setOperation((short)0);
            apCommentLike.setAuthorId(userId);
            this.mongoTemplate.save(apCommentLike);
        }else{
            //取消点赞
            Integer likes = apComment.getLikes()-1;
            apComment.setLikes(likes>0?likes:0);
            //直接根据评论id与用户id删除点赞文档信息
            if(apCommentLike != null){
                apCommentLike.setOperation((short)1);
                this.mongoTemplate.save(apCommentLike);
            }
        }
        mongoTemplate.save(apComment);
        //返回点赞数给前台
        return ResponseResult.okResult(apComment.getLikes());
    }

    /**
     * 查询评论
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findByArticleId(CommentDto dto) {
        if(!ObjectUtil.isAllNotEmpty(dto,dto.getArticleId())){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        if(null == dto.getSize()){
            dto.setSize(20);
        }
        //查询文章的评论信息
        Query query = Query.query(Criteria.where("entryId").is(dto.getArticleId()).and("likes").lt(dto.getMinLikes())).limit(dto.getSize());
        query.with(Sort.by(Sort.Order.desc("likes")));
        List<ApComment> apComments = this.mongoTemplate.find(query, ApComment.class);
        if(CollUtil.isEmpty(apComments)){
            return ResponseResult.okResult(null);
        }
        //收集评论的用户id信息
        List<Object> commentIds = CollUtil.getFieldValues(apComments, "id");
        Integer userId = WmThreadLocalUtils.get().getId();
        //如果用户未登陆，直接返回数据
        if(0 == userId){
            return ResponseResult.okResult(apComments);
        }
        //查询当前用户是否点赞过哪些评论
        //查询点赞文档信息
        Query queryCommentLike = Query.query(Criteria.where("authorId").is(userId).and("commentId").in(commentIds));
        List<ApCommentLike> apCommentLikes = this.mongoTemplate.find(queryCommentLike, ApCommentLike.class);
        Map<String, ApCommentLike> map = apCommentLikes.stream().collect(Collectors.toMap(k -> k.getCommentId(), v -> v));
        List<ApCommentVo> collect = apComments.stream().map(apComment -> {
            ApCommentVo vo = new ApCommentVo();
            ApCommentLike apCommentLike = map.get(apComment.getId());
            BeanUtil.copyProperties(apComment, vo);
            vo.setOperation((short)1);
            if (apCommentLike != null) {
                vo.setOperation(apCommentLike.getOperation());
            }
            return vo;
        }).collect(Collectors.toList());
        collect.sort((o1,o2)->o2.getLikes()-o1.getLikes());
        return ResponseResult.okResult(collect);
    }
}
