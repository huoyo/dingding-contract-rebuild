package com.ynunicom.dd.contract.dingdingcontractrebuild.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRoleSimplelistRequest;
import com.dingtalk.api.response.OapiRoleSimplelistResponse;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import lombok.SneakyThrows;

import java.util.List;

/**
 * @author: jinye.Bai
 * @date: 2020/7/1 14:00
 */
public class GetAllUserInRole {
    @SneakyThrows
    public static List<OapiRoleSimplelistResponse.OpenEmpSimple> get(String roleId, String accessToken){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/role/simplelist");
        OapiRoleSimplelistRequest request = new OapiRoleSimplelistRequest();
        request.setRoleId(Long.valueOf(roleId));
        request.setOffset(0L);
        request.setSize(150L);
        OapiRoleSimplelistResponse response = client.execute(request, accessToken);
        if (!response.isSuccess()){
            throw new BussException(response.getErrmsg());
        }
        return response.getResult().getList();
    }
}
