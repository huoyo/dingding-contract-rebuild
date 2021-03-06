package com.ynunicom.dd.contract.dingdingcontractrebuild.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jcraft.jsch.UserInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.AttachmentEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractInfoEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractTemplateEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.AttachmentMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.ContractInfoMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.ContractTemplateMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.ContractRecorrectRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.LegalRecorrectRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.RecorrectService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.RoleService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.TaskOptionService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.UserInfoService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.FileSaver;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.OtherDeletor;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.PushFileTo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.UploadToDingPan;
import lombok.SneakyThrows;
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
import java.util.HashMap;
import java.util.List;
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
    UserInfoService userInfoService;

    @Resource
    ContractTemplateMapper contractTemplateMapper;

    @Resource
    ContractInfoMapper contractInfoMapper;

    @Resource
    TaskOptionService taskOptionService;

    private Map<String,Object> dropContract(String taskId) throws BussException {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Map<String,Object> map = taskService.getVariables(task.getId());
        ContractInfoEntity contractInfoEntity = (ContractInfoEntity) map.get("contract");
        String contractId = contractInfoEntity.getId();
        if (contractId==null||contractId.isEmpty()){
            throw new BussException(taskId+"任务的合同不在数据库中，删除失败");
        }

        //删除数据库的记录
        int resultC = contractInfoMapper.deleteById(contractId);
        int resultA = attachmentMapper.delete(new LambdaQueryWrapper<AttachmentEntity>().eq(AttachmentEntity::getContractInfoId,contractId));

        //删除文件
        if (!OtherDeletor.delete(contractInfoEntity.getContractTextFilePath())){
            log.info(contractInfoEntity.getContractTextFilePath()+"删除失败");
        }
        if (!OtherDeletor.delete(contractInfoEntity.getTheirQualityFilePath())){
            log.info(contractInfoEntity.getTheirQualityFilePath()+"删除失败");
        }
        if (!OtherDeletor.delete(contractInfoEntity.getAttachmentFilePath1())){
            log.info(contractInfoEntity.getAttachmentFilePath1()+"删除失败");
        }
        if (!OtherDeletor.delete(contractInfoEntity.getAttachmentFilePath2())){
            log.info(contractInfoEntity.getAttachmentFilePath2()+"删除失败");
        }
        if (!OtherDeletor.delete(contractInfoEntity.getAttachmentFilePath3())){
            log.info(contractInfoEntity.getAttachmentFilePath3()+"删除失败");
        }

        //删除流程任务
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.deleteProcessInstance(processInstanceId,"");



        Map<String,Object> returnMap = new HashMap<>(1);
        returnMap.put("删除情况","成功删除");
        return  returnMap;
    }

    @SneakyThrows
    @Override
    @Transactional
    public Map<String, Object> recorrect(String accessToken, ContractRecorrectRequestBody contractRecorrectRequestBody) {
        Task task = taskService.createTaskQuery().taskId(contractRecorrectRequestBody.getTaskId()).singleResult();

        //放弃合同申请的情况
        if (contractRecorrectRequestBody.isDrop()){
            return dropContract(contractRecorrectRequestBody.getTaskId());
        }

        if (task==null){
            throw new FlowableObjectNotFoundException(contractRecorrectRequestBody.getTaskId()+"任务没有找到");
        }
        task = taskOptionService.taskVarLoadFromOutSide(task,contractRecorrectRequestBody,accessToken);
        Map<String,Object> map = taskService.getVariables(task.getId());

        ContractInfoEntity contractInfoEntity = (ContractInfoEntity) map.get("contract");
        String contractId = contractInfoEntity.getId();
        String applyerUserId = (String) map.get("applyUserId");

        //附件1保存并上传钉盘,存入流程变量
        MultipartFile attachment = contractRecorrectRequestBody.getAttachment1();
        if (attachment!=null&&!attachment.isEmpty()){
            String attachmentFileName = FileSaver.save(filePath,attachment);
            String attachmentMediaId = uploadToDingPan.doUpload(attachmentFileName,accessToken);
            if (!PushFileTo.pushToUser(applyerUserId,attachmentMediaId,attachmentFileName,accessToken,appInfo)){
                log.info("文件:"+attachmentFileName+",mediaId:"+attachmentMediaId+",推送失败");
            }
            AttachmentEntity attachmentEntity = new AttachmentEntity(attachmentFileName,contractId,attachmentFileName,attachmentMediaId);
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
            if (!PushFileTo.pushToUser(applyerUserId,attachmentMediaId,attachmentFileName,accessToken,appInfo)){
                log.info("文件:"+attachmentFileName+",mediaId:"+attachmentMediaId+",推送失败");
            }
            AttachmentEntity attachmentEntity = new AttachmentEntity(attachmentFileName,contractId,attachmentFileName,attachmentMediaId);
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
            if (!PushFileTo.pushToUser(applyerUserId,attachmentMediaId,attachmentFileName,accessToken,appInfo)){
                log.info("文件:"+attachmentFileName+",mediaId:"+attachmentMediaId+",推送失败");
            }
            AttachmentEntity attachmentEntity = new AttachmentEntity(attachmentFileName,contractId,attachmentFileName,attachmentMediaId);
            attachmentMapper.insert(attachmentEntity);
            contractInfoEntity.setAttachmentDingPanid3(attachmentMediaId);
            contractInfoEntity.setAttachmentFilePath3(attachmentFileName);
            String attachmentId = attachmentEntity.getId();
        }

        //附件4保存并上传钉盘,存入流程变量
        attachment = contractRecorrectRequestBody.getAttachment4();
        if (attachment!=null&&!attachment.isEmpty()){
            String attachmentFileName = FileSaver.save(filePath,attachment);
            String attachmentMediaId = uploadToDingPan.doUpload(attachmentFileName,accessToken);
            if (!PushFileTo.pushToUser(applyerUserId,attachmentMediaId,attachmentFileName,accessToken,appInfo)){
                log.info("文件:"+attachmentFileName+",mediaId:"+attachmentMediaId+",推送失败");
            }
            AttachmentEntity attachmentEntity = new AttachmentEntity(attachmentFileName,contractId,attachmentFileName,attachmentMediaId);
            attachmentMapper.insert(attachmentEntity);
            contractInfoEntity.setAttachmentDingPanid4(attachmentMediaId);
            contractInfoEntity.setAttachmentFilePath4(attachmentFileName);
            String attachmentId = attachmentEntity.getId();
        }

        //附件5保存并上传钉盘,存入流程变量
        attachment = contractRecorrectRequestBody.getAttachment5();
        if (attachment!=null&&!attachment.isEmpty()){
            String attachmentFileName = FileSaver.save(filePath,attachment);
            String attachmentMediaId = uploadToDingPan.doUpload(attachmentFileName,accessToken);
            if (!PushFileTo.pushToUser(applyerUserId,attachmentMediaId,attachmentFileName,accessToken,appInfo)){
                log.info("文件:"+attachmentFileName+",mediaId:"+attachmentMediaId+",推送失败");
            }
            AttachmentEntity attachmentEntity = new AttachmentEntity(attachmentFileName,contractId,attachmentFileName,attachmentMediaId);
            attachmentMapper.insert(attachmentEntity);
            contractInfoEntity.setAttachmentDingPanid5(attachmentMediaId);
            contractInfoEntity.setAttachmentFilePath5(attachmentFileName);
            String attachmentId = attachmentEntity.getId();
        }


        //合同模板上传钉盘，存入流程变量
        MultipartFile standTemplate = contractRecorrectRequestBody.getStandTemplate();
        if (standTemplate!=null&&!standTemplate.isEmpty()){
            String standTemplateFileName = FileSaver.save(filePath,standTemplate);
            String standTemplateMediaId = uploadToDingPan.doUpload(standTemplateFileName,accessToken);
            if (!PushFileTo.pushToUser(applyerUserId,standTemplateMediaId,standTemplateFileName,accessToken,appInfo)){
                log.info("文件:"+standTemplateFileName+",mediaId:"+standTemplateMediaId+",推送失败");
            }
            contractInfoEntity.setStandTemplateDingPanId(standTemplateMediaId);
            contractInfoEntity.setStandTemplateFilePath(standTemplateFileName);
        }


        //对方资质文件保存并上传钉盘，存入流程变量
        MultipartFile qualityFIle = contractRecorrectRequestBody.getTheirQuality();
        if (qualityFIle!=null&&!qualityFIle.isEmpty()){
            MultipartFile qualityFile = contractRecorrectRequestBody.getTheirQuality();
            String qualityFileName = FileSaver.save(filePath,qualityFile);
            String qualityFileMediaId = uploadToDingPan.doUpload(qualityFileName,accessToken);
            if (!PushFileTo.pushToUser(applyerUserId,qualityFileMediaId,qualityFileName,accessToken,appInfo)){
                log.info("文件:"+qualityFileName+",mediaId:"+qualityFileMediaId+",推送失败");
            }
            contractInfoEntity.setTheirQualityFilePath(qualityFileName);
            contractInfoEntity.setTheirQualityDingPanId(qualityFileMediaId);
        }


        //合同正文保存并上传钉盘，存入流程变量
        MultipartFile contractText = contractRecorrectRequestBody.getContractText();
        if (contractText!=null&&!contractText.isEmpty()){
            String contractTextFileName = FileSaver.save(filePath,contractText);
            if (contractTextFileName!=null){
                String contractTextMediaId = uploadToDingPan.doUpload(contractTextFileName,accessToken);
                if (!PushFileTo.pushToUser(applyerUserId,contractTextMediaId,contractTextFileName,accessToken,appInfo)){
                    log.info("文件:"+contractTextFileName+",mediaId:"+contractTextMediaId+",推送失败");
                }
                contractInfoEntity.setContractTextFilePath(contractTextFileName);
                contractInfoEntity.setContractTextDingPanId(contractTextMediaId);
            }
        }

        //合同变量存入数据库
        contractInfoMapper.updateById(contractInfoEntity);

        //结束任务
        contractInfoEntity.setStatu(((String) map.get("method"))+"ing");
        map.put("contract",contractInfoEntity);
        taskService.setVariables(task.getId(),map);
        taskService.complete(task.getId(),map);
        return map;
    }

    @SneakyThrows
    @Override
    public Map<String, Object> legalRecorrect(String accessToken, String userId, LegalRecorrectRequestBody legalRecorrectRequestBody) {
        if (legalRecorrectRequestBody.getContractText()==null||legalRecorrectRequestBody.getContractText().isEmpty()){
            throw new BussException("合同正文不可为空");
        }
        JSONObject jsonObject = userInfoService.getUserInfo(accessToken,userId);
        if (jsonObject==null||jsonObject.isEmpty()){
            throw new BussException("获取用户信息失败");
        }
        List<String> deptList = jsonObject.getJSONArray("department").toJavaList(String.class);
        if (deptList==null||deptList.isEmpty()){
            throw new BussException("获取用户信息失败");
        }
        boolean flag = false;
        for (String deptId :
                deptList) {
            if (deptId.equals(appInfo.getLegalDeptId())){
                flag = true;
            }
        }
        if (!flag){
            throw new BussException("用户并非法律顾问人员");
        }

        Task task = taskService.createTaskQuery().taskId(legalRecorrectRequestBody.getTaskId()).singleResult();
        if (task==null){
            throw new BussException(legalRecorrectRequestBody.getTaskId()+"任务不存在");
        }
        Map<String,Object> map = taskService.getVariables(task.getId());
        ContractInfoEntity contractInfoEntity = (ContractInfoEntity) map.get("contract");
        String fileName = FileSaver.save(filePath,legalRecorrectRequestBody.getContractText());
        String mediaId = uploadToDingPan.doUpload(fileName,accessToken);
        contractInfoEntity.setContractTextDingPanId(mediaId);
        contractInfoEntity.setContractTextFilePath(fileName);
        map.put("contract",contractInfoEntity);
        taskService.setVariables(task.getId(),map);
        return map;
    }
}
