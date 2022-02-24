package com.heima.search.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: tang
 * @date: Create in 19:02 2021/9/12
 * @description:
 */
@Configuration
@ComponentScan({"com.heima.common.threadpool","com.heima.common.redis"})
public class InitConfig {
}
