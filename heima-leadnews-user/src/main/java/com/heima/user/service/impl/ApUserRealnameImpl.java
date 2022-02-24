package com.heima.user.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heima.common.constants.user.AdminConstans;
import com.heima.model.article.pojos.ApAuthor;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.dtos.AuthDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.model.user.pojos.ApUserRealname;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.user.feign.ArticleFeign;
import com.heima.user.feign.WemediaFeign;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.mapper.ApUserRealnameMapper;
import com.heima.user.service.ApUserRealnameService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author: tang
 * @date: Create in 19:34 2021/8/30
 * @description:
 */
@Service
public class ApUserRealnameImpl implements ApUserRealnameService {

    @Autowired
    private ApUserRealnameMapper apUserRealnameMapper;

    @Autowired
    private WemediaFeign wemediaFeign;

    @Autowired
    private ArticleFeign articleFeign;

    @Autowired
    private ApUserMapper apUserMapper;

    @Override
    public ResponseResult loadListByStatus(AuthDto dto) {
        if (ObjectUtil.isEmpty(dto)) {
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }
        //初始化分页参数
        dto.checkParam();
//        int i = 1/0;
        //查询
        PageHelper.startPage(dto.getPage(), dto.getSize());
        List<ApUserRealname> list = apUserRealnameMapper.findAll(dto.getStatus());
        PageInfo<ApUserRealname> pageInfo = new PageInfo<>(list);
        ResponseResult result = new PageResponseResult(dto.getPage(), dto.getSize(), Convert.toInt(pageInfo.getTotal()));
        result.setData(pageInfo.getList());
        return result;
    }


    @Override
    @GlobalTransactional
    @Transactional
    public ResponseResult updateStatusById(AuthDto dto,Short status) {
        //参数校验
        if (!ObjectUtil.isAllNotEmpty(dto, dto.getId())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //验证状态
        if (!checkStatus(status)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //修改状态
        ApUserRealname apUserRealname = apUserRealnameMapper.findById(dto.getId());
        if (ObjectUtil.isEmpty(apUserRealname)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        apUserRealname.setStatus(status);
        int res = apUserRealnameMapper.updateStatusById(apUserRealname);
        //保存用户信息到自媒体信息表和作者表中
        if (res > 0 && apUserRealname.getStatus().equals(AdminConstans.AUTH_PASS)) {
            //认证通过后添加自媒体信息到数据库中
            ResponseResult result = createWmUserAndArticle(apUserRealname.getUserId());
            if (result == null) {
                return ResponseResult.errorResult(AppHttpCodeEnum.FAILED);
            }
        }
//        int i = 1/0;
        return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
    }
    /**
     * 保存自媒体用户信息和作者信息
     * @param userId
     * @return
     */
    private ResponseResult createWmUserAndArticle(Integer userId) {
        //查询出用户详情信息
        ApUser apUser = apUserMapper.findById(userId);
        if (ObjectUtil.isEmpty(apUser)) {
            return null;
        }
        //保存文章作者信息
        Boolean flag = saveArticle(apUser);
        if (!flag) {
            return null;
        }
        Boolean res = saveWmUser(apUser);
        if (!res) {
            return null;
        }
        apUser.setFlag((short)1);
        apUserMapper.updateUserStatus(apUser);
        return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
    }


    /**
     * 保存文章作者信息
     *
     * @param apUser
     * @return
     */
    private Boolean saveArticle(ApUser apUser) {
        ApAuthor apAuthor = articleFeign.findById(apUser.getId());
        if (ObjectUtil.isEmpty(apAuthor)) {
            apAuthor = new ApAuthor();
            apAuthor.setName(apUser.getName());
            apAuthor.setUserId(apUser.getId());
            apAuthor.setType(AdminConstans.WEMEDIAPEOPLE);
            ResponseResult result = articleFeign.save(apAuthor);
            if (result.getCode() != 0) {
                return false;
            }
        }
        //保存自媒体人信息
        return true;
    }

    private Boolean saveWmUser(ApUser apUser) {
        WmUser wmUser = wemediaFeign.findByName(apUser.getName());
        if (ObjectUtil.isEmpty(wmUser)) {
            wmUser = new WmUser();
            wmUser.setName(apUser.getName());
            wmUser.setSalt(apUser.getSalt());
            wmUser.setPassword(apUser.getPassword());
            wmUser.setApUserId(apUser.getId());
            wmUser.setPhone(apUser.getPhone());
            wmUser.setImage(apUser.getImage());
            wmUser.setStatus((int) AdminConstans.AUTH_PASS);
            //TODO 设置账号类型
            wmUser.setType(0);
            ResponseResult result = wemediaFeign.save(wmUser);
            if (result.getCode() != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 校验用户实名认证状态
     *
     * @param status
     * @return
     */
    private Boolean checkStatus(Short status) {
        if (status.equals(AdminConstans.AUTH_FAIL) || status.equals(AdminConstans.AUTH_PASS)) {
            return true;
        }
        return false;
    }
}
