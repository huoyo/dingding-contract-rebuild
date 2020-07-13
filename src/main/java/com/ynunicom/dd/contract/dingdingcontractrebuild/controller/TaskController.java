package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ResponseDto;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.ContractApplyRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.HurryUpRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.TaskOptionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getOne")
    public ResponseDto getOneByUserIdAndTaskId(@RequestParam("accessToken")String accessToken,@RequestParam("userId")String userId,@RequestParam("taskId")String taskId){
        return ResponseDto.success(taskOptionService.getOneByApplyUserIdAndContractId(accessToken,userId,taskId));
    }


    @GetMapping("/getByRole")
    public ResponseDto getByRole(@RequestParam("accessToken")String accessToken,@RequestParam("userId")String userId){
        return ResponseDto.success(taskOptionService.getByRole(accessToken,userId));
    }

    /**
     * data-form格式
     * @param contractApplyRequestBody
     * @return
     */
    @PostMapping("/startNewInst")
    public ResponseDto startNewInst(ContractApplyRequestBody contractApplyRequestBody,@RequestParam("accessToken")String accessToken){
        return ResponseDto.success(taskOptionService.startNewInst(contractApplyRequestBody,accessToken));
    }

    @PostMapping("/hurryUp")
    public ResponseDto hurryUp(@RequestParam("accessToken")String accessToken, @RequestBody HurryUpRequestBody hurryUpRequestBody){
        return ResponseDto.success(taskOptionService.hurryUp(accessToken,hurryUpRequestBody));
    }


}
