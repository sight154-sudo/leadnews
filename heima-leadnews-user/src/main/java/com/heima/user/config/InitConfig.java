package com.heima.user.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: tang
 * @date: Create in 19:36 2021/9/6
 * @description:
 */
@Configuration
@ComponentScan({"com.heima.common.aliyun","com.heima.common.fastdfs"})
public class InitConfig {
}
