package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCspaceAddToSingleChatRequest;
import com.dingtalk.api.response.OapiCspaceAddToSingleChatResponse;
import com.taobao.api.internal.util.WebUtils;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.FileSaver;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.UploadToDingPan;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author: jinye.Bai
 * @date: 2020/6/19 16:43
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    AppInfo appInfo;

    @Resource
    String filePath;

    @Resource
    FileSaver fileSaver;

    @Resource
    UploadToDingPan uploadToDingPan;

    @SneakyThrows
    @PostMapping
    public void test(@RequestParam("accessToken")String accessToken, MultipartFile file,String userId){
        String fileName = fileSaver.save(file);
        String dingdingFileId = uploadToDingPan.doUpload(new File(filePath+'/'+fileName),accessToken);
        OapiCspaceAddToSingleChatRequest request = new OapiCspaceAddToSingleChatRequest();
        request.setAgentId(appInfo.getAgentId());
        request.setUserid(userId);
        request.setMediaId(dingdingFileId);
        request.setFileName(file.getOriginalFilename());
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/cspace/add_to_single_chat?"+ WebUtils.buildQuery(request.getTextParams(),"utf-8"));
        OapiCspaceAddToSingleChatResponse response = client.execute(request, accessToken);
        if (!response.isSuccess()){
            throw new BussException("失败");
        }

    }
}
