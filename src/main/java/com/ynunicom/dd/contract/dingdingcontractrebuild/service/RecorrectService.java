package com.ynunicom.dd.contract.dingdingcontractrebuild.service;

import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.ContractRecorrectRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.LegalRecorrectRequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author: jinye.Bai
 * @date: 2020/6/23 18:56
 */
public interface RecorrectService {
    public Map<String,Object> recorrect(String accessToken,ContractRecorrectRequestBody contractRecorrectRequestBody);

    public Map<String,Object> legalRecorrect(String accessToken, String userId, LegalRecorrectRequestBody legalRecorrectRequestBody);
}
