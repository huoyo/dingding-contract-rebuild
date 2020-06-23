package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ResponseDto;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.ContractRecorrectRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.RecorrectService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: jinye.Bai
 * @date: 2020/6/23 17:40
 */
@RestController
@RequestMapping("/recorrect")
public class RecorrectController {

    @Resource
    RecorrectService recorrectService;

    /**
     * 修改，需用form-data格式上传
     * @param accessToken
     * @param contractRecorrectRequestBody
     * @return
     */
    @PostMapping
    public ResponseDto recorrect(@RequestParam("accessToken")String accessToken, ContractRecorrectRequestBody contractRecorrectRequestBody){
        return ResponseDto.success(recorrectService.recorrect(accessToken,contractRecorrectRequestBody));
    }

}
