package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractInfoEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractInfoEntityForSelect;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.ContractInfoMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.ContractInfoSelectMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ResponseDto;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.RunningContractCommentRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.RoleService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

/**
 * @author: jinye.Bai
 * @date: 2020/6/30 16:16
 */
@RestController
@RequestMapping("/runningContractInfo")
public class RunningContreactInfoController {

    @Autowired
    ContractInfoMapper contractInfoMapper;

    @Autowired
    ContractInfoSelectMapper contractInfoSelectMapper;

    @Resource
    RoleService roleService;

    @Resource
    AppInfo appInfo;

    /**
     * 获取自己履行的合同,合同在履行时为running
     * @param accessToken
     * @param userId
     * @return
     */
    @GetMapping
    public ResponseDto get(@RequestParam("accessToken")String accessToken,@RequestParam("userId") String userId){
        List<ContractInfoEntityForSelect> contractInfoEntityList = contractInfoSelectMapper.selectList(new LambdaQueryWrapper<ContractInfoEntityForSelect>().eq(ContractInfoEntityForSelect::getStatu,"running").eq(ContractInfoEntityForSelect::getContractRunnerUserId,userId));
        return ResponseDto.success(contractInfoEntityList);
    }

    @SneakyThrows
    @GetMapping("/superGet")
    public ResponseDto superGet(@RequestParam("accessToken")String accessToken,@RequestParam("userId") String userId,@RequestParam("offset")Integer offset,@RequestParam("size")Integer size){
        Page page = new Page(offset,size);
        List<String> roleList = roleService.getRoleByUserId(userId,accessToken);
        if (roleList==null||roleList.isEmpty()){
            throw new BussException("用户"+userId+"没有任何角色，操作失败");
        }
        Iterator<String> roleIterator = roleList.iterator();
        boolean flag = false;
        while(roleIterator.hasNext()){
            if (roleIterator.next().equals(appInfo.getSuperUserRole())){
                flag = true;
                break;
            }
        }
        if (!flag){
            throw new BussException(userId+"用户不具有超级权限");
        }
        Page contractInfoEntityList = contractInfoSelectMapper.selectPage(page,new LambdaQueryWrapper<ContractInfoEntityForSelect>().eq(ContractInfoEntityForSelect::getStatu,"running"));
        return ResponseDto.success(contractInfoEntityList);
    }

    @SneakyThrows
    @PostMapping
    public ResponseDto comment(@RequestParam("accessToken")String accessToken,@RequestParam("userId") String userId,@RequestBody @Validated RunningContractCommentRequestBody runningContractCommentRequestBody){
        ContractInfoEntity contractInfoEntity = contractInfoMapper.selectById(runningContractCommentRequestBody.getContractId());
        if (contractInfoEntity==null|| !"running".equals(contractInfoEntity.getStatu())){
            throw new BussException(runningContractCommentRequestBody.getContractId()+"合同不在履行状态");
        }
        if (!userId.equals(contractInfoEntity.getContractRunnerUserId())){
            throw new BussException("用户"+userId+"不是合同履行人");
        }
        contractInfoEntity.setContractRunningComment(runningContractCommentRequestBody.getComment());
        int result = contractInfoMapper.updateById(contractInfoEntity);
        return ResponseDto.success("评论添加成功");
    }

}
