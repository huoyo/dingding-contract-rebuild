package com.ynunicom.dd.contract.dingdingcontractrebuild.dao.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: jinye.Bai
 * @date: 2020/6/18 10:13
 */
@Component
public class MetaHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName("createdtime",new Date(),metaObject);
        setFieldValByName("updatetime",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("updatetime",new Date(),metaObject);

    }
}
