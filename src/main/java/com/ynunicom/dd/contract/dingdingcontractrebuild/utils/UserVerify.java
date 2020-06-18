package com.ynunicom.dd.contract.dingdingcontractrebuild.utils;

import com.alibaba.fastjson.JSONObject;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: jinye.Bai
 * @date: 2020/6/18 18:29
 */
@Component
public class UserVerify {
    @Resource
    UserInfoService userInfoService;

    public boolean verify(String accessToken,String userId){
        JSONObject jsonObject = userInfoService.getUserInfo(accessToken,userId);
        return Integer.parseInt(jsonObject.getString("errcode")) == 0;
    }
}
