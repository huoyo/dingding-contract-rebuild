package com.ynunicom.dd.contract.dingdingcontractrebuild.config;

import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: jinye.Bai
 * @date: 2020/6/17 14:45
 */
@Configuration
public class AppInfoConfig {

    @Value("${dingding.app-key}")
    String appKey;

    @Value("${dingding.app-secret}")
    String appSecret;

    @Value("${dingding.agent-id}")
    String agentId;


    @Bean
    public AppInfo appInfo(){
        AppInfo appInfo = new AppInfo();
        appInfo.setAgentId(agentId);
        appInfo.setAppKey(appKey);
        appInfo.setAppSecret(appSecret);
        return appInfo;
    }

}
