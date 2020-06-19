package com.ynunicom.dd.contract.dingdingcontractrebuild.tasklistener;

import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.JudgePersonEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.SpringHelper;
import org.flowable.engine.TaskService;
import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.task.service.delegate.TaskListener;

import java.util.List;
import java.util.Map;

/**
 * @author: jinye.Bai
 * @date: 2020/6/5 17:30
 */

/**
 * 这个监听器实现了当审批人为空或者当审批已通过时的自动跳转，在任务开始时调用，此监听器优先级应最低
 */
public class SkipTaskListener implements TaskListener {
    private static final long serialVersionUID = 5303026947812088792L;

    @Override
    public void notify(DelegateTask delegateTask) {
        int key = Integer.parseInt(delegateTask.getTaskDefinitionKey());
        TaskService taskService = SpringHelper.getTaskService();
        Map<String,Object> map = taskService.getVariables(delegateTask.getId());
        List<JudgePersonEntity> judgePersonEntityList = (List<JudgePersonEntity>) map.get("stages");
        Boolean cunrrentIsOk = (Boolean) map.get("cunrrentIsOk");
        cunrrentIsOk = false;
        JudgePersonEntity judgePersonEntity = judgePersonEntityList.get(key-1);
        if ("null".equals(judgePersonEntity.getPersonEntity().getUserId())){
            judgePersonEntity.setIsOk(true);
            cunrrentIsOk = true;
            taskService.complete(delegateTask.getId());
        }
        if (judgePersonEntity.getIsOk()){
            cunrrentIsOk = true;
            taskService.complete(delegateTask.getId());
        }

    }
}
