package com.ynunicom.dd.contract.dingdingcontractrebuild.utils;

import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractInfoEntity;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.EntityFatherForMerge;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;

/**
 * @author: jinye.Bai
 * @date: 2020/6/23 19:48
 */
public class ContractInfoEntityMerge {

    @SneakyThrows
    public static EntityFatherForMerge merge(EntityFatherForMerge before, EntityFatherForMerge comming){
            Field[] beforeFields = before.getClass().getDeclaredFields();
            Field[] commingFields = comming.getClass().getDeclaredFields();
            for (int i = 0;i<commingFields.length;i++){
                Field beforeField = beforeFields[i];
                Field commingField = commingFields[i];
                if (Modifier.isStatic(commingField.getModifiers())){
                    continue;
                }
                if (Modifier.isStatic(beforeField.getModifiers())){
                    continue;
                }
                beforeField.setAccessible(true);
                commingField.setAccessible(true);
                if (commingField.getType() == Date.class){
                    if (null!=commingField.get(comming)){
                        beforeField.set(before,commingField.get(comming));
                    }
                }
                if (commingField.getType() == String.class){
                    if (commingField.get(comming)!=null && !((String)commingField.get(comming)).isEmpty()){
                        beforeField.set(before,commingField.get(comming));
                    }
                }
            }
            return before;
     }

}
