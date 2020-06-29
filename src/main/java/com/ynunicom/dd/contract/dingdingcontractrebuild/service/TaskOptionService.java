package com.ynunicom.dd.contract.dingdingcontractrebuild.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.ContractApplyRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.HurryUpRequestBody;
import org.flowable.task.api.Task;

import java.util.Map;

/**
 * @author: jinye.Bai
 * @date: 2020/6/18 18:20
 */
public interface TaskOptionService {

    public Task taskVarLoadFromOutSide(Task task, ContractApplyRequestBody contractApplyRequestBody, String accessToken);

    public JSONArray getByAssigneeUserId(String accessToken , String userId);

    public JSONArray getByApplyUserId(String accessToken , String applyUserId);

    public Map<String, Object> startNewInst(ContractApplyRequestBody contractApplyRequestBody, String accessToken);

    public String hurryUp(String accessToken, HurryUpRequestBody hurryUpRequestBody);

}
