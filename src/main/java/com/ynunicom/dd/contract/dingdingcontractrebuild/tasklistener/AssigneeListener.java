package com.ynunicom.dd.contract.dingdingcontractrebuild.tasklistener;

import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.JudgePersonEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.SpringHelper;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

import java.util.List;
import java.util.Map;

/**
 * @author: jinye.Bai
 * @date: 2020/6/18 18:03
 */

/**
 * 此监听器优先级需要高于其他监听器,在开始时调用
 */
public class AssigneeListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        String str = delegateTask.getTaskDefinitionKey();
        str = str.substring(1);
        int key = Integer.parseInt(str);
        TaskService taskService = SpringHelper.getTaskService();
        Map<String,Object> map = taskService.getVariables(delegateTask.getId());
        List<JudgePersonEntity> judgePersonEntityList = (List<JudgePersonEntity>) map.get("stages");
        JudgePersonEntity judgePersonEntity = judgePersonEntityList.get(key-1);
        String userId = judgePersonEntity.getPersonEntity().getUserId();
        taskService.setAssignee(delegateTask.getId(),userId);
    }
}
