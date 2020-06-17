package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ResponseDto;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.UserInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: jinye.Bai
 * @date: 2020/6/17 15:08
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Resource
    UserInfoService userInfoService;

    /**
     * 根据部门id查询人员列表
     * @param accessToken
     * @param deptId 部门id
     * @param offset 分页起点
     * @param size  分页终点
     * @param order 排序规律有以下几种：
     *              entry_asc：代表按照进入部门的时间升序，
     *              entry_desc：代表按照进入部门的时间降序，
     *              modify_asc：代表按照部门信息修改时间升序，
     *              modify_desc：代表按照部门信息修改时间降序，
     *              custom：代表用户定义(未定义时按照拼音)排序
     * @return
     */
    @GetMapping("/getList")
    public ResponseDto getList(@RequestParam("accessToken")String accessToken,@RequestParam("deptId") Long deptId,
                               @RequestParam("offset")Long offset,@RequestParam("size")Long size,@RequestParam(value = "order",required = false)String order){
        return ResponseDto.success(userInfoService.getList(accessToken,deptId,offset,size,order));
    }

    @GetMapping("/get")
    public ResponseDto get(@RequestParam("accessToken")String accessToken,@RequestParam("userId")String userId){
        return ResponseDto.success(userInfoService.getUserInfo(accessToken,userId));
    }
}
