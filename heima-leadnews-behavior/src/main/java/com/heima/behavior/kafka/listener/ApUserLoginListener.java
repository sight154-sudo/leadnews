package com.heima.behavior.kafka.listener;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.heima.behavior.mapper.ApBehaviorEntryMapper;
import com.heima.behavior.service.ApBehaviorEntryService;
import com.heima.common.constants.message.UserLoginMessageConstants;
import com.heima.common.constants.message.WmNewsMessageConstants;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * @author: tang
 * @date: Create in 21:26 2021/9/11
 * @description:
 */
@Component
public class ApUserLoginListener {
    @Autowired
    private ApBehaviorEntryService apBehaviorEntryService;

    @KafkaListener(topics = UserLoginMessageConstants.USER_LOGIN_MESSAGE)
    public void getMessage(ConsumerRecord record){
        Optional<ConsumerRecord> optional = Optional.ofNullable(record);
        if(optional.isPresent()){
            ApBehaviorEntry apBehaviorEntry = JSON.parseObject(record.value().toString(),ApBehaviorEntry.class);
            //先查询
            ApBehaviorEntry entry = apBehaviorEntryService.findByTypeAndEntryId(apBehaviorEntry.getType(), apBehaviorEntry.getEntryId());
            if(ObjectUtil.isEmpty(entry)){
                apBehaviorEntry.setCreatedTime(new Date());
                apBehaviorEntryService.insertApBehaviorEntry(apBehaviorEntry);
            }
        }

    }
}
