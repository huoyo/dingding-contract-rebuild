package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ResponseDto;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.ContractAlterRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.AlterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: jinye.Bai
 * @date: 2020/7/1 9:30
 */
@Deprecated
@RestController
@RequestMapping("/alter")
public class RunningContractAlterController {

    @Resource
    AlterService alterService;

    /**
     *
     * @param contractAlterRequestBody    method只能为以下三种之一：
     *                                    alter:修改
     *                                    preEnd: 提前终止
     *                                    continue: 续签
     * @param accessToken
     * @param userId
     * @return
     */
    @PostMapping
    public ResponseDto alter(ContractAlterRequestBody contractAlterRequestBody, @RequestParam("accessToken")String accessToken, @RequestParam("userId") String userId){
        return ResponseDto.success(alterService.alterApply(contractAlterRequestBody, accessToken, userId));
    }

}
