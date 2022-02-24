package com.heima.behavior;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: tang
 * @date: Create in 21:12 2021/9/11
 * @description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@ServletComponentScan
@MapperScan("com.heima.behavior.mapper")
public class BehaviorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BehaviorApplication.class);
    }
}
