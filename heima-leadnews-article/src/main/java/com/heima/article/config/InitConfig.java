package com.heima.article.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: tang
 * @date: Create in 21:01 2021/9/10
 * @description:
 */
@Configuration
@ComponentScan({"com.heima.common.jackson","com.heima.common.config"})
public class InitConfig {
}
