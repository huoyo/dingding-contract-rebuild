package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCspaceGetCustomSpaceRequest;
import com.dingtalk.api.request.OapiCspaceGrantCustomSpaceRequest;
import com.dingtalk.api.response.OapiCspaceGetCustomSpaceResponse;
import com.dingtalk.api.response.OapiCspaceGrantCustomSpaceResponse;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ResponseDto;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.AddToSpaceId;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.DocToPDF;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.FileSaver;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.UploadToDingPan;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.Utf8Encoder;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
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

    @SneakyThrows
    @PostMapping("/addToSpace")
    public ResponseDto addToSpace(@RequestParam("accessToken")String accessToken,String userId,String mediaId,String code,String spaceId,String fileName) {
        return ResponseDto.success(AddToSpaceId.addToSpaceId(accessToken, appInfo, code, mediaId, spaceId, fileName));
    }

    @SneakyThrows
    @PostMapping("/docToPdf")
    public void docToPdf(MultipartFile doc, HttpServletResponse httpServletResponse){
        String fileName = FileSaver.save(filePath,doc);
        String pdfFileName = DocToPDF.trans(fileName,filePath);
        httpServletResponse.setHeader("Content-Type","application/json;charset=utf-8");
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename="+ pdfFileName);
        OutputStream outputStream = httpServletResponse.getOutputStream();
        File file = new File("/pdf/"+pdfFileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        while (fileInputStream.read(bytes)!=-1){
            outputStream.write(bytes);
        }
        outputStream.flush();
        outputStream.close();
        fileInputStream.close();
    }
}
