package com.heima.wemedia.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: tang
 * @date: Create in 9:20 2021/9/3
 * @description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class WemediaGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WemediaGatewayApplication.class);
    }
}
