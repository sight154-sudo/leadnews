package com.heima.admin.job;

import cn.hutool.core.collection.CollUtil;
import com.heima.admin.feign.WmNewsFeign;
import com.heima.admin.service.WemediaNewsAutoScanService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author: tang
 * @date: Create in 18:45 2021/9/9
 * @description:
 */
@Component
@Slf4j
public class WmNewsScheduleJob {

    @Autowired
    private WmNewsFeign wmNewsFeign;

    @Autowired
    private WemediaNewsAutoScanService wemediaNewsAutoScanService;

    @XxlJob("wemediaAutoScanJob")
    public ReturnT<String> scheduleReviewWemedia(String param) {
        System.out.println("定时器开始执行");
        log.info("审核定时器开始执行");
        List<Integer> ids = wmNewsFeign.findRelease();
        if (CollUtil.isNotEmpty(ids)) {
            ids.stream().forEach(id->wemediaNewsAutoScanService.autoScanByMediaNewsId(id));
        }
        log.info("审核定时器执行结束{}",ids);
        return ReturnT.SUCCESS;
    }
}
