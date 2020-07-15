package com.ynunicom.dd.contract.dingdingcontractrebuild.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCspaceGetCustomSpaceRequest;
import com.dingtalk.api.response.OapiCspaceGetCustomSpaceResponse;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ResponseDto;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import lombok.SneakyThrows;

/**
 * @author: jinye.Bai
 * @date: 2020/7/14 10:29
 */
public class GetSpaceId {
    @SneakyThrows
    public static String getSpaceId(AppInfo appInfo,String accessToken){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/cspace/get_custom_space");
        OapiCspaceGetCustomSpaceRequest request = new OapiCspaceGetCustomSpaceRequest();
        request.setAgentId(appInfo.getAgentId());
        request.setDomain(appInfo.getDomain());
        request.setHttpMethod("GET");
        OapiCspaceGetCustomSpaceResponse response = client.execute(request,accessToken);
        if (!response.isSuccess()){
            throw new BussException(response.getErrmsg());
        }
        return response.getSpaceid();
    }
}
