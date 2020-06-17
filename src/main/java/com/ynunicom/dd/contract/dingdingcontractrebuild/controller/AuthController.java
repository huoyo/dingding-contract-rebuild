package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ResponseDto;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: jinye.Bai
 * @date: 2020/6/17 14:39
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Resource
    AppInfo appInfo;

    @SneakyThrows
    @GetMapping("/getToken")
    public ResponseDto getToken(){
        DefaultDingTalkClient defaultDingTalkClient = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest oapiGettokenRequest = new OapiGettokenRequest();
        oapiGettokenRequest.setAppkey(appInfo.getAppKey());
        oapiGettokenRequest.setAppsecret(appInfo.getAppSecret());
        oapiGettokenRequest.setHttpMethod("GET");
        OapiGettokenResponse response = defaultDingTalkClient.execute(oapiGettokenRequest);
        if(!response.isSuccess()){
            throw new BussException("token获取失败");
        }
        return ResponseDto.success("token返回",response.getAccessToken());
    }

    @SneakyThrows
    @GetMapping("/getUserId")
    public ResponseDto getUserId(@RequestParam("requestAuthCode") String requestAuthCode,@RequestParam("accessToken")String accessToken){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(requestAuthCode);
        request.setHttpMethod("GET");
        OapiUserGetuserinfoResponse response = client.execute(request, accessToken);
        if (!response.isSuccess()){
            throw new BussException("userId获取失败");
        }
        return ResponseDto.success("userId返回",response.getUserid());
    }

}
