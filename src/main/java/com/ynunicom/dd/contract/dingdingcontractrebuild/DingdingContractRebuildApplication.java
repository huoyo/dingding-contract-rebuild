package com.ynunicom.dd.contract.dingdingcontractrebuild;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 白金晔
 */
@SpringBootApplication(
        scanBasePackages = {"com.ynunicom.dd.contract.dingdingcontractrebuild"},
        exclude = {DruidDataSourceAutoConfigure.class,org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
)
@MapperScan({"com.gitee.sunchenbin.mybatis.actable.dao.*","com.ynunicom.dd.contract.dingdingcontractrebuild.dao.*"} )
@ComponentScan({"com.gitee.sunchenbin.mybatis.actable.manager.*","com.ynunicom.dd.contract.dingdingcontractrebuild"})
@EnableScheduling
public class DingdingContractRebuildApplication {

    public static void main(String[] args) {
        SpringApplication.run(DingdingContractRebuildApplication.class, args);
    }

}
