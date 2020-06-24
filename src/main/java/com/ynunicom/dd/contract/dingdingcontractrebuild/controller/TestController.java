package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCspaceAddToSingleChatRequest;
import com.dingtalk.api.request.OapiCspaceGetCustomSpaceRequest;
import com.dingtalk.api.request.OapiCspaceGrantCustomSpaceRequest;
import com.dingtalk.api.response.OapiCspaceAddToSingleChatResponse;
import com.dingtalk.api.response.OapiCspaceGetCustomSpaceResponse;
import com.dingtalk.api.response.OapiCspaceGrantCustomSpaceResponse;
import com.taobao.api.internal.util.WebUtils;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ResponseDto;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.FileSaver;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.PushFileTo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.UploadToDingPan;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * @author: jinye.Bai
 * @date: 2020/6/19 16:43
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Resource
    RuntimeService runtimeService;

    @Resource
    AppInfo appInfo;

    @Resource
    String filePath;

    @Resource
    UploadToDingPan uploadToDingPan;

    @SneakyThrows
    @PostMapping
    public ResponseDto test(@RequestParam("accessToken")String accessToken, MultipartFile file, String userId){
        String fileName = FileSaver.save(filePath,file);
        String mediaId = uploadToDingPan.doUpload(fileName,accessToken);
        if (PushFileTo.pushToUser(userId,mediaId,fileName,accessToken,appInfo)){
            return ResponseDto.success(fileName+"推送成功");
        }
        throw new BussException(fileName+"推送失败");

    }

    @GetMapping
    public ResponseDto del(){
        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().list();
        for (ProcessInstance processInstance:
        processInstanceList) {
            log.info(processInstance.getId());
            runtimeService.deleteProcessInstance(processInstance.getId(),"");
        }
        return null;
    }

    @SneakyThrows
    @GetMapping("/space")
    public ResponseDto space(@RequestParam("accessToken")String accessToken){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/cspace/get_custom_space");
        OapiCspaceGetCustomSpaceRequest request = new OapiCspaceGetCustomSpaceRequest();
        request.setAgentId(appInfo.getAgentId());
        request.setDomain(appInfo.getDomain());
        request.setHttpMethod("GET");
        OapiCspaceGetCustomSpaceResponse response = client.execute(request,accessToken);
        if (!response.isSuccess()){
            throw new BussException(response.getErrmsg());
        }
        return ResponseDto.success("spaceId",response.getSpaceid());
    }

    @SneakyThrows
    @GetMapping("/spaceAuth")
    public ResponseDto spaceAuth(@RequestParam("accessToken")String accessToken,@RequestParam("userId")String userId,@RequestParam("mediaId")String mediaId){
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/cspace/grant_custom_space");
        OapiCspaceGrantCustomSpaceRequest request = new OapiCspaceGrantCustomSpaceRequest();
        request.setAgentId(appInfo.getAgentId());
        request.setDomain(appInfo.getDomain());
        request.setType("download");
        request.setUserid(userId);
        request.setFileids(mediaId);
        request.setDuration(3600L);
        request.setHttpMethod("GET");
        OapiCspaceGrantCustomSpaceResponse response = client.execute(request,accessToken);
        if (!response.isSuccess()){
            throw new BussException(response.getErrmsg());
        }
        return ResponseDto.success(response.getErrmsg());
    }
}
