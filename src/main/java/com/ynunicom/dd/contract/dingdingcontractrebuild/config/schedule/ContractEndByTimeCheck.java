package com.ynunicom.dd.contract.dingdingcontractrebuild.config.schedule;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.ServiceInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractInfoEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper.ContractInfoMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import com.ynunicom.dd.contract.dingdingcontractrebuild.utils.MsgSender;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author: jinye.Bai
 * @date: 2020/7/6 14:50
 */
@Component
@EnableScheduling
@Slf4j
public class ContractEndByTimeCheck {

    @Resource
    RestTemplate restTemplate;

    @Resource
    ServiceInfo serviceInfo;

    @Resource
    AppInfo appInfo;

    @Autowired
    ContractInfoMapper contractInfoMapper;

    @SneakyThrows
    @Scheduled(fixedRate = 86400000L,initialDelay = 2000L)
    public void check(){
        List<ContractInfoEntity> contractInfoEntityList = contractInfoMapper.selectShouldEndByTime();
        for (ContractInfoEntity contractInfoEntity:
                contractInfoEntityList) {
            contractInfoEntity.setStatu("ended");
            contractInfoMapper.updateById(contractInfoEntity);
            log.info(contractInfoEntity.getId()+"合同已到期");

            JSONObject jsonObject = restTemplate.getForObject("http://localhost:"+serviceInfo.getPort()+"/auth/getToken", JSONObject.class,new HashMap<>(1));
            if (jsonObject==null){
                throw new BussException("发送消息失败");
            }
            String accessToken = jsonObject.getString("data");
            MsgSender.send(accessToken,contractInfoEntity.getOrganizerUserId(),appInfo,"您的合同"+contractInfoEntity.getContractName()+"已到期");
        }
    }
}
