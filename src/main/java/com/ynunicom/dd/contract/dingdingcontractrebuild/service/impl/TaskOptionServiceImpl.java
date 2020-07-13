package com.ynunicom.dd.contract.dingdingcontractrebuild.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.mysql.cj.xdevapi.JsonArray;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.AttachmentEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractInfoEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractTemplateEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.AttachmentMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.ContractInfoMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.ContractTemplateMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.status.ContractInfoStatus;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.status.MethodStatus;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.AttachmentResponse;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.JudgePersonEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.PersonEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ProcessInstanceDefKey;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.ContractApplyRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.HurryUpRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.DeptService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.RoleService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.TaskOptionService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.UserInfoService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
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

    @Resource
    RoleService roleService;

    @Resource
    DeptService deptService;

    @Resource
    ProcessInstanceDefKey startProcessInstanceDefKey;

    @Override
    public Task taskVarLoadFromOutSide(Task task, ContractApplyRequestBody contractApplyRequestBody, String accessToken){
        Map<String,Object> map = taskService.getVariables(task.getId());
        ContractInfoEntity contractInfoEntityBefore = (ContractInfoEntity) map.get("contract");
        ContractInfoEntity contractInfoEntityComming = new ContractInfoEntity(contractApplyRequestBody);
        /**
         * 这里要把contractInfoEntityBefore和contractInfoEntityComming整合起来
         */
        contractInfoEntityBefore = (ContractInfoEntity) ContractInfoEntityMerge.merge(contractInfoEntityBefore,contractInfoEntityComming);
        map.put("contract",contractInfoEntityBefore);
        map.put("currentIsOk",false);
        taskService.setVariables(task.getId(),map);
        return task;
    }

    @SneakyThrows
    @Override
    public JSONObject getOneByApplyUserIdAndContractId(String accessToken, String userId, String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task==null){
            throw new BussException(taskId+"任务不存在");
        }
        Map<String,Object> map = taskService.getVariables(task.getId());
        ContractInfoEntity contractInfoEntity = (ContractInfoEntity) map.get("contract");
        AttachmentResponse attachmentResponse = new AttachmentResponse();
        Map<String ,String > attachmentMap = new HashMap<>();
        if(contractInfoEntity.getAttachmentFilePath1()!=null){
            attachmentMap.put(contractInfoEntity.getAttachmentFilePath1(),contractInfoEntity.getAttachmentDingPanid1());
        }
        if(contractInfoEntity.getAttachmentFilePath2()!=null){
            attachmentMap.put(contractInfoEntity.getAttachmentFilePath2(),contractInfoEntity.getAttachmentDingPanid2());
        }
        if(contractInfoEntity.getAttachmentFilePath3()!=null){
            attachmentMap.put(contractInfoEntity.getAttachmentFilePath3(),contractInfoEntity.getAttachmentDingPanid3());
        }
        if(contractInfoEntity.getAttachmentFilePath4()!=null){
            attachmentMap.put(contractInfoEntity.getAttachmentFilePath4(),contractInfoEntity.getAttachmentDingPanid4());
        }
        if(contractInfoEntity.getAttachmentFilePath5()!=null){
            attachmentMap.put(contractInfoEntity.getAttachmentFilePath5(),contractInfoEntity.getAttachmentDingPanid5());
        }
        attachmentResponse.setMap(attachmentMap);
        map.put("attachments",attachmentResponse);

        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(map.get("stages")));
        for ( Object inner :
                jsonArray) {
                JSONObject innerJson = (JSONObject) inner;
                if (innerJson.getString("lastJudgeTime")==null){
                    innerJson.put("isOk",null);
                }
        }
        map.put("stages",jsonArray);
        return new JSONObject(map);
    }

    private Task taskVarLoad(Task task,ContractApplyRequestBody contractApplyRequestBody,String accessToken){
        Map<String,Object> map = new HashMap<>();
        List<String> userIdList = StringsToList.trans(contractApplyRequestBody.getReviewerList());
        List<JudgePersonEntity> judgePersonEntityList = new ArrayList<>();
        for (String userId:
        userIdList){
            JSONObject jsonObject = userInfoService.getUserInfo(accessToken,userId);
            JudgePersonEntity judgePersonEntity = new JudgePersonEntity();
            String deptId = jsonObject.getJSONArray("department").getString(0);
            PersonEntity personEntity = new PersonEntity(jsonObject.getString("name"),userId,jsonObject.getString("avatar"),deptId,deptService.getByDeptId(accessToken,deptId).get("name"));
            judgePersonEntity.setPersonEntity(personEntity);
            judgePersonEntity.setIsOk(false);
            judgePersonEntityList.add(judgePersonEntity);
        }
        while (judgePersonEntityList.size()<9){
            JudgePersonEntity judgePersonEntity = new JudgePersonEntity();
            judgePersonEntity.setIsOk(true);
            judgePersonEntity.setComment("null");
            PersonEntity personEntity = new PersonEntity("null","null","null","null","null");
            judgePersonEntity.setPersonEntity(personEntity);
            judgePersonEntityList.add(judgePersonEntity);
        }


        //塞入最终审核人
        if (contractApplyRequestBody.getFinalReviewerList()!=null&&!contractApplyRequestBody.getFinalReviewerList().isEmpty()){
            String finalReviewer = contractApplyRequestBody.getFinalReviewerList();
            JSONObject finalReviewerJson = userInfoService.getUserInfo(accessToken,finalReviewer);
            JudgePersonEntity judgePersonEntity = new JudgePersonEntity();
            judgePersonEntity.setIsOk(false);
            String deptId = finalReviewerJson.getJSONArray("department").getString(0);
            PersonEntity personEntity = new PersonEntity(finalReviewerJson.getString("name"),finalReviewer,finalReviewerJson.getString("avatar"),deptId,deptService.getByDeptId(accessToken,deptId).get("name"));
            judgePersonEntity.setPersonEntity(personEntity);
            judgePersonEntityList.add(judgePersonEntity);
        }
        else {
            JudgePersonEntity judgePersonEntity = new JudgePersonEntity();
            judgePersonEntity.setIsOk(true);
            judgePersonEntity.setComment("null");
            PersonEntity personEntity = new PersonEntity("null","null","null","null","null");
            judgePersonEntity.setPersonEntity(personEntity);
            judgePersonEntityList.add(judgePersonEntity);
        }



        map.put("stages",judgePersonEntityList);
        map.put("applyUserId",contractApplyRequestBody.getOrganizerUserId());
        map.put("contract",new ContractInfoEntity(contractApplyRequestBody));
        map.put("currentIsOk",false);
        map.put("contractSaverRole",appInfo.getContractSaverRole());
        taskService.setVariables(task.getId(),map);
        return task;
    }

    @SneakyThrows
    @Override
    public JSONArray getByAssigneeUserId(String accessToken, String userId) {
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
        AttachmentResponse attachmentResponse = new AttachmentResponse();
        jsonArray.addAll(mapList);
        jsonArray.add(getByRole(accessToken,userId));
        return jsonArray;
    }

    @SneakyThrows
    @Override
    public JSONArray getByApplyUserId(String accessToken, String applyUserId) {
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

    @Override
    public JSONArray getByRole(String accessToken, String userId) {
        List<String> roleList =  roleService.getRoleByUserId(userId,accessToken);
        JSONArray jsonArray = new JSONArray();
        Set<Task> taskSet = new HashSet<>();
        for (String role:
             roleList) {
            List<Task> innerTaskList = taskService.createTaskQuery().taskCandidateUser(role).list();
            taskSet.addAll(innerTaskList);
        }
        List<Task> taskList =  new ArrayList<>(taskSet);
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (Task task :
                taskList) {
            Map<String,Object> map  = taskService.getVariables(task.getId());
            map.put("taskId",task.getId());
            map.put("taskStage",task.getName());
            mapList.add(map);
        }
        jsonArray.addAll(mapList);
        return jsonArray;
    }

    @SneakyThrows
    @Transactional
    @Override
    public Map<String, Object> startNewInst(ContractApplyRequestBody contractApplyRequestBody, String accessToken) {


        if (!contractApplyRequestBody.getMethod().equals(MethodStatus.APPLY)&&!contractApplyRequestBody.getMethod().equals(MethodStatus.ALTER)
        &&!contractApplyRequestBody.getMethod().equals(MethodStatus.CONTINUE)&&!contractApplyRequestBody.getMethod().equals(MethodStatus.PREEND)){
            throw new BussException("method不合法");
        }
        ContractInfoEntity preContractInfoEntity = null;
        if (contractApplyRequestBody.getPreContractId()!=null&&!contractApplyRequestBody.getPreContractId().isEmpty()){
            preContractInfoEntity = contractInfoMapper.selectById(contractApplyRequestBody.getPreContractId());
            if (preContractInfoEntity==null){
                throw new BussException("历史合同"+contractApplyRequestBody.getPreContractId()+"不存在");
            }
        }

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(startProcessInstanceDefKey.getKey());
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        if (task==null){
            throw new FlowableObjectNotFoundException("任务创建失败");
        }
        task = taskVarLoad(task,contractApplyRequestBody,accessToken);
        Map<String,Object> map = taskService.getVariables(task.getId());


        ContractInfoEntity contractInfoEntity = (ContractInfoEntity) map.get("contract");
        String contractId = UUID.randomUUID().toString().replace("-","");
        contractInfoEntity.setId(contractId);


        //附件1保存并上传钉盘,存入流程变量
        MultipartFile attachment = contractApplyRequestBody.getAttachment1();
        if (attachment!=null&&!attachment.isEmpty()){
            String attachmentFileName = FileSaver.save(filePath,attachment);
            String attachmentMediaId = uploadToDingPan.doUpload(attachmentFileName,accessToken);
            if (!PushFileTo.pushToUser(contractApplyRequestBody.getOrganizerUserId(),attachmentMediaId,attachmentFileName,accessToken,appInfo)){
                log.info("文件:"+attachmentFileName+",mediaId:"+attachmentMediaId+",推送失败");
            }
            AttachmentEntity attachmentEntity = new AttachmentEntity(attachmentFileName,contractId,attachmentFileName,attachmentMediaId);
            attachmentMapper.insert(attachmentEntity);
            contractInfoEntity.setAttachmentDingPanid1(attachmentMediaId);
            contractInfoEntity.setAttachmentFilePath1(attachmentFileName);
            String attachmentId = attachmentEntity.getId();
        }

        //附件2保存并上传钉盘,存入流程变量
        attachment = contractApplyRequestBody.getAttachment2();
        if (attachment!=null&&!attachment.isEmpty()){
            String attachmentFileName = FileSaver.save(filePath,attachment);
            String attachmentMediaId = uploadToDingPan.doUpload(attachmentFileName,accessToken);
            if (!PushFileTo.pushToUser(contractApplyRequestBody.getOrganizerUserId(),attachmentMediaId,attachmentFileName,accessToken,appInfo)){
                log.info("文件:"+attachmentFileName+",mediaId:"+attachmentMediaId+",推送失败");
            }
            AttachmentEntity attachmentEntity = new AttachmentEntity(attachmentFileName,contractId,attachmentFileName,attachmentMediaId);
            attachmentMapper.insert(attachmentEntity);
            contractInfoEntity.setAttachmentDingPanid2(attachmentMediaId);
            contractInfoEntity.setAttachmentFilePath2(attachmentFileName);
            String attachmentId = attachmentEntity.getId();
        }

        //附件3保存并上传钉盘,存入流程变量
        attachment = contractApplyRequestBody.getAttachment3();
        if (attachment!=null&&!attachment.isEmpty()){
            String attachmentFileName = FileSaver.save(filePath,attachment);
            String attachmentMediaId = uploadToDingPan.doUpload(attachmentFileName,accessToken);
            if (!PushFileTo.pushToUser(contractApplyRequestBody.getOrganizerUserId(),attachmentMediaId,attachmentFileName,accessToken,appInfo)){
                log.info("文件:"+attachmentFileName+",mediaId:"+attachmentMediaId+",推送失败");
            }
            AttachmentEntity attachmentEntity = new AttachmentEntity(attachmentFileName,contractId,attachmentFileName,attachmentMediaId);
            attachmentMapper.insert(attachmentEntity);
            contractInfoEntity.setAttachmentDingPanid3(attachmentMediaId);
            contractInfoEntity.setAttachmentFilePath3(attachmentFileName);
            String attachmentId = attachmentEntity.getId();
        }

        //附件4保存并上传钉盘,存入流程变量
        attachment = contractApplyRequestBody.getAttachment4();
        if (attachment!=null&&!attachment.isEmpty()){
            String attachmentFileName = FileSaver.save(filePath,attachment);
            String attachmentMediaId = uploadToDingPan.doUpload(attachmentFileName,accessToken);
            if (!PushFileTo.pushToUser(contractApplyRequestBody.getOrganizerUserId(),attachmentMediaId,attachmentFileName,accessToken,appInfo)){
                log.info("文件:"+attachmentFileName+",mediaId:"+attachmentMediaId+",推送失败");
            }
            AttachmentEntity attachmentEntity = new AttachmentEntity(attachmentFileName,contractId,attachmentFileName,attachmentMediaId);
            attachmentMapper.insert(attachmentEntity);
            contractInfoEntity.setAttachmentDingPanid4(attachmentMediaId);
            contractInfoEntity.setAttachmentFilePath4(attachmentFileName);
            String attachmentId = attachmentEntity.getId();
        }

        //附件5保存并上传钉盘,存入流程变量
        attachment = contractApplyRequestBody.getAttachment5();
        if (attachment!=null&&!attachment.isEmpty()){
            String attachmentFileName = FileSaver.save(filePath,attachment);
            String attachmentMediaId = uploadToDingPan.doUpload(attachmentFileName,accessToken);
            if (!PushFileTo.pushToUser(contractApplyRequestBody.getOrganizerUserId(),attachmentMediaId,attachmentFileName,accessToken,appInfo)){
                log.info("文件:"+attachmentFileName+",mediaId:"+attachmentMediaId+",推送失败");
            }
            AttachmentEntity attachmentEntity = new AttachmentEntity(attachmentFileName,contractId,attachmentFileName,attachmentMediaId);
            attachmentMapper.insert(attachmentEntity);
            contractInfoEntity.setAttachmentDingPanid5(attachmentMediaId);
            contractInfoEntity.setAttachmentFilePath5(attachmentFileName);
            String attachmentId = attachmentEntity.getId();
        }


        //合同模板上传钉盘，存入流程变量
        MultipartFile standTemplate = contractApplyRequestBody.getStandTemplate();
        if (standTemplate!=null&&!standTemplate.isEmpty()){
            String standTemplateFileName = FileSaver.save(filePath,standTemplate);
            String standTemplateMediaId = uploadToDingPan.doUpload(standTemplateFileName,accessToken);
            if (!PushFileTo.pushToUser(contractApplyRequestBody.getOrganizerUserId(),standTemplateMediaId,standTemplateFileName,accessToken,appInfo)){
                log.info("文件:"+standTemplateFileName+",mediaId:"+standTemplateMediaId+",推送失败");
            }
            contractInfoEntity.setStandTemplateDingPanId(standTemplateMediaId);
            contractInfoEntity.setStandTemplateFilePath(standTemplateFileName);
        }


        //对方资质文件保存并上传钉盘，存入流程变量
        MultipartFile qualityFile = contractApplyRequestBody.getTheirQuality();
        if (qualityFile!=null&&!qualityFile.isEmpty()){
            String qualityFileName = FileSaver.save(filePath,qualityFile);
            String qualityFileMediaId = uploadToDingPan.doUpload(qualityFileName,accessToken);
            if (!PushFileTo.pushToUser(contractApplyRequestBody.getOrganizerUserId(),qualityFileMediaId,qualityFileName,accessToken,appInfo)){
                log.info("文件:"+qualityFileName+",mediaId:"+qualityFileMediaId+",推送失败");
            }
            contractInfoEntity.setTheirQualityFilePath(qualityFileName);
            contractInfoEntity.setTheirQualityDingPanId(qualityFileMediaId);
        }


        //合同正文保存并上传钉盘，存入流程变量
        MultipartFile contractText = contractApplyRequestBody.getContractText();
        if (contractText!=null&&!contractText.isEmpty()){
            String contractTextFileName = FileSaver.save(filePath,contractText);
            if (contractTextFileName!=null){
                String contractTextMediaId = uploadToDingPan.doUpload(contractTextFileName,accessToken);
                if (!PushFileTo.pushToUser(contractApplyRequestBody.getOrganizerUserId(),contractTextMediaId,contractTextFileName,accessToken,appInfo)){
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
        if (!contractApplyRequestBody.getMethod().equals(MethodStatus.APPLY)){
            if (preContractInfoEntity==null){
                throw new BussException(contractApplyRequestBody.getPreContractId()+"合同未找到");
            }
            if (!preContractInfoEntity.getContractRunnerUserId().equals(contractApplyRequestBody.getOrganizerUserId())){
                throw new BussException("只有合同履行人才能发起修改、续签、提前终止");
            }
            contractInfoEntity.setPreContractId(preContractInfoEntity.getId());
            contractInfoEntity.setStatu(contractApplyRequestBody.getMethod()+"ing");
        }
        else{
            contractInfoEntity.setStatu(ContractInfoStatus.APPLYING);
        }
        map.put("method",contractApplyRequestBody.getMethod());
        map.put("contract",contractInfoEntity);
        taskService.complete(task.getId(),map);
        return map;
    }

    @SneakyThrows
    @Override
    public String hurryUp(String accessToken, HurryUpRequestBody hurryUpRequestBody) {
        String judgeName = userInfoService.getUserInfo(accessToken,hurryUpRequestBody.getUserId()).getString("name");
        if (judgeName==null||judgeName.isEmpty()){
            throw new BussException("用户"+hurryUpRequestBody.getUserId()+"不存在");
        }
        Task task = taskService.createTaskQuery().taskId(hurryUpRequestBody.getTaskId()).singleResult();
        if (task==null){
            throw new FlowableObjectNotFoundException(hurryUpRequestBody.getTaskId()+"任务没有找到");
        }
        Map<String,Object> map = taskService.getVariables(task.getId());
        List<JudgePersonEntity> judgePersonEntityList = (List<JudgePersonEntity>) map.get("stages");
        String judgerId = null;
        for (JudgePersonEntity judgePersonEntity:
            judgePersonEntityList) {
            if (judgePersonEntity.getIsOk()){
                continue;
            }
            else {
                judgerId = judgePersonEntity.getPersonEntity().getUserId();
            }
        }
        if (judgerId==null||judgerId.isEmpty()){
            throw new BussException("所有审核都已通过，无需催办");
        }
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");

        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setUseridList(judgerId);
        request.setAgentId(Long.parseLong(appInfo.getAgentId()));
        request.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("text");
        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
        String message = new Date() +judgeName+ "催办了你的合同审批";
        msg.getText().setContent(message);
        request.setMsg(msg);
        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request,accessToken);
        if (!response.isSuccess()){
            throw new BussException(response.getErrmsg());
        }
        return message;
    }
}
