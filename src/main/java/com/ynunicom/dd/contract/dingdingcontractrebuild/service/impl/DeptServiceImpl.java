package com.ynunicom.dd.contract.dingdingcontractrebuild.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentGetRequest;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.DeptService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author: jinye.Bai
 * @date: 2020/6/17 15:21
 */
@Service
public class DeptServiceImpl implements DeptService {
    @SneakyThrows
    @Override
    public JSONObject getList(String accessToken, Boolean fetchChild, String id) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        if (!"".equals(Objects.requireNonNull(id))){
            request.setId(id);
        }
        request.setFetchChild(fetchChild);
        request.setHttpMethod("GET");
        OapiDepartmentListResponse response = client.execute(request, accessToken);
        if (!response.isSuccess()){
            throw new BussException("获取部门列表失败"+response.getErrmsg());
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("deptList",response.getDepartment());
        return jsonObject;
    }

    @SneakyThrows
    @Override
    public Map<String,String> getByDeptId(String accessToken, String deptId) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
        OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
        request.setId(deptId);
        request.setHttpMethod("GET");
        OapiDepartmentGetResponse response = client.execute(request, accessToken);
        if (!response.isSuccess()){
            throw new BussException(response.getErrmsg());
        }
        Map<String,String> map = new HashMap<>();
        map.put("name",response.getName());
        map.put("deptId",String.valueOf(response.getId()));
        return map;
    }
}
