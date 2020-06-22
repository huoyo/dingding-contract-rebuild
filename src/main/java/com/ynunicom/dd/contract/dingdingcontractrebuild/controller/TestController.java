package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCspaceAddToSingleChatRequest;
import com.dingtalk.api.response.OapiCspaceAddToSingleChatResponse;
import com.taobao.api.internal.util.WebUtils;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ResponseDto;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.FileSaver;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.PushFileTo;
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
}
