package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ResponseDto;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.DeptService;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: jinye.Bai
 * @date: 2020/6/17 15:13
 */
@RestController
@RequestMapping("/dept")
public class DeptController {
    @Resource
    DeptService deptService;

    /**
     * 部门列表查询
     * @param accessToken
     * @param fetchChild 是否递归显示子部门
     * @param id 父部门ID
     * @return 部门列表
     */
    @SneakyThrows
    @GetMapping("/getList")
    public ResponseDto getList(@RequestParam("accessToken")String accessToken,@RequestParam(value = "fetch_child",required = false)Boolean fetchChild,
                               @RequestParam(value = "id",required = false) String id){
       return ResponseDto.success(deptService.getList(accessToken,fetchChild,id));
    }

}
