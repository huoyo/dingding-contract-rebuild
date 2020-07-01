package com.ynunicom.dd.contract.dingdingcontractrebuild.utils;

import com.ynunicom.dd.contract.dingdingcontractrebuild.config.info.AppInfo;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.UserInfoService;
import lombok.Getter;
import lombok.Setter;
import org.flowable.engine.TaskService;
import org.flowable.spring.boot.app.App;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author: jinye.Bai
 * @date: 2020/5/27 14:48
 */
@Component
@Getter
@Setter
public class SpringHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringHelper.applicationContext = applicationContext;
    }

    public static <T> T getBean(String beanName) {
        if(applicationContext.containsBean(beanName)){
            return (T) applicationContext.getBean(beanName);
        }else{
            return null;
        }
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> baseType){
        return applicationContext.getBeansOfType(baseType);
    }

    public static Map<String, Object> getBeansOfAnnotation(Class<? extends Annotation> annotationType) {
        return applicationContext.getBeansWithAnnotation(annotationType);
    }

    public static TaskService getTaskService(){
       return applicationContext.getBean(TaskService.class);
    }

    public static UserInfoService getUserInfoService(){
        return applicationContext.getBean(UserInfoService.class);
    }

    public static String getFilePath(){
        return (String) applicationContext.getBean("filePath");
    }

    public static AppInfo getAppInfo(){
        return applicationContext.getBean(AppInfo.class);
    }

}
