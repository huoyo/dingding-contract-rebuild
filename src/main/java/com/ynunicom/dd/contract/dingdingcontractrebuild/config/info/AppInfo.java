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

}
