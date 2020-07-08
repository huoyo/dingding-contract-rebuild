package com.ynunicom.dd.contract.dingdingcontractrebuild.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserSimplelistRequest;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserSimplelistResponse;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.PersonEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.UserInfoService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author: jinye.Bai
 * @date: 2020/6/17 15:47
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @SneakyThrows
    @Override
    public JSONObject getList(String accessToken, Long deptId, Long offset, Long size, String order) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/listbypage");
        OapiUserSimplelistRequest request = new OapiUserSimplelistRequest();
        request.setDepartmentId(deptId);
        request.setOffset(offset);
        request.setSize(size);
        request.setHttpMethod("GET");
        if (!Objects.requireNonNull(order).isEmpty()){
            request.setOrder(order);
        }
        OapiUserSimplelistResponse response = client.execute(request, accessToken);
        if (!response.isSuccess()){
            throw new BussException("获取人员列表失败"+response.getErrmsg());
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userList",response.getUserlist());
        return jsonObject;

    }

    @SneakyThrows
    @Override
    public JSONObject getUserInfo(String accessToken, String userId) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid(userId);
        request.setHttpMethod("GET");
        OapiUserGetResponse response = client.execute(request, accessToken);
        if (!response.isSuccess()){
            throw new BussException("获取用户信息失败"+response.getErrmsg());
        }
        JSONObject jsonObject = JSONObject.parseObject(response.getBody());
        return jsonObject;
    }

    @Override
    public JSONArray getUserInfoByUserIdlist(String accessToken, List<String> userIdList) {
        JSONArray jsonArray = new JSONArray();
        for (String userId :
                userIdList) {
            JSONObject innerJson = getUserInfo(accessToken, userId);
            PersonEntity personEntity = new PersonEntity(innerJson.getString("name"),innerJson.getString("userid"),innerJson.getString("avatar"),innerJson.getJSONArray("department").toJSONString());
            jsonArray.add(personEntity);
        }
        return jsonArray;
    }

}
