package com.ynunicom.dd.contract.dingdingcontractrebuild.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.TaskOptionService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.UserInfoService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.UserVerify;
import lombok.SneakyThrows;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: jinye.Bai
 * @date: 2020/6/18 18:21
 */
@Service
public class TaskOptionServiceImpl implements TaskOptionService {

    @Resource
    UserVerify userVerify;

    @Autowired
    TaskService taskService;

    @Autowired
    UserInfoService userInfoService;

    @SneakyThrows
    @Override
    public JSONArray getByAssigneeUserId(String accessToken, String userId) {
        if (!userVerify.verify(accessToken,userId)){
            throw new BussException("用户不存在");
        }
        JSONArray jsonArray = new JSONArray();
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(userId).list();
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (Task task :
                taskList) {
            Map<String,Object> map = taskService.getVariables(task.getId());
            map.put("taskId",task.getId());
            mapList.add(map);
        }
        jsonArray.addAll(mapList);
        return jsonArray;
    }

    @SneakyThrows
    @Override
    public JSONArray getByApplyUserId(String accessToken, String applyUserId) {
        if (!userVerify.verify(accessToken,applyUserId)){
            throw new BussException("用户不存在");
        }
        JSONArray jsonArray = new JSONArray();
        List<Task> taskList = taskService.createTaskQuery().taskVariableValueEquals("applyUserId",applyUserId).list();
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (Task task :
                taskList) {
            Map<String,Object> map = taskService.getVariables(task.getId());
            map.put("taskId",task.getId());
            mapList.add(map);
        }
        jsonArray.addAll(mapList);
        return jsonArray;
    }
}
