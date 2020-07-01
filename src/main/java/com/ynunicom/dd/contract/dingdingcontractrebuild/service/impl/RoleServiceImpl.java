package com.ynunicom.dd.contract.dingdingcontractrebuild.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.RoleService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.UserInfoService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: jinye.Bai
 * @date: 2020/7/1 11:09
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    UserInfoService userInfoService;

    @Resource
    AppInfo appInfo;

    @SneakyThrows
    @Override
    public List<String> getRoleByUserId(String userId, String accessToken) {
        JSONObject jsonObject = userInfoService.getUserInfo(accessToken,userId);
        if (jsonObject.getInteger("errcode")!=0){
            throw new BussException("查询用户信息失败");
        }
        JSONArray jsonArray = jsonObject.getJSONArray("roles");
        Iterator iterator = jsonArray.iterator();
        List<String> returner = new ArrayList<>();
        while (iterator.hasNext()){
            JSONObject innerJsonObject = (JSONObject) iterator.next();
            returner.add(innerJsonObject.getString("name"));
        }
        return returner;
    }
}
