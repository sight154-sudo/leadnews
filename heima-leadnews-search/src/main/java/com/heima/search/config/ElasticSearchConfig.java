package com.heima.search.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: tang
 * @date: Create in 15:49 2021/9/14
 * @description:
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticSearchConfig {
    private String host;
    private Integer port;

    @Bean
    public RestHighLevelClient client(){
        System.out.println(host);
        System.out.println(port);
        return new RestHighLevelClient(RestClient.builder(new HttpHost(host,port,"http")));
    }
}
