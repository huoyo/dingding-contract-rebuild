package com.ynunicom.dd.contract.dingdingcontractrebuild.utils;

import com.alibaba.fastjson.JSONObject;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.UserInfoService;

/**
 * @author: jinye.Bai
 * @date: 2020/6/18 18:29
 */
public class UserVerify {

    public static boolean verify(String accessToken,String userId,UserInfoService userInfoService){
        JSONObject jsonObject = userInfoService.getUserInfo(accessToken,userId);
        return Integer.parseInt(jsonObject.getString("errcode")) != 0;
    }
}
