package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractInfoEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.ContractInfoMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.status.ContractInfoStatus;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ResponseDto;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.EndRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jinye.Bai
 * @date: 2020/7/15 12:52
 */
@RestController
@RequestMapping("/end")
public class EndController {

    @Autowired
    ContractInfoMapper contractInfoMapper;

    @SneakyThrows
    @PostMapping
    public ResponseDto end(@RequestParam("accessToken")String accessToken, @RequestParam(value = "userId")String userId, EndRequestBody endRequestBody){
        ContractInfoEntity contractInfoEntity = contractInfoMapper.selectById(endRequestBody.getContractId());
        if (contractInfoEntity==null){
            throw new BussException(endRequestBody.getContractId()+"合同不存在");
        }
        contractInfoEntity.setStatu(ContractInfoStatus.ENDED);
        contractInfoMapper.updateById(contractInfoEntity);
        return ResponseDto.success("合同到期终止成功");
    }
}
