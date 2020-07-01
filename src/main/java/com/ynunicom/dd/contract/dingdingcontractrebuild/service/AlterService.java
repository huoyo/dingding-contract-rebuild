package com.ynunicom.dd.contract.dingdingcontractrebuild.service;

import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.ContractAlterRequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author: jinye.Bai
 * @date: 2020/7/1 9:37
 */
public interface AlterService {
    public Map<String,Object> alterApply(ContractAlterRequestBody contractAlterRequestBody,String accessToken,String userId);
}
