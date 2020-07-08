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

    @Value("${dingding.corp-id}")
    String corpId;

    @Value("${dingding.nonceStr}")
    String nonceStr;

    @Value("${dingding.domain}")
    String domain;

    @Value("${dingding.contract-saver-role}")
    String contractSaverRole;

    @Value("${dingding.super-user-role}")
    String superUserRole;

    @Value("${dingding.contract-saver-role-group}")
    String contractSaverRoleGroup;

    @Value("${dingding.contract-saver-role-id}")
    String contractSaverRoleId;

    @Value("${dingding.financial-Dept-Id}")
    String financialDeptId;

    @Value("${dingding.legal-Dept-Id}")
    String legalDeptId;

    @Value("${dingding.viceManager-Id}")
    String viceManagerId;

    @Value("${dingding.manager-Id}")
    String managerId;


    @Bean("appInfo")
    public AppInfo appInfo(){
        AppInfo appInfo = new AppInfo();
        appInfo.setAgentId(agentId);
        appInfo.setAppKey(appKey);
        appInfo.setAppSecret(appSecret);
        appInfo.setCorpId(corpId);
        appInfo.setNonceStr(nonceStr);
        appInfo.setDomain(domain);
        appInfo.setContractSaverRole(contractSaverRole);
        appInfo.setSuperUserRole(superUserRole);
        appInfo.setContractSaverRoleGroup(contractSaverRoleGroup);
        appInfo.setContractSaverRoleId(contractSaverRoleId);
        appInfo.setFinancialDeptId(financialDeptId);
        appInfo.setLegalDeptId(legalDeptId);
        appInfo.setViceManagerId(viceManagerId);
        appInfo.setManagerId(managerId);
        return appInfo;
    }

}
