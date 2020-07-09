package com.ynunicom.dd.contract.dingdingcontractrebuild.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractInfoEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.JudgePersonEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.JudgeRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.JudgeService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.UserInfoService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.MsgSender;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.UserVerify;
import lombok.SneakyThrows;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: jinye.Bai
 * @date: 2020/6/19 11:25
 */
@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    UserInfoService userInfoService;

    @Resource
    AppInfo appInfo;

    @Autowired
    TaskService taskService;

    @SneakyThrows
    @Override
    public JSONArray judge(String accessToken, JudgeRequestBody judgeRequestBody) {
        String judgerName = userInfoService.getUserInfo(accessToken,judgeRequestBody.getUserId()).getString("name");
        String taskId = judgeRequestBody.getTaskId();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Map<String,Object> map = taskService.getVariables(taskId);
        ContractInfoEntity contractInfoEntity = (ContractInfoEntity) map.get("contract");
        int stageNo = Integer.parseInt(task.getTaskDefinitionKey().substring(1));
        List<JudgePersonEntity> judgePersonEntityList = (List<JudgePersonEntity>) map.get("stages");
        JudgePersonEntity judgePersonEntity = judgePersonEntityList.get(stageNo-1);
        if (!judgePersonEntity.getPersonEntity().getUserId().equals(judgeRequestBody.getUserId())){
            throw new BussException("操作者与审核人不一致");
        }
        judgePersonEntity.setIsOk(judgeRequestBody.getIsOk());
        judgePersonEntity.setComment(judgeRequestBody.getComment());
        judgePersonEntity.setLastJudgeTime(new Date());
        map.put("currentIsOk",judgeRequestBody.getIsOk());
        MsgSender.send(accessToken,contractInfoEntity.getOrganizerUserId(),appInfo,"你的合同"+contractInfoEntity.getContractName()+"已被审核人"+judgerName+"审批");
        taskService.complete(taskId,map);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(map);

        return jsonArray;
    }
}
