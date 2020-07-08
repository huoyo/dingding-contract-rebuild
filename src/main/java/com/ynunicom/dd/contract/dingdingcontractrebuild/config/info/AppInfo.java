package com.ynunicom.dd.contract.dingdingcontractrebuild.config.info;

import lombok.Data;

/**
 * @author: jinye.Bai
 * @date: 2020/6/17 14:45
 */
@Data
public class AppInfo {

    private String appKey;

    private String appSecret;

    private String agentId;

    private String corpId;

    private String nonceStr;

    private String domain;

    private String contractSaverRole;

    private String superUserRole;

    private String contractSaverRoleGroup;

    private String contractSaverRoleId;

    private String financialDeptId;

    private String viceManagerId;

    private String managerId;

    private String legalDeptId;

}
