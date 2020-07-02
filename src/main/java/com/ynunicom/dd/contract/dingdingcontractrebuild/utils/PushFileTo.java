package com.ynunicom.dd.contract.dingdingcontractrebuild.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCspaceAddToSingleChatRequest;
import com.dingtalk.api.response.OapiCspaceAddToSingleChatResponse;
import com.taobao.api.internal.util.WebUtils;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import lombok.SneakyThrows;

/**
 * @author: jinye.Bai
 * @date: 2020/6/22 9:03
 */

public class PushFileTo {


    @SneakyThrows
    public static Boolean pushToUser(String userId, String mediaId, String fileName, String accessToken, AppInfo appInfo){
        OapiCspaceAddToSingleChatRequest request = new OapiCspaceAddToSingleChatRequest();
        request.setAgentId(appInfo.getAgentId());
        request.setUserid(userId);
        request.setMediaId(mediaId);
        request.setFileName(fileName);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/cspace/add_to_single_chat?"+ WebUtils.buildQuery(request.getTextParams(),"utf-8"));
        OapiCspaceAddToSingleChatResponse response = client.execute(request, accessToken);
        if (!response.isSuccess()){
            throw new BussException(response.getErrmsg());
        }
        return true;
    }

}
