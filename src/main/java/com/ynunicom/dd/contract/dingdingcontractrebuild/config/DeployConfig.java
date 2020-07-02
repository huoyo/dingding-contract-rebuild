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

    @Value("${dingding.start-process-instance-def-key}")
    private String startProcessInstanceDefKey;

    @Value("${dingding.start-process-filename}")
    private String startProcessFileName;

    @Value("${dingding.alter-process-instance-def-key}")
    private String alterProcessInstanceDefKey;

    @Value("${dingding.alter-process-filename}")
    private String alterProcessFileName;

    @Autowired
    RepositoryService repositoryService;

    @Bean
    public ProcessInstanceDefKey startProcessInstanceDefKey(){
        ProcessInstanceDefKey processInstanceDefKey = new ProcessInstanceDefKey();
        processInstanceDefKey.setKey(startProcessInstanceDefKey);
        return processInstanceDefKey;
    }

    @Bean
    public ProcessInstanceDefKey alterProcessInstanceDefKey(){
        ProcessInstanceDefKey processInstanceDefKey = new ProcessInstanceDefKey();
        processInstanceDefKey.setKey(alterProcessInstanceDefKey);
        return processInstanceDefKey;
    }
    @Bean
    public Deployment deploymentForStart(){
        InputStream inputStream = this.getClass().getResourceAsStream("/processes/"+startProcessFileName);
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().addInputStream(startProcessFileName,inputStream).key(startProcessInstanceDefKey().getKey());
        return deploymentBuilder.deploy();
    }

    @Bean
    public Deployment deploymentForAlter(){
        InputStream inputStream = this.getClass().getResourceAsStream("/processes/"+alterProcessFileName);
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().addInputStream(alterProcessFileName,inputStream).key(alterProcessInstanceDefKey().getKey());
        return deploymentBuilder.deploy();
    }
}
