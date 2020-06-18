package com.ynunicom.dd.contract.dingdingcontractrebuild.config;

import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ProcessInstanceDefKey;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

/**
 * @author: jinye.Bai
 * @date: 2020/6/18 13:53
 */
@Configuration
public class DeployConfig {

    @Value("${dingding.process-instance-def-key}")
    private String settedProcessInstanceDefKey;

    @Value("${dingding.process-filename}")
    private String processFileName;

    @Autowired
    RepositoryService repositoryService;

    @Bean
    public ProcessInstanceDefKey processInstanceDefKey(){
        ProcessInstanceDefKey processInstanceDefKey = new ProcessInstanceDefKey();
        processInstanceDefKey.setKey(settedProcessInstanceDefKey);
        return processInstanceDefKey;
    }

    @Bean
    public Deployment deployment(){
        InputStream inputStream = this.getClass().getResourceAsStream("/processes/"+processFileName);
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().addInputStream(processFileName,inputStream).key(processInstanceDefKey().getKey());
        return deploymentBuilder.deploy();
    }
}
