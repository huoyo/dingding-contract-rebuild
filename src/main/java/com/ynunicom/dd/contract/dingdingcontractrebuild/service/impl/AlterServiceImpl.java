package com.ynunicom.dd.contract.dingdingcontractrebuild.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.AttachmentEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractInfoEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractInfoEntityForSelect;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractTemplateEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.AttachmentMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.ContractInfoMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.ContractInfoSelectMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.ContractTemplateMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.JudgePersonEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.PersonEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ProcessInstanceDefKey;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.ContractAlterRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.ContractApplyRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.AlterService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: jinye.Bai
 * @date: 2020/7/1 9:38
 */
@Service
@Slf4j
public class AlterServiceImpl implements AlterService {

    @Autowired
    ContractTemplateMapper contractTemplateMapper;

    @Autowired
    AttachmentMapper attachmentMapper;

    @Resource
    AppInfo appInfo;

    @Resource
    UserInfoService userInfoService;

    @Resource
    String filePath;

    @Resource
    UploadToDingPan uploadToDingPan;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    ContractInfoMapper contractInfoMapper;

    @Autowired
    ContractInfoSelectMapper contractInfoSelectMapper;

    @Resource
    ProcessInstanceDefKey alterProcessInstanceDefKey;

    private Task taskVarLoad(Task task, ContractAlterRequestBody contractAlterRequestBody,ContractInfoEntity beforeContractInfoEntity, String accessToken){
        Map<String,Object> map = new HashMap<>();
        List<String> userIdList = StringsToList.trans(contractAlterRequestBody.getReviewerList());
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
        ContractInfoEntity contractInfoEntity = new ContractInfoEntity(contractAlterRequestBody);
        contractInfoEntity = (ContractInfoEntity) ContractInfoEntityMerge.merge(beforeContractInfoEntity,contractInfoEntity);
        map.put("stages",judgePersonEntityList);
        map.put("applyUserId",contractAlterRequestBody.getOrganizerUserId());
        map.put("contract",contractInfoEntity);
        map.put("currentIsOk",false);
        map.put("contractSaverRole",appInfo.getContractSaverRole());
        taskService.setVariables(task.getId(),map);
        return task;
    }

    @SneakyThrows
    @Transactional
    @Override
    public Map<String, Object> alterApply(ContractAlterRequestBody contractAlterRequestBody, String accessToken, String userId) {
        if (!"alter".equals(contractAlterRequestBody.getMethod())&&!"continue".equals(contractAlterRequestBody.getMethod())&&!"preEnd".equals(contractAlterRequestBody.getMethod())){
            throw new BussException("请求方法不合理");
        }
        String id = contractAlterRequestBody.getContractId();
        ContractInfoEntity beforeContractInfoEntity = contractInfoMapper.selectById(id);
        if (beforeContractInfoEntity==null||!"running".equals(beforeContractInfoEntity.getStatu())){
            throw new BussException("合同id"+contractAlterRequestBody.getContractId()+"不在履行中");
        }
        if (!userId.equals(beforeContractInfoEntity.getContractRunnerUserId())){
            throw new BussException("用户"+userId+"不是合同"+contractAlterRequestBody.getContractId()+"的履行人，只有合同履行人可以发起合同的修改，续签，提前终止");
        }

        //开启流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(alterProcessInstanceDefKey.getKey());
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        //开始塞入流程变量
        task = taskVarLoad(task,contractAlterRequestBody,beforeContractInfoEntity,accessToken);
        Map<String,Object> map = taskService.getVariables(task.getId());
        ContractInfoEntity contractInfoEntity = (ContractInfoEntity) map.get("contract");

        //附件1保存并上传钉盘,存入流程变量
        MultipartFile attachment = contractAlterRequestBody.getAttachment1();
        if (attachment!=null&&!attachment.isEmpty()){
            String attachmentFileName = FileSaver.save(filePath,attachment);
            String attachmentMediaId = uploadToDingPan.doUpload(attachmentFileName,accessToken);
            if (!PushFileTo.pushToUser(contractAlterRequestBody.getOrganizerUserId(),attachmentMediaId,attachmentFileName,accessToken,appInfo)){
                log.info("文件:"+attachmentFileName+",mediaId:"+attachmentMediaId+",推送失败");
            }
            AttachmentEntity attachmentEntity = new AttachmentEntity(attachmentFileName,id,contractAlterRequestBody.getStandTemplateId(),attachmentFileName,attachmentMediaId);
            attachmentMapper.insert(attachmentEntity);
            contractInfoEntity.setAttachmentDingPanid1(attachmentMediaId);
            contractInfoEntity.setAttachmentFilePath1(attachmentFileName);
            String attachmentId = attachmentEntity.getId();
        }

        //附件2保存并上传钉盘,存入流程变量
        attachment = contractAlterRequestBody.getAttachment2();
        if (attachment!=null&&!attachment.isEmpty()){
            String attachmentFileName = FileSaver.save(filePath,attachment);
            String attachmentMediaId = uploadToDingPan.doUpload(attachmentFileName,accessToken);
            if (!PushFileTo.pushToUser(contractAlterRequestBody.getOrganizerUserId(),attachmentMediaId,attachmentFileName,accessToken,appInfo)){
                log.info("文件:"+attachmentFileName+",mediaId:"+attachmentMediaId+",推送失败");
            }
            AttachmentEntity attachmentEntity = new AttachmentEntity(attachmentFileName,id,contractAlterRequestBody.getStandTemplateId(),attachmentFileName,attachmentMediaId);
            attachmentMapper.insert(attachmentEntity);
            contractInfoEntity.setAttachmentDingPanid2(attachmentMediaId);
            contractInfoEntity.setAttachmentFilePath2(attachmentFileName);
            String attachmentId = attachmentEntity.getId();
        }

        //附件3保存并上传钉盘,存入流程变量
        attachment = contractAlterRequestBody.getAttachment3();
        if (attachment!=null&&!attachment.isEmpty()){
            String attachmentFileName = FileSaver.save(filePath,attachment);
            String attachmentMediaId = uploadToDingPan.doUpload(attachmentFileName,accessToken);
            if (!PushFileTo.pushToUser(contractAlterRequestBody.getOrganizerUserId(),attachmentMediaId,attachmentFileName,accessToken,appInfo)){
                log.info("文件:"+attachmentFileName+",mediaId:"+attachmentMediaId+",推送失败");
            }
            AttachmentEntity attachmentEntity = new AttachmentEntity(attachmentFileName,id,contractAlterRequestBody.getStandTemplateId(),attachmentFileName,attachmentMediaId);
            attachmentMapper.insert(attachmentEntity);
            contractInfoEntity.setAttachmentDingPanid3(attachmentMediaId);
            contractInfoEntity.setAttachmentFilePath3(attachmentFileName);
            String attachmentId = attachmentEntity.getId();
        }


        //合同模板上传钉盘，存入流程变量
        if (!contractAlterRequestBody.getStandTemplateId().isEmpty()){
            ContractTemplateEntity contractTemplateEntity = contractTemplateMapper.selectById(contractAlterRequestBody.getStandTemplateId());
            String templateFileName = contractTemplateEntity.getFilePath();
            String templateMediaId = uploadToDingPan.doUpload(templateFileName,accessToken);
            if (!PushFileTo.pushToUser(contractAlterRequestBody.getOrganizerUserId(),templateMediaId,templateFileName,accessToken,appInfo)){
                log.info("文件:"+templateFileName+",mediaId:"+templateMediaId+",推送失败");
            }
            contractInfoEntity.setStandTemplateDingPanId(templateMediaId);
        }


        //对方资质文件保存并上传钉盘，存入流程变量
        if (!contractAlterRequestBody.getTheirQuality().isEmpty()){
            MultipartFile qualityFile = contractAlterRequestBody.getTheirQuality();
            String qualityFileName = FileSaver.save(filePath,qualityFile);
            String qualityFileMediaId = uploadToDingPan.doUpload(qualityFileName,accessToken);
            if (!PushFileTo.pushToUser(contractAlterRequestBody.getOrganizerUserId(),qualityFileMediaId,qualityFileName,accessToken,appInfo)){
                log.info("文件:"+qualityFileName+",mediaId:"+qualityFileMediaId+",推送失败");
            }
            contractInfoEntity.setTheirQualityFilePath(qualityFileName);
            contractInfoEntity.setTheirQualityDingPanId(qualityFileMediaId);
        }


        //不使用标准模板的说明文件保存并上传钉盘，存入流程变量
        if (!contractAlterRequestBody.getReasonOfNotUsingStandTemplate().isEmpty()){
            MultipartFile reason = contractAlterRequestBody.getReasonOfNotUsingStandTemplate();
            String reasonFileName = FileSaver.save(filePath,reason);
            String reasonMediaId = uploadToDingPan.doUpload(reasonFileName,accessToken);
            if (!PushFileTo.pushToUser(contractAlterRequestBody.getOrganizerUserId(),reasonMediaId,reasonFileName,accessToken,appInfo)){
                log.info("文件:"+reasonFileName+",mediaId:"+reasonMediaId+",推送失败");
            }
            contractInfoEntity.setReasonOfNotUsingStandTemplateFilePath(reasonFileName);
            contractInfoEntity.setReasonOfNotUsingStandTemplateDingPanId(reasonMediaId);
        }

        //合同正文保存并上传钉盘，存入流程变量
        if (!contractAlterRequestBody.getContractText().isEmpty()){
            MultipartFile contractText = contractAlterRequestBody.getContractText();
            String contractTextFileName = FileSaver.save(filePath,contractText);
            if (contractTextFileName!=null){
                String contractTextMediaId = uploadToDingPan.doUpload(contractTextFileName,accessToken);
                if (!PushFileTo.pushToUser(contractAlterRequestBody.getOrganizerUserId(),contractTextMediaId,contractTextFileName,accessToken,appInfo)){
                    log.info("文件:"+contractTextFileName+",mediaId:"+contractTextMediaId+",推送失败");
                }
                contractInfoEntity.setContractTextFilePath(contractTextFileName);
                contractInfoEntity.setContractTextDingPanId(contractTextMediaId);
            }
        }

        //合同变量 不能 存入数据库,更新的数据，在合同管理员签章存档后才更新覆盖原来的合同数据
        //int result = contractInfoMapper.updateById(contractInfoEntity);

        //结束任务
        runtimeService.updateBusinessKey(task.getProcessInstanceId(),id);
        contractInfoEntity.setStatu(contractAlterRequestBody.getMethod()+"ing");
        contractInfoEntity.setSign(0);
        contractInfoEntity.setSave(0);
        map.put("contract",contractInfoEntity);
        map.put("method",contractAlterRequestBody.getMethod());
        map.put("alterReason",contractAlterRequestBody.getReason());
        taskService.complete(task.getId(),map);
        return map;
    }
}
