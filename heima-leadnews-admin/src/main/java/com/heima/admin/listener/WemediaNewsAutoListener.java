package com.heima.admin.listener;

import com.heima.admin.service.WemediaNewsAutoScanService;
import com.heima.common.constants.message.NewsAutoScanConstants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author: tang
 * @date: Create in 20:43 2021/9/8
 * @description:
 */
@Component
public class WemediaNewsAutoListener {

    @Autowired
    private WemediaNewsAutoScanService wemediaNewsAutoScanService;

    @KafkaListener(topics = NewsAutoScanConstants.WM_NEWS_AUTO_SCAN_TOPIC)
    public void autoScanRevicw(ConsumerRecord<String,String> record){
        Optional<ConsumerRecord<String, String>> optional = Optional.ofNullable(record);
        if(optional.isPresent()){
            wemediaNewsAutoScanService.autoScanByMediaNewsId(Integer.valueOf(record.value()));
        }
    }
}
