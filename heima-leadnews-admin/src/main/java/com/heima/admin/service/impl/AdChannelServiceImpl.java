package com.heima.admin.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heima.admin.mapper.AdChannelMapper;
import com.heima.admin.service.AdChannelService;
import com.heima.common.exception.BusinessException;
import com.heima.model.admin.dtos.AdChannelDto;
import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: tang
 * @date: Create in 20:17 2021/8/28
 * @description:
 */
@Service
@Slf4j
public class AdChannelServiceImpl implements AdChannelService {

    @Autowired
    private AdChannelMapper adChannelMapper;

    @Override
    public ResponseResult findAllByName(AdChannelDto dto) {
        //参数校验
        if(ObjectUtil.isEmpty(dto)){
            ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        //初始化
        dto.checkParam();
        PageHelper.startPage(dto.getPage(),dto.getSize());
        List<AdChannel> list = adChannelMapper.findAll(dto.getName());
        PageInfo<AdChannel> pageInfo = new PageInfo(list);
        ResponseResult responseResult = new PageResponseResult(dto.getPage(), dto.getSize(), Convert.toInt(pageInfo.getTotal()));
        responseResult.setData(list);
        return responseResult;
    }

    @Override
    public ResponseResult save(AdChannel adchannel) {
        try {
            if(ObjectUtil.isEmpty(adchannel)){
                return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
            }
            //设置创建时间
            adchannel.setCreateTime(new Date());
            int res = adChannelMapper.save(adchannel);
            return res>0?ResponseResult.errorResult(AppHttpCodeEnum.SUCCESS):ResponseResult.errorResult(AppHttpCodeEnum.SAVE_FAILED);
        } catch (Exception e) {
            log.error("新增频道失败{}",adchannel,e);
            throw new BusinessException("新增频道失败");
        }
    }

    @Override
    public ResponseResult update(AdChannel adChannel) {
        try {
            if(!ObjectUtil.isAllNotEmpty(adChannel,adChannel.getId())){
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
            }
            int res = adChannelMapper.update(adChannel);
            return res>0?ResponseResult.errorResult(AppHttpCodeEnum.SUCCESS):ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_FAILED);
        } catch (Exception e) {
            log.error("修改频道信息失败{}",adChannel,e);
            throw new BusinessException("修改频道信息失败");
        }
    }

    @Override
    public ResponseResult delete(Integer id) {
        try {
            if(ObjectUtil.isEmpty(id)){
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
            }
            //删除前 查询数据库中是否存在  判断状态是否有效 若有效则不能删除
            AdChannel channel = adChannelMapper.findById(id);
            if(ObjectUtil.isEmpty(channel)){
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
            }
            if(channel.getStatus()){
                //有效数据不能删除
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"有效数据不能删除");
            }
//            int i = 1/0;
            int res = adChannelMapper.delete(id);
            return res>0?ResponseResult.errorResult(AppHttpCodeEnum.SUCCESS):ResponseResult.errorResult(AppHttpCodeEnum.DELETE_FAILED);
        } catch (Exception e) {
            log.error("删除频道信息失败,频道id为{}",id,e);
            throw new BusinessException("删除频道信息失败");
        }
    }
}
