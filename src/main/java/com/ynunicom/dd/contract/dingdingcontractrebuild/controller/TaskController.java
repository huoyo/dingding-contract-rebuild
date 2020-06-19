package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ResponseDto;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.TaskOptionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: jinye.Bai
 * @date: 2020/6/18 18:17
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Resource
    TaskOptionService taskOptionService;

    @GetMapping("/getByAssigneeUserId")
    public ResponseDto get(@RequestParam("accessToken")String accessToken,@RequestParam("userId")String userId){
        return ResponseDto.success(taskOptionService.getByAssigneeUserId(accessToken,userId));
    }

    @GetMapping("/getByApplyUserId")
    public ResponseDto getByApplyUserId(@RequestParam("accessToken")String accessToken,@RequestParam("userId")String userId){
        return ResponseDto.success(taskOptionService.getByApplyUserId(accessToken,userId));
    }


}
