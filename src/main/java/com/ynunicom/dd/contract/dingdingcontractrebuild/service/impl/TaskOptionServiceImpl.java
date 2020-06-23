package com.ynunicom.dd.contract.dingdingcontractrebuild.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.AttachmentEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractInfoEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractTemplateEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.AttachmentMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.ContractInfoMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.ContractTemplateMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.JudgePersonEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.PersonEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.ContractApplyRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.TaskOptionService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.UserInfoService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author: jinye.Bai
 * @date: 2020/6/18 18:21
 */
@Service
@Slf4j
public class TaskOptionServiceImpl implements TaskOptionService {
    @Resource
    UploadToDingPan uploadToDingPan;

    @Resource
    String filePath;

    @Resource
    AppInfo appInfo;

    @Resource
    UserInfoService userInfoService;

    @Autowired
    ContractInfoMapper contractInfoMapper;

    @Autowired
    AttachmentMapper attachmentMapper;

    @Autowired
    ContractTemplateMapper contractTemplateMapper;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Override
    public Task taskVarLoadFromOutSide(Task task, ContractApplyRequestBody contractApplyRequestBody, String accessToken){
        Map<String,Object> map = taskService.getVariables(task.getId());
        map.put("applyUserId",contractApplyRequestBody.getOrganizerUserId());
        ContractInfoEntity contractInfoEntityBefore = (ContractInfoEntity) map.get("contract");
        ContractInfoEntity contractInfoEntityComming = new ContractInfoEntity(contractApplyRequestBody);
        /**
         * 这里要把contractInfoEntityBefore和contractInfoEntityComming整合起来
         */
        contractInfoEntityBefore = ContractInfoEntityMerge.merge(contractInfoEntityBefore,contractInfoEntityComming);
        map.put("contract",contractInfoEntityBefore);
        map.put("currentIsOk",false);
        taskService.setVariables(task.getId(),map);
        return task;
    }

    private Task taskVarLoad(Task task,ContractApplyRequestBody contractApplyRequestBody,String accessToken){
        Map<String,Object> map = new HashMap<>();
        List<String> userIdList = StringsToList.trans(contractApplyRequestBody.getReviewerList());
        List<JudgePersonEntity> judgePersonEntityList = new ArrayList<>();
        for (String userId:
        userIdList){
            JSONObject jsonObject = userInfoService.getUserInfo(accessToken,userId);
            JudgePersonEntity judgePersonEntity = new JudgePersonEntity();
            PersonEntity personEntity = new PersonEntity(jsonObject.getString("name"),userId,jsonObject.getString("avatar"));
            judgePersonEntity.setPersonEntity(personEntity);
            judgePersonEntity.setIsOk(false);
            judgePersonEntityList.add(judgePersonEntity);
        }
        while (judgePersonEntityList.size()<7){
            JudgePersonEntity judgePersonEntity = new JudgePersonEntity();
            judgePersonEntity.setIsOk(true);
            judgePersonEntity.setComment("null");
            PersonEntity personEntity = new PersonEntity("null","null","null");
            judgePersonEntity.setPersonEntity(personEntity);
            judgePersonEntityList.add(judgePersonEntity);
        }
        map.put("stages",judgePersonEntityList);
        map.put("applyUserId",contractApplyRequestBody.getOrganizerUserId());
        map.put("contract",new ContractInfoEntity(contractApplyRequestBody));
        map.put("currentIsOk",false);
        taskService.setVariables(task.getId(),map);
        return task;
    }

    @SneakyThrows
    @Override
    public JSONArray getByAssigneeUserId(String accessToken, String userId) {
        if (UserVerify.verify(accessToken, userId, userInfoService)){
            throw new BussException("用户不存在");
        }
        JSONArray jsonArray = new JSONArray();
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(userId).list();
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (Task task :
                taskList) {
            Map<String,Object> map = taskService.getVariables(task.getId());
            map.put("taskId",task.getId());
            map.put("taskStage",task.getName());
            mapList.add(map);
        }
        jsonArray.addAll(mapList);
        return jsonArray;
    }

    @SneakyThrows
    @Override
    public JSONArray getByApplyUserId(String accessToken, String applyUserId) {
        if (UserVerify.verify(accessToken, applyUserId, userInfoService)){
            throw new BussException("用户不存在");
        }
        JSONArray jsonArray = new JSONArray();
        List<Task> taskListBefore = taskService.createTaskQuery().active().list();
        List<Task> taskList = new ArrayList<>();
        for (Task task:
                taskListBefore){
            Map<String,Object> map = taskService.getVariables(task.getId());
            if (map.get("applyUserId").equals(applyUserId)){
                taskList.add(task);
            }
        }
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (Task task :
                taskList) {
            Map<String,Object> map = taskService.getVariables(task.getId());
            map.put("taskId",task.getId());
            map.put("taskStage",task.getName());
            mapList.add(map);
        }
        jsonArray.addAll(mapList);
        return jsonArray;
    }

    @Transactional
    @Override
    public Map<String, Object> startNewInst(ContractApplyRequestBody contractApplyRequestBody, String accessToken) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("ContractApplyForYnUnicomRebuild");
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        task = taskVarLoad(task,contractApplyRequestBody,accessToken);
        Map<String,Object> map = taskService.getVariables(task.getId());


        ContractInfoEntity contractInfoEntity = (ContractInfoEntity) map.get("contract");
        String contractId = UUID.randomUUID().toString().replace("-","");
        contractInfoEntity.setId(contractId);


        //附件保存并上传钉盘,存入流程变量
        MultipartFile attachment = contractApplyRequestBody.getAttachment();
        String attachmentFileName = FileSaver.save(filePath,attachment);
        String attachmentMediaId = uploadToDingPan.doUpload(attachmentFileName,accessToken);
        if (!PushFileTo.pushToUser(contractApplyRequestBody.getOrganizerUserId(),attachmentMediaId,attachmentFileName,accessToken,appInfo)){
            log.info("文件:"+attachmentFileName+",mediaId:"+attachmentMediaId+",推送失败");
        }
        AttachmentEntity attachmentEntity = new AttachmentEntity(attachmentFileName,contractId,contractApplyRequestBody.getStandTemplateId(),attachmentFileName,attachmentMediaId);
        attachmentMapper.insert(attachmentEntity);
        contractInfoEntity.setAttachmentDingPanid(attachmentMediaId);
        contractInfoEntity.setAttachmentFilePath(attachmentFileName);
        String attachmentId = attachmentEntity.getId();


        //合同模板上传钉盘，存入流程变量
        ContractTemplateEntity contractTemplateEntity = contractTemplateMapper.selectById(contractApplyRequestBody.getStandTemplateId());
        String templateFileName = contractTemplateEntity.getFilePath();
        String templateMediaId = uploadToDingPan.doUpload(templateFileName,accessToken);
        if (!PushFileTo.pushToUser(contractApplyRequestBody.getOrganizerUserId(),templateMediaId,templateFileName,accessToken,appInfo)){
            log.info("文件:"+templateFileName+",mediaId:"+templateMediaId+",推送失败");
        }
        contractInfoEntity.setStandTemplateDingPanId(templateMediaId);


        //对方资质文件保存并上传钉盘，存入流程变量
        MultipartFile qualityFile = contractApplyRequestBody.getTheirQuality();
        String qualityFileName = FileSaver.save(filePath,qualityFile);
        String qualityFileMediaId = uploadToDingPan.doUpload(qualityFileName,accessToken);
        if (!PushFileTo.pushToUser(contractApplyRequestBody.getOrganizerUserId(),qualityFileMediaId,qualityFileName,accessToken,appInfo)){
            log.info("文件:"+qualityFileName+",mediaId:"+qualityFileMediaId+",推送失败");
        }
        contractInfoEntity.setTheirQualityFilePath(qualityFileName);
        contractInfoEntity.setTheirQualityDingPanId(qualityFileMediaId);


        //不使用标准模板的说明文件保存并上传钉盘，存入流程变量
        MultipartFile reason = contractApplyRequestBody.getReasonOfNotUsingStandTemplate();
        String reasonFileName = FileSaver.save(filePath,reason);
        String reasonMediaId = uploadToDingPan.doUpload(reasonFileName,accessToken);
        if (!PushFileTo.pushToUser(contractApplyRequestBody.getOrganizerUserId(),reasonMediaId,reasonFileName,accessToken,appInfo)){
            log.info("文件:"+reasonFileName+",mediaId:"+reasonMediaId+",推送失败");
        }
        contractInfoEntity.setReasonOfNotUsingStandTemplateFilePath(reasonFileName);
        contractInfoEntity.setReasonOfNotUsingStandTemplateDingPanId(reasonMediaId);

        //合同变量存入数据库
        contractInfoMapper.insert(contractInfoEntity);

        //结束任务
        runtimeService.updateBusinessKey(task.getProcessInstanceId(),contractId);
        taskService.complete(task.getId(),map);
        return map;
    }
}
