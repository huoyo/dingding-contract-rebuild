package com.ynunicom.dd.contract.dingdingcontractrebuild.utils;

import lombok.Getter;
import lombok.Setter;
import org.flowable.engine.TaskService;
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

}
