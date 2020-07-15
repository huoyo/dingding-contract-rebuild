package com.ynunicom.dd.contract.dingdingcontractrebuild.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractInfoEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.ContractInfoMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.FileService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.UserInfoService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.DocToPDF;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.PushFileTo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.UserVerify;
import lombok.SneakyThrows;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: jinye.Bai
 * @date: 2020/6/28 16:57
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    TaskService taskService;

    @Resource
    String filePath;

    @Resource
    AppInfo appInfo;

    @Resource
    UserInfoService userInfoService;

    @Autowired
    ContractInfoMapper contractInfoMapper;

    @SneakyThrows
    private HttpServletResponse get(String accessToken, String fileName, HttpServletResponse httpServletResponse, String userId,boolean fileType){
        File file = null;
        if (fileType){
            file = new File(filePath+"/"+fileName);
        }
        else {
            file = new File("/pdf"+"/"+fileName);
        }
        if (!file.exists()){
            throw new BussException(fileName+"文件不存在");
        }
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
        FileInputStream fileInputStream = new FileInputStream(file);
        OutputStream outputStream = httpServletResponse.getOutputStream();
        byte[] bytes = new byte[1024];
        while(fileInputStream.read(bytes)!=-1){
            outputStream.write(bytes);
        }
        outputStream.flush();
        outputStream.close();
        fileInputStream.close();
        return httpServletResponse;
    }

    private boolean checkHavePdf(String fileName){
        File file = new File("/pdf/"+fileName);
        return file.exists();
    }

    @SneakyThrows
    @Override
    public boolean del(String accessToken, String fileName, String userId, String contractId,String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task==null){
            throw new BussException(taskId+"任务不存在");
        }
        Map<String,Object> map = taskService.getVariables(task.getId());
        ContractInfoEntity contractInfoEntityForMap = (ContractInfoEntity) map.get("contract");
        ContractInfoEntity contractInfoEntity = contractInfoMapper.selectById(contractId);
        if (contractInfoEntity==null){
            throw new BussException(contractId+"此合同不存在");
        }
        if (contractInfoEntity.getAttachmentFilePath1()!=null&&contractInfoEntity.getAttachmentFilePath1().equals(fileName)){
            contractInfoEntityForMap.setAttachmentFilePath1(null);
            contractInfoEntityForMap.setAttachmentDingPanid1(null);
            contractInfoEntity.setAttachmentFilePath1("");
            contractInfoEntity.setAttachmentDingPanid1("");
        }
        if (contractInfoEntity.getAttachmentFilePath2()!=null&&contractInfoEntity.getAttachmentFilePath2().equals(fileName)){
            contractInfoEntityForMap.setAttachmentFilePath2(null);
            contractInfoEntityForMap.setAttachmentDingPanid2(null);
            contractInfoEntity.setAttachmentFilePath2("");
            contractInfoEntity.setAttachmentDingPanid2("");
        }
        if (contractInfoEntity.getAttachmentFilePath3()!=null&&contractInfoEntity.getAttachmentFilePath3().equals(fileName)){
            contractInfoEntityForMap.setAttachmentFilePath3(null);
            contractInfoEntityForMap.setAttachmentDingPanid3(null);
            contractInfoEntity.setAttachmentFilePath3("");
            contractInfoEntity.setAttachmentDingPanid3("");
        }
        if (contractInfoEntity.getAttachmentFilePath4()!=null&&contractInfoEntity.getAttachmentFilePath4().equals(fileName)){
            contractInfoEntityForMap.setAttachmentFilePath4(null);
            contractInfoEntityForMap.setAttachmentDingPanid4(null);
            contractInfoEntity.setAttachmentFilePath4("");
            contractInfoEntity.setAttachmentDingPanid4("");
        }
        if (contractInfoEntity.getAttachmentFilePath5()!=null&&contractInfoEntity.getAttachmentFilePath5().equals(fileName)){
            contractInfoEntityForMap.setAttachmentFilePath5(null);
            contractInfoEntityForMap.setAttachmentDingPanid5(null);
            contractInfoEntity.setAttachmentFilePath5("");
            contractInfoEntity.setAttachmentDingPanid5("");
        }
        map.put("contract",contractInfoEntityForMap);
        taskService.setVariables(task.getId(),map);
        int result = contractInfoMapper.updateById(contractInfoEntity);
        return true;
    }

    @SneakyThrows
    @Override
    public HttpServletResponse getDoc(String accessToken, String fileName, HttpServletResponse httpServletResponse,String userId) {
        return get(accessToken,fileName,httpServletResponse,userId,true);
    }

    @SneakyThrows
    @Override
    public HttpServletResponse getPdf(String accessToken, String fileName, HttpServletResponse httpServletResponse, String userId) {
        if (checkHavePdf(fileName)){
            return get(accessToken,fileName,httpServletResponse,userId,false);
        }
        String[] docFileNames = fileName.split("\\.");
        StringBuilder docFileNameBuilder = new StringBuilder();
        for (int i = 0;i<docFileNames.length-1;i++) {
            docFileNameBuilder.append(docFileNames[i]);
        }
        StringBuilder tempBuilder = new StringBuilder(docFileNameBuilder);

        docFileNameBuilder.append(".docx");
        String docFileName = docFileNameBuilder.toString();
        String pdfFileName = null;
        try {
            pdfFileName = DocToPDF.trans(docFileName,filePath);
            return  get(accessToken,pdfFileName,httpServletResponse,userId,false);
        }
        catch (BussException e){
            docFileName = tempBuilder.append(".doc").toString();
            pdfFileName = DocToPDF.trans(docFileName,filePath);
            return  get(accessToken,pdfFileName,httpServletResponse,userId,false);
        }
    }

    @SneakyThrows
    @Override
    public HttpServletResponse getOther(String accessToken, String fileName, HttpServletResponse httpServletResponse, String userId) {
        File file = new File(filePath+"/"+fileName);
        if (!file.exists()){
            throw new BussException(fileName+"文件不存在");
        }
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
        OutputStream outputStream = httpServletResponse.getOutputStream();
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        while (fileInputStream.read(bytes)!=-1){
            outputStream.write(bytes);
        }
        outputStream.flush();
        outputStream.close();
        fileInputStream.close();
        return httpServletResponse;
    }

    @Override
    public boolean push(String accessToken, String mediaId,String fileName, String recvUserId) {
        return PushFileTo.pushToUser(recvUserId, mediaId, fileName , accessToken, appInfo);
    }
}
