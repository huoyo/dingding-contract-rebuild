package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGetJsapiTicketRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiGetJsapiTicketResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ResponseDto;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.JsapiVerify;
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
            throw new BussException("token获取失败"+response.getErrmsg());
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
            throw new BussException("userId获取失败"+response.getErrmsg());
        }
        return ResponseDto.success("userId返回",response.getUserid());
    }

    @SneakyThrows
    @GetMapping("/getJsapi")
    public ResponseDto getJsapi(@RequestParam("accessToken")String accessToken,@RequestParam("url")String url){
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/get_jsapi_ticket");
        OapiGetJsapiTicketRequest req = new OapiGetJsapiTicketRequest();
        req.setTopHttpMethod("GET");
        OapiGetJsapiTicketResponse execute = client.execute(req, accessToken);
        if (!execute.isSuccess()){
            throw new BussException("jsapi鉴权失败"+execute.getErrmsg());
        }
        String ticket = execute.getTicket();
        long timeStamp = System.currentTimeMillis();
        String signature = JsapiVerify.sign(ticket,appInfo.getNonceStr(),timeStamp,url);
        JSONObject  jsonObject = new JSONObject();
        jsonObject.put("url",url);
        jsonObject.put("nonceStr",appInfo.getNonceStr());
        jsonObject.put("agentId",appInfo.getAgentId());
        jsonObject.put("timeStamp",timeStamp);
        jsonObject.put("corpId",appInfo.getCorpId());
        jsonObject.put("signature",signature);
        return ResponseDto.success(jsonObject);
    }

}
