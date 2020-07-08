package com.ynunicom.dd.contract.dingdingcontractrebuild.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentListParentDeptsByDeptRequest;
import com.dingtalk.api.response.OapiDepartmentListParentDeptsByDeptResponse;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import lombok.SneakyThrows;

import java.util.List;

/**
 * @author: jinye.Bai
 * @date: 2020/7/8 14:20
 */
public class UpperDeptFoundByDeptId {
    @SneakyThrows
    public static String find(String accessToken, String deptId){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list_parent_depts_by_dept");
        OapiDepartmentListParentDeptsByDeptRequest request = new OapiDepartmentListParentDeptsByDeptRequest();
        request.setId(deptId);
        request.setHttpMethod("GET");
        OapiDepartmentListParentDeptsByDeptResponse response = client.execute(request, accessToken);
        if (!response.isSuccess()){
            throw new BussException(response.getErrmsg());
        }
        List<Long> deptIdList = response.getParentIds();
        return String.valueOf(deptIdList.get(deptIdList.size()-2));
    }
}
