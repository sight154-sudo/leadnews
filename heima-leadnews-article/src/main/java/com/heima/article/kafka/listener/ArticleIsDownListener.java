package com.heima.article.kafka.listener;

import com.alibaba.fastjson.JSON;
import com.heima.article.mapper.ApArticleConfigMapper;
import com.heima.common.constants.message.WmNewsMessageConstants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * @author: tang
 * @date: Create in 17:56 2021/9/11
 * @description:
 */
@Component
public class ArticleIsDownListener {

    @Autowired
    private ApArticleConfigMapper apArticleConfigMapper;

    @KafkaListener(topics = WmNewsMessageConstants.WM_NEWS_UP_OR_DOWN_TOPIC)
    public void getMessage(ConsumerRecord record){
        Optional<ConsumerRecord> optional = Optional.ofNullable(record);
        if(optional.isPresent()){
            Map<String,Object> map = JSON.parseObject(record.value().toString(),Map.class);
            Boolean enable = (Boolean) map.get("enable");
            Long articleId = Long.valueOf(map.get("articleId").toString());
            //修改文章状态
            apArticleConfigMapper.updateEnable(enable,articleId);
        }

    }
}
