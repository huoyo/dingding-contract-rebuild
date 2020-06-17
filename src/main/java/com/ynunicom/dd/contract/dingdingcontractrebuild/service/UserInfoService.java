package com.ynunicom.dd.contract.dingdingcontractrebuild.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: jinye.Bai
 * @date: 2020/6/17 15:45
 */
public interface UserInfoService {

    public JSONObject getList(String accessToken,Long deptId,Long offset,Long size,String order);

    public JSONObject getUserInfo(String accessToken,String userId);
}
