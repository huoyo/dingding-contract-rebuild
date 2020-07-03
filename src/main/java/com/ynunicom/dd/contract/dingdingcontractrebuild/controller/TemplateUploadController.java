package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractTemplateEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.ContractTemplateMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ResponseDto;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.requestBody.ContractTemplateUploadRequestBody;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.UserInfoService;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.FileSaver;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.UserVerify;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    UserInfoService userInfoService;

    @SneakyThrows
    @PostMapping
    public ResponseDto post(ContractTemplateUploadRequestBody contractTemplateUploadRequestBody,@RequestParam("accessToken")String accessToken,@RequestParam("userId") String userId){
        if (contractTemplateUploadRequestBody.getFile().isEmpty()){
            throw new BussException("上传文件为空");
        }
        String fileName = FileSaver.save(filePath,contractTemplateUploadRequestBody.getFile());
        ContractTemplateEntity contractTemplateEntity = contractTemplateUploadRequestBody.build();
        contractTemplateEntity.setFilePath(fileName);
        contractTemplateMapper.insert(contractTemplateEntity);
        log.info(fileName+"，模板文件新增");
        return ResponseDto.success(fileName+"新增完成");
    }

    @SneakyThrows
    @GetMapping("/get")
    public ResponseDto get(@RequestParam("accessToken")String accessToken,@RequestParam("userId") String userId,@RequestParam("offset")long offset,@RequestParam("size")long size){
        Page page = new Page(offset,size);
        Page contractTemplateEntityPage = contractTemplateMapper.selectPage(page,new LambdaQueryWrapper<ContractTemplateEntity>());
        return ResponseDto.success(contractTemplateEntityPage);
    }

    @SneakyThrows
    @GetMapping("/search")
    public ResponseDto search(@RequestParam("accessToken")String accessToken,@RequestParam("userId") String userId,@RequestParam("name")String name){
        List<ContractTemplateEntity> contractTemplateEntityList = contractTemplateMapper.selectList(new LambdaQueryWrapper<ContractTemplateEntity>().like(ContractTemplateEntity::getStandeTextName,name));
        return ResponseDto.success(contractTemplateEntityList);
    }

}
