package com.ynunicom.dd.contract.dingdingcontractrebuild.service.impl;

import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.AttachmentEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractInfoEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractTemplateEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.AttachmentMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.ContractInfoMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.ContractTemplateMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.ContractRecorrectRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.RecorrectService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.TaskOptionService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.FileSaver;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.PushFileTo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.UploadToDingPan;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

/**
 * @author: jinye.Bai
 * @date: 2020/6/23 18:57
 */
@Service
@Slf4j
public class RecorrectServiceImpl implements RecorrectService {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Resource
    AttachmentMapper attachmentMapper;

    @Resource
    UploadToDingPan uploadToDingPan;

    @Resource
    String filePath;

    @Resource
    AppInfo appInfo;

    @Resource
    ContractTemplateMapper contractTemplateMapper;

    @Resource
    ContractInfoMapper contractInfoMapper;

    @Resource
    TaskOptionService taskOptionService;

    @Override
    @Transactional
    public Map<String, Object> recorrect(String accessToken, ContractRecorrectRequestBody contractRecorrectRequestBody) {
        Task task = taskService.createTaskQuery().taskId(contractRecorrectRequestBody.getTaskId()).singleResult();
        if (task==null){
            throw new FlowableObjectNotFoundException(contractRecorrectRequestBody.getTaskId()+"任务没有找到");
        }
        task = taskOptionService.taskVarLoadFromOutSide(task,contractRecorrectRequestBody,accessToken);
        Map<String,Object> map = taskService.getVariables(task.getId());


        ContractInfoEntity contractInfoEntity = (ContractInfoEntity) map.get("contract");
        String contractId = UUID.randomUUID().toString().replace("-","");
        contractInfoEntity.setId(contractId);


        //附件1保存并上传钉盘,存入流程变量
        MultipartFile attachment = contractRecorrectRequestBody.getAttachment1();
        if (attachment!=null&&!attachment.isEmpty()){
            String attachmentFileName = FileSaver.save(filePath,attachment);
            String attachmentMediaId = uploadToDingPan.doUpload(attachmentFileName,accessToken);
            if (!PushFileTo.pushToUser(contractRecorrectRequestBody.getOrganizerUserId(),attachmentMediaId,attachmentFileName,accessToken,appInfo)){
                log.info("文件:"+attachmentFileName+",mediaId:"+attachmentMediaId+",推送失败");
            }
            AttachmentEntity attachmentEntity = new AttachmentEntity(attachmentFileName,contractId,contractRecorrectRequestBody.getStandTemplateId(),attachmentFileName,attachmentMediaId);
            attachmentMapper.insert(attachmentEntity);
            contractInfoEntity.setAttachmentDingPanid1(attachmentMediaId);
            contractInfoEntity.setAttachmentFilePath1(attachmentFileName);
            String attachmentId = attachmentEntity.getId();
        }

        //附件2保存并上传钉盘,存入流程变量
        attachment = contractRecorrectRequestBody.getAttachment2();
        if (attachment!=null&&!attachment.isEmpty()){
            String attachmentFileName = FileSaver.save(filePath,attachment);
            String attachmentMediaId = uploadToDingPan.doUpload(attachmentFileName,accessToken);
            if (!PushFileTo.pushToUser(contractRecorrectRequestBody.getOrganizerUserId(),attachmentMediaId,attachmentFileName,accessToken,appInfo)){
                log.info("文件:"+attachmentFileName+",mediaId:"+attachmentMediaId+",推送失败");
            }
            AttachmentEntity attachmentEntity = new AttachmentEntity(attachmentFileName,contractId,contractRecorrectRequestBody.getStandTemplateId(),attachmentFileName,attachmentMediaId);
            attachmentMapper.insert(attachmentEntity);
            contractInfoEntity.setAttachmentDingPanid2(attachmentMediaId);
            contractInfoEntity.setAttachmentFilePath2(attachmentFileName);
            String attachmentId = attachmentEntity.getId();
        }

        //附件3保存并上传钉盘,存入流程变量
        attachment = contractRecorrectRequestBody.getAttachment3();
        if (attachment!=null&&!attachment.isEmpty()){
            String attachmentFileName = FileSaver.save(filePath,attachment);
            String attachmentMediaId = uploadToDingPan.doUpload(attachmentFileName,accessToken);
            if (!PushFileTo.pushToUser(contractRecorrectRequestBody.getOrganizerUserId(),attachmentMediaId,attachmentFileName,accessToken,appInfo)){
                log.info("文件:"+attachmentFileName+",mediaId:"+attachmentMediaId+",推送失败");
            }
            AttachmentEntity attachmentEntity = new AttachmentEntity(attachmentFileName,contractId,contractRecorrectRequestBody.getStandTemplateId(),attachmentFileName,attachmentMediaId);
            attachmentMapper.insert(attachmentEntity);
            contractInfoEntity.setAttachmentDingPanid3(attachmentMediaId);
            contractInfoEntity.setAttachmentFilePath3(attachmentFileName);
            String attachmentId = attachmentEntity.getId();
        }


        //合同模板上传钉盘，存入流程变量
        if (!contractRecorrectRequestBody.getStandTemplateId().isEmpty()){
            ContractTemplateEntity contractTemplateEntity = contractTemplateMapper.selectById(contractRecorrectRequestBody.getStandTemplateId());
            String templateFileName = contractTemplateEntity.getFilePath();
            String templateMediaId = uploadToDingPan.doUpload(templateFileName,accessToken);
            if (!PushFileTo.pushToUser(contractRecorrectRequestBody.getOrganizerUserId(),templateMediaId,templateFileName,accessToken,appInfo)){
                log.info("文件:"+templateFileName+",mediaId:"+templateMediaId+",推送失败");
            }
            contractInfoEntity.setStandTemplateDingPanId(templateMediaId);
        }


        //对方资质文件保存并上传钉盘，存入流程变量
        if (!contractRecorrectRequestBody.getTheirQuality().isEmpty()){
            MultipartFile qualityFile = contractRecorrectRequestBody.getTheirQuality();
            String qualityFileName = FileSaver.save(filePath,qualityFile);
            String qualityFileMediaId = uploadToDingPan.doUpload(qualityFileName,accessToken);
            if (!PushFileTo.pushToUser(contractRecorrectRequestBody.getOrganizerUserId(),qualityFileMediaId,qualityFileName,accessToken,appInfo)){
                log.info("文件:"+qualityFileName+",mediaId:"+qualityFileMediaId+",推送失败");
            }
            contractInfoEntity.setTheirQualityFilePath(qualityFileName);
            contractInfoEntity.setTheirQualityDingPanId(qualityFileMediaId);
        }


        //不使用标准模板的说明文件保存并上传钉盘，存入流程变量
        if (!contractRecorrectRequestBody.getReasonOfNotUsingStandTemplate().isEmpty()){
            MultipartFile reason = contractRecorrectRequestBody.getReasonOfNotUsingStandTemplate();
            String reasonFileName = FileSaver.save(filePath,reason);
            String reasonMediaId = uploadToDingPan.doUpload(reasonFileName,accessToken);
            if (!PushFileTo.pushToUser(contractRecorrectRequestBody.getOrganizerUserId(),reasonMediaId,reasonFileName,accessToken,appInfo)){
                log.info("文件:"+reasonFileName+",mediaId:"+reasonMediaId+",推送失败");
            }
            contractInfoEntity.setReasonOfNotUsingStandTemplateFilePath(reasonFileName);
            contractInfoEntity.setReasonOfNotUsingStandTemplateDingPanId(reasonMediaId);
        }

        //合同正文保存并上传钉盘，存入流程变量
        if (!contractRecorrectRequestBody.getContractText().isEmpty()){
            MultipartFile contractText = contractRecorrectRequestBody.getContractText();
            String contractTextFileName = FileSaver.save(filePath,contractText);
            if (contractTextFileName!=null){
                String contractTextMediaId = uploadToDingPan.doUpload(contractTextFileName,accessToken);
                if (!PushFileTo.pushToUser(contractRecorrectRequestBody.getOrganizerUserId(),contractTextMediaId,contractTextFileName,accessToken,appInfo)){
                    log.info("文件:"+contractTextFileName+",mediaId:"+contractTextMediaId+",推送失败");
                }
                contractInfoEntity.setContractTextFilePath(contractTextFileName);
                contractInfoEntity.setContractTextDingPanId(contractTextMediaId);
            }
        }

        //合同变量存入数据库
        contractInfoMapper.insert(contractInfoEntity);

        //结束任务
        runtimeService.updateBusinessKey(task.getProcessInstanceId(),contractId);
        map.put("contract",contractInfoEntity);
        taskService.complete(task.getId(),map);
        return map;
    }
}
