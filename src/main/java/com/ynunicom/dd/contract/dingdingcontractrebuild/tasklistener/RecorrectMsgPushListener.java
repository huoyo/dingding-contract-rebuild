package com.ynunicom.dd.contract.dingdingcontractrebuild.tasklistener;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.ServiceInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.SpringHelper;
import lombok.SneakyThrows;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: jinye.Bai
 * @date: 2020/6/23 17:32
 */

/**
 * 在修改事件发生时发送消息通知经办人
 */
public class RecorrectMsgPushListener implements TaskListener {
    @SneakyThrows
    @Override
    public void notify(DelegateTask delegateTask) {
        TaskService taskService = SpringHelper.getTaskService();
        Map<String,Object> map = taskService.getVariables(delegateTask.getId());
        String userId = (String) map.get("applyUserId");

        RestTemplate restTemplate = SpringHelper.getBean("restTemplate");
        ServiceInfo serviceInfo = SpringHelper.getBean("serviceInfo");
        AppInfo appInfo = SpringHelper.getBean("appInfo");
        JSONObject jsonObject = restTemplate.getForObject("http://localhost:"+serviceInfo.getPort()+"/auth/getToken", JSONObject.class,new HashMap<>(1));
        if (jsonObject==null){
            throw new BussException("发送消息失败");
        }
        String accessToken = jsonObject.getString("data");
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setUseridList(userId);
        request.setAgentId(Long.parseLong(appInfo.getAgentId()));
        request.setToAllUser(false);
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("text");
        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
        msg.getText().setContent("["+ new Date().toString() +"]"+"您有新的合同审批事件,进入应用查看");
        request.setMsg(msg);
        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request,accessToken);
        if (!response.isSuccess()){
            throw new BussException("推送消息失败");
        }
    }
}
