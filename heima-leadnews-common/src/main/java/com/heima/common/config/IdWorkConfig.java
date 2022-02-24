package com.heima.common.config;

import com.heima.utils.common.SnowflakeIdWorker;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author: tang
 * @date: Create in 19:01 2021/9/12
 * @description:
 */
@Configuration
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "mybatis")
@Data
public class IdWorkConfig {
    @Value("${datacenter-id}")
    private Long datacenterId;

    @Value("${workerId}")
    private Long workerId;

    @Bean
    public SnowflakeIdWorker snowflakeIdWorker(){
        return new SnowflakeIdWorker(workerId,datacenterId);
    }
}
