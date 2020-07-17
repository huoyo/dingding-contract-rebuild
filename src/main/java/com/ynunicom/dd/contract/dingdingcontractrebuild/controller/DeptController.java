package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.alibaba.fastjson.JSONObject;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ResponseDto;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.DeptService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.UserInfoService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.UpperDeptFoundByDeptId;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: jinye.Bai
 * @date: 2020/6/17 15:13
 */
@RestController
@RequestMapping("/dept")
public class DeptController {
    @Resource
    DeptService deptService;

    @Resource
    UserInfoService userInfoService;


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

    @SneakyThrows
    @GetMapping
    public ResponseDto getDeptInfoByUserId(@RequestParam("accessToken")String accessToken,@RequestParam(value = "userId")String userId){
        JSONObject jsonObject = userInfoService.getUserInfo(accessToken,userId);
        if (jsonObject.isEmpty()){
            throw new BussException("获取用户信息失败");
        }
        if (jsonObject.getInteger("errcode")!=0){
            throw new BussException(jsonObject.getString("errmsg"));
        }
        List<String> deptIdList = jsonObject.getJSONArray("department").toJavaList(String.class);
        JSONObject returner = new JSONObject();
        returner.put("userId",userId);
        returner.put("userName",jsonObject.getString("name"));
        List<Map<String,String>> deptMapList = new ArrayList<>();
        for (String deptId :
                deptIdList) {
                deptMapList.add(deptService.getByDeptId(accessToken,deptId));
        }
        returner.put("deptList",deptMapList);
        return ResponseDto.success(returner);
    }

}
