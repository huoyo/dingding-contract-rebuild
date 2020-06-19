package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ResponseDto;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.JudgeRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.JudgeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: jinye.Bai
 * @date: 2020/6/19 11:12
 */
@RestController
@RequestMapping("/judge")
public class JudgeController {

    @Resource
    JudgeService judgeService;

    @PostMapping
    public ResponseDto judge(@RequestParam("accessToken")String accessToken, @RequestBody @Validated JudgeRequestBody judgeRequestBody){
        return ResponseDto.success(judgeService.judge(accessToken,judgeRequestBody));
    }

}
