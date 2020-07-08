package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractTypeEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.DeptRelation;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.ContractTypeMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.DeptRelationMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.status.ContractTypes;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.DeptEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ResponseDto;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.TypeDeciderRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.UpperDeptFoundByDeptId;
import org.apache.http.annotation.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: jinye.Bai
 * @date: 2020/7/8 9:40
 */
@RestController
@RequestMapping("/typeChoice")
public class ContractTypeChoiceController {

    @Autowired
    DeptRelationMapper deptRelationMapper;

    @Autowired
    ContractTypeMapper contractTypeMapper;

    @Resource
    AppInfo appInfo;

    private List<Map<String,String>> deptManagerJudge(List<Map<String,String>> deptList,String accessToken,TypeDeciderRequestBody typeDeciderRequestBody){
        //塞入承办部门
        Map<String,String> organizerDept = new HashMap<>(1);
        String deptId = UpperDeptFoundByDeptId.find(accessToken,typeDeciderRequestBody.getUserDeptId());
        organizerDept.put("承办部门id",deptId);
        deptList.add(organizerDept);

        //塞入会签
        Map<String,String> other = new HashMap<>(1);
        other.put("会签","");
        deptList.add(other);

        //塞入财务
        Map<String,String> financial = new HashMap<>(1);
        financial.put("财务部门id",appInfo.getFinancialDeptId());
        deptList.add(financial);

        //塞入法律顾问
        Map<String,String> legal = new HashMap<>(1);
        legal.put("法律顾问部门id",appInfo.getLegalDeptId());
        deptList.add(legal);

        return deptList;
    }

    private List<Map<String,String>> viceManagerJudge(List<Map<String,String>> deptList,String accessToken,TypeDeciderRequestBody typeDeciderRequestBody){
        //塞入承办部门
        Map<String,String> organizerDept = new HashMap<>(1);
        String deptId = UpperDeptFoundByDeptId.find(accessToken,typeDeciderRequestBody.getUserDeptId());
        organizerDept.put("承办部门id",deptId);
        deptList.add(organizerDept);

        //塞入会签
        Map<String,String> other = new HashMap<>(1);
        other.put("会签","");
        deptList.add(other);

        //塞入财务
        Map<String,String> financial = new HashMap<>(1);
        financial.put("财务部门id",appInfo.getFinancialDeptId());
        deptList.add(financial);

        //塞入法律顾问
        Map<String,String> legal = new HashMap<>(1);
        legal.put("法律顾问部门id",appInfo.getLegalDeptId());
        deptList.add(legal);

        //塞入公司分管副总
        Map<String,String> viceManager = new HashMap<>(1);
        viceManager.put("公司分管副总经理用户ID",appInfo.getViceManagerId());
        deptList.add(viceManager);

        return deptList;
    }

    private List<Map<String,String>> managerJudge(List<Map<String,String>> deptList,String accessToken,TypeDeciderRequestBody typeDeciderRequestBody){
        //塞入承办部门
        Map<String,String> organizerDept = new HashMap<>(1);
        String deptId = UpperDeptFoundByDeptId.find(accessToken,typeDeciderRequestBody.getUserDeptId());
        organizerDept.put("承办部门id",deptId);
        deptList.add(organizerDept);

        //塞入会签
        Map<String,String> other = new HashMap<>(1);
        other.put("会签","");
        deptList.add(other);

        //塞入财务
        Map<String,String> financial = new HashMap<>(1);
        financial.put("财务部门id",appInfo.getFinancialDeptId());
        deptList.add(financial);

        //塞入法律顾问
        Map<String,String> legal = new HashMap<>(1);
        legal.put("法律顾问部门id",appInfo.getLegalDeptId());
        deptList.add(legal);

        //塞入公司分管副总
        Map<String,String> viceManager = new HashMap<>(1);
        viceManager.put("公司分管副总经理用户ID",appInfo.getViceManagerId());
        deptList.add(viceManager);

        //塞入公司总经理
        Map<String,String> manager = new HashMap<>(1);
        manager.put("公司总经理用户ID",appInfo.getManagerId());
        deptList.add(manager);

        return deptList;
    }


    @GetMapping
    public ResponseDto typeChoice(@RequestParam("accessToken")String accessToken, @RequestParam("userId")String userId){
        List<ContractTypeEntity> contractTypeEntityList = contractTypeMapper.selectList(new LambdaQueryWrapper<ContractTypeEntity>());
        return ResponseDto.success(contractTypeEntityList);
    }

    /**
     * 获得流程审核路径
     * @param accessToken
     * @param userId
     * @param typeDeciderRequestBody
     * @return
     */
    @PostMapping
    public ResponseDto typeDecide(@RequestParam("accessToken")String accessToken, @RequestParam("userId")String userId, @RequestBody TypeDeciderRequestBody typeDeciderRequestBody){

        DeptEntity deptEntity = new DeptEntity();
        List<Map<String,String>> deptList = new ArrayList<>();

        //战略框架合作协议
        if (typeDeciderRequestBody.getType().equals(ContractTypes.STRATEGY_FRAMEWORK)){
            deptList = managerJudge(deptList,accessToken,typeDeciderRequestBody);
        }

        //支出类框架协议
        else if (typeDeciderRequestBody.getType().equals(ContractTypes.EXPENDITURE_FRAMEWORK)){
            deptList = managerJudge(deptList,accessToken,typeDeciderRequestBody);
        }

        //支出类固定金额合同
        else if (typeDeciderRequestBody.getType().equals(ContractTypes.EXPENDITURE_FIXED_AMOUNT)){
            if (typeDeciderRequestBody.getMoney().compareTo(new BigDecimal(1000000))>=0){
                deptList = managerJudge(deptList,accessToken,typeDeciderRequestBody);
            }
            else if (typeDeciderRequestBody.getMoney().compareTo(new BigDecimal(300000))>=0&&typeDeciderRequestBody.getMoney().compareTo(new BigDecimal(1000000))<0){
                deptList = viceManagerJudge(deptList,accessToken,typeDeciderRequestBody);
            }
            else if (typeDeciderRequestBody.getMoney().compareTo(new BigDecimal(300000))<0){
                deptList = deptManagerJudge(deptList,accessToken,typeDeciderRequestBody);
            }
        }

        //收入类合同
        else if (typeDeciderRequestBody.getType().equals(ContractTypes.REVENUE)){
            if (typeDeciderRequestBody.getMoney().compareTo(new BigDecimal(5000000))>=0){
                deptList = managerJudge(deptList,accessToken,typeDeciderRequestBody);
            }
            else if (typeDeciderRequestBody.getMoney().compareTo(new BigDecimal(1000000))>=0&&typeDeciderRequestBody.getMoney().compareTo(new BigDecimal(5000000))<0){
                deptList = viceManagerJudge(deptList,accessToken,typeDeciderRequestBody);
            }
            else if (typeDeciderRequestBody.getMoney().compareTo(new BigDecimal(1000000))<0){
                deptList = deptManagerJudge(deptList,accessToken,typeDeciderRequestBody);
            }
        }

        deptEntity.setDeptIdList(deptList);
        return ResponseDto.success(deptEntity);
    }
}
