package com.ynunicom.dd.contract.dingdingcontractrebuild.service;

import com.alibaba.fastjson.JSONArray;

/**
 * @author: jinye.Bai
 * @date: 2020/6/18 18:20
 */
public interface TaskOptionService {

    public JSONArray getByAssigneeUserId(String accessToken , String userId);

    public JSONArray getByApplyUserId(String accessToken , String applyUserId);

}
