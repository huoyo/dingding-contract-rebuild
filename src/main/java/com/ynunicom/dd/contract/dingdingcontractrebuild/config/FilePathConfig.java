package com.ynunicom.dd.contract.dingdingcontractrebuild.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: jinye.Bai
 * @date: 2020/6/19 10:51
 */
@Configuration
public class FilePathConfig {

    @Bean
    public String filePath(){ return "./contractFile"; }


}
