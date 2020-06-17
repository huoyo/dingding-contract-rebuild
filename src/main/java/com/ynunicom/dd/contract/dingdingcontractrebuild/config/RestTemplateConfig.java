package com.ynunicom.dd.contract.dingdingcontractrebuild.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author: jinye.Bai
 * @date: 2020/6/17 17:21
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
