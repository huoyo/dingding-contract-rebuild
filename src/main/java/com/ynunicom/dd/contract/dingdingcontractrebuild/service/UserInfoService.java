package com.ynunicom.dd.contract.dingdingcontractrebuild.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: jinye.Bai
 * @date: 2020/6/17 15:45
 */
public interface UserInfoService {

    public JSONObject getList(String accessToken,Long deptId,Long offset,Long size,String order);

    public JSONObject getUserInfo(String accessToken,String userId);

    /**
     * 通过用户id列表批量查找详细信息
     * @param accessToken
     * @param userIdList
     * @return
     */
    public JSONArray getUserInfoByUserIdlist(String accessToken, List<String> userIdList);
}
