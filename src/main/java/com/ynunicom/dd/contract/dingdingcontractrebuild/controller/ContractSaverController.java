package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.alibaba.fastjson.JSONObject;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractInfoEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.ContractInfoMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ResponseDto;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.ContractSaverRefulseRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.TaskIdRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.RoleService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.UserInfoService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.MsgSender;
import lombok.SneakyThrows;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author: jinye.Bai
 * @date: 2020/7/1 13:11
 */
@RestController
@RequestMapping("/contractSaver")
public class ContractSaverController {

    @Resource
    AppInfo appInfo;

    @Resource
    RoleService roleService;

    @Autowired
    TaskService taskService;

    @Autowired
    ContractInfoMapper contractInfoMapper;

    @Resource
    UserInfoService userInfoService;

    @SneakyThrows
    private void contractSaverRoleCheck(String accessToken, String userId){
        List<String> roleList = roleService.getRoleByUserId(userId, accessToken);
        if (roleList == null || roleList.isEmpty()) {
            throw new BussException("用户" + userId + "没有任何角色，操作失败");
        }
        Iterator<String> roleIterator = roleList.iterator();
        boolean flag = false;
        while (roleIterator.hasNext()) {
            if (roleIterator.next().equals(appInfo.getContractSaverRole())) {
                flag = true;
                break;
            }
        }
        if (!flag){
            throw new BussException("用户"+userId+"无权操作");
        }
    }

    @Transactional
    @SneakyThrows
    @PostMapping("/sign")
    public ResponseDto contractSaver(@RequestParam("accessToken")String accessToken, @RequestParam("userId")String userId,@RequestBody TaskIdRequestBody taskIdRequestBody) {
        contractSaverRoleCheck(accessToken,userId);
        Task task = taskService.createTaskQuery().taskId(taskIdRequestBody.getTaskId()).singleResult();
        if (task==null){
            throw new BussException(taskIdRequestBody.getTaskId()+"任务不存在");
        }
        JSONObject jsonObject = userInfoService.getUserInfo(accessToken,userId);
        String userName = jsonObject.getString("name");

        Map<String,Object> map = taskService.getVariables(taskIdRequestBody.getTaskId());
        ContractInfoEntity contractInfoEntity = (ContractInfoEntity) map.get("contract");
        contractInfoEntity.setSign(1);
        contractInfoEntity.setSignName(userName);
        contractInfoMapper.updateById(contractInfoEntity);
        if (contractInfoEntity.getSave()==1){
            if ("altering".equals(contractInfoEntity.getStatu())){
                contractInfoEntity.setStatu("running");
            }
            else if ("continueing".equals(contractInfoEntity.getStatu())){
                contractInfoEntity.setStatu("running");
            }
            else if ("preEnding".equals(contractInfoEntity.getStatu())){
                contractInfoEntity.setStatu("preEnded");
            }
            else{
                contractInfoEntity.setStatu("running");
            }
            map.put("contract",contractInfoEntity);
            map.put("currentIsOk",true);
            contractInfoMapper.updateById(contractInfoEntity);
            taskService.complete(taskIdRequestBody.getTaskId(),map);
            return ResponseDto.success("签章完成");
        }
        map.put("contract",contractInfoEntity);
        taskService.setVariables(taskIdRequestBody.getTaskId(),map);
        return ResponseDto.success("签章完成");
    }

    @SneakyThrows
    @Transactional
    @PostMapping("/save")
    public ResponseDto save(@RequestParam("accessToken")String accessToken, @RequestParam("userId")String userId, @RequestBody TaskIdRequestBody taskIdRequestBody){
        contractSaverRoleCheck(accessToken,userId);
        Task task = taskService.createTaskQuery().taskId(taskIdRequestBody.getTaskId()).singleResult();
        if (task==null){
            throw new BussException(taskIdRequestBody.getTaskId()+"任务不存在");
        }
        JSONObject jsonObject = userInfoService.getUserInfo(accessToken,userId);
        String userName = jsonObject.getString("name");

        Map<String,Object> map = taskService.getVariables(taskIdRequestBody.getTaskId());
        ContractInfoEntity contractInfoEntity = (ContractInfoEntity) map.get("contract");
        contractInfoEntity.setSave(1);
        contractInfoEntity.setSaveName(userName);
        contractInfoMapper.updateById(contractInfoEntity);
        if (contractInfoEntity.getSign()==1){
            if ("altering".equals(contractInfoEntity.getStatu())){
                contractInfoEntity.setStatu("running");
            }
            else if ("continueing".equals(contractInfoEntity.getStatu())){
                contractInfoEntity.setStatu("running");
            }
            else if ("preEnding".equals(contractInfoEntity.getStatu())){
                contractInfoEntity.setStatu("preEnded");
            }
            else{
                contractInfoEntity.setStatu("running");
            }
            map.put("contract",contractInfoEntity);
            map.put("currentIsOk",true);
            contractInfoMapper.updateById(contractInfoEntity);
            taskService.complete(taskIdRequestBody.getTaskId(),map);
            return ResponseDto.success("存档完成");
        }
        map.put("contract",contractInfoEntity);
        taskService.setVariables(taskIdRequestBody.getTaskId(),map);
        return ResponseDto.success("存档完成");
    }

    @SneakyThrows
    @PostMapping("/refulse")
    public ResponseDto refulse(@RequestParam("accessToken")String accessToken, @RequestParam("userId")String userId, @RequestBody @Validated ContractSaverRefulseRequestBody contractSaverRefulseRequestBody){
        contractSaverRoleCheck(accessToken,userId);
        Task task = taskService.createTaskQuery().taskId(contractSaverRefulseRequestBody.getTaskId()).singleResult();
        if (task==null){
            throw new BussException(task+"任务不存在");
        }
        Map<String,Object> map = taskService.getVariables(contractSaverRefulseRequestBody.getTaskId());
        map.put("contractSaverComment",contractSaverRefulseRequestBody.getComment());
        map.put("currentIsOk",false);
        if (!MsgSender.send(accessToken,userId,appInfo)) {
            throw new BussException("消息发送失败");
        }
        taskService.complete(contractSaverRefulseRequestBody.getTaskId(),map);
        return ResponseDto.success("合同已成功驳回");
    }
}
