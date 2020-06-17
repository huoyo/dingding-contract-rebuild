package com.ynunicom.dd.contract.dingdingcontractrebuild;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 白金晔
 */
@SpringBootApplication(
        scanBasePackages = {"com.ynunicom.dd.contract.dingdingcontractrebuild"},
        exclude = {DruidDataSourceAutoConfigure.class,org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
)
@EnableScheduling
public class DingdingContractRebuildApplication {

    public static void main(String[] args) {
        SpringApplication.run(DingdingContractRebuildApplication.class, args);
    }

}
