package com.ynunicom.dd.contract.dingdingcontractrebuild.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCspaceAddRequest;
import com.dingtalk.api.response.OapiCspaceAddResponse;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import lombok.SneakyThrows;

/**
 * @author: jinye.Bai
 * @date: 2020/6/24 11:19
 */
public class AddToSpaceId {
    @SneakyThrows
    public static OapiCspaceAddResponse addToSpaceId(String accessToken, AppInfo appInfo,String code,String mediaId,String spaceId,String fileName){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/cspace/add");
        OapiCspaceAddRequest request = new OapiCspaceAddRequest();
        request.setAgentId(appInfo.getAgentId());
        request.setCode(code);
        request.setMediaId(mediaId);
        request.setSpaceId(spaceId);
        request.setName(fileName);
        request.setOverwrite(false);
        request.setHttpMethod("GET");
        OapiCspaceAddResponse response = client.execute(request,accessToken);
        if (!response.isSuccess()){
            throw new BussException(response.getErrmsg());
        }
        return response;
    }
}
