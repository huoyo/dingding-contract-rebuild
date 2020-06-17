package com.ynunicom.dd.contract.dingdingcontractrebuild.config;

import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.ServiceInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: jinye.Bai
 * @date: 2020/6/17 17:25
 */
@Configuration
public class ServiceInfoConfig {

    @Value("${server.port}")
    private String port;

    @Bean
    public ServiceInfo serviceInfo(){
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setPort(port);
        return serviceInfo;
    }
}
