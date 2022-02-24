package com.heima.behavior.kafka.listener;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.heima.behavior.mapper.ApFollowBehaviorMapper;
import com.heima.behavior.service.ApFollowBehaviorService;
import com.heima.common.constants.message.FollowBehaviorConstants;
import com.heima.model.behavior.dtos.ApFollowBehaviorDto;
import com.heima.model.behavior.pojos.ApFollowBehavior;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author: tang
 * @date: Create in 21:57 2021/9/11
 * @description:
 */
@Component
public class FollowBehaviorListener {
    @Autowired
    private ApFollowBehaviorService apFollowBehaviorService;

    @KafkaListener(topics = FollowBehaviorConstants.FOLLOW_BEHAVIOR_TOPIC)
    public void getMessage(ConsumerRecord record){
        Optional<ConsumerRecord> optional = Optional.ofNullable(record);
        if(optional.isPresent()){
            ApFollowBehaviorDto dto = JSON.parseObject(record.value().toString(), ApFollowBehaviorDto.class);
            if(ObjectUtil.isNotEmpty(dto)){
                apFollowBehaviorService.insert(dto);
            }
        }
    }
}
