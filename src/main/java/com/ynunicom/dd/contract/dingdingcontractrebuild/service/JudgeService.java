package com.ynunicom.dd.contract.dingdingcontractrebuild.service;

import com.alibaba.fastjson.JSONArray;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.JudgeRequestBody;

/**
 * @author: jinye.Bai
 * @date: 2020/6/19 11:20
 */
public interface JudgeService{

    public JSONArray judge(String accessToken, JudgeRequestBody judgeRequestBody);

}
