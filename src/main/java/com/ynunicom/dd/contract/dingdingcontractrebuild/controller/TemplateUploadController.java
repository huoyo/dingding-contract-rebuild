package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractTemplateEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.ContractTemplateMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.ContractTemplateUploadRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.FileSaver;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: jinye.Bai
 * @date: 2020/6/23 11:04
 */
@RestController
@RequestMapping("/template")
@Slf4j
public class TemplateUploadController {

    @Resource
    String filePath;

    @Autowired
    ContractTemplateMapper contractTemplateMapper;

    @SneakyThrows
    @PostMapping
    public void post(ContractTemplateUploadRequestBody contractTemplateUploadRequestBody){
        if (contractTemplateUploadRequestBody.getFile().isEmpty()){
            throw new BussException("上传文件为空");
        }
        String fileName = FileSaver.save(filePath,contractTemplateUploadRequestBody.getFile());
        ContractTemplateEntity contractTemplateEntity = contractTemplateUploadRequestBody.build();
        contractTemplateEntity.setFilePath(fileName);
        contractTemplateMapper.insert(contractTemplateEntity);
        log.info(fileName+"，模板文件新增");
    }

}
