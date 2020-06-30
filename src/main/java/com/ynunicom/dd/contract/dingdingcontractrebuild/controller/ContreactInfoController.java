package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractInfoEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.ContractInfoMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: jinye.Bai
 * @date: 2020/6/30 16:16
 */
@RestController
@RequestMapping("/contractInfo")
public class ContreactInfoController {

    @Autowired
    ContractInfoMapper contractInfoMapper;

    /**
     * 获取自己履行的合同,合同在履行时为running
     * @param accessToken
     * @param userId
     * @return
     */
    @GetMapping
    public ResponseDto get(@RequestParam("accessToken")String accessToken,@RequestParam("userId") String userId){
        List<ContractInfoEntity> contractInfoEntityList = contractInfoMapper.selectList(new LambdaQueryWrapper<ContractInfoEntity>().eq(ContractInfoEntity::getContractRunnerUserId,userId).eq(ContractInfoEntity::getStatu,"running"));
        return ResponseDto.success(contractInfoEntityList);
    }

}
