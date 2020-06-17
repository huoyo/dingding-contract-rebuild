package com.ynunicom.dd.contract.dingdingcontractrebuild.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author: jinye.Bai
 * @date: 2020/6/17 15:19
 */
public interface DeptService {
    public JSONObject getList(String accessToken, Boolean fetchChild, String id);
}
