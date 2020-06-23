package com.ynunicom.dd.contract.dingdingcontractrebuild.utils;

import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractInfoEntity;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author: jinye.Bai
 * @date: 2020/6/23 19:48
 */
public class ContractInfoEntityMerge {

    @SneakyThrows
    public static ContractInfoEntity merge(ContractInfoEntity before, ContractInfoEntity comming){
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
                /** 明天处理，旨在合并两个对象的属性
                if (commingField.getType() String){
                    if (!"".equals((String)(commingField.get(comming)))||commingField.get(comming)!=null){
                        beforeField.set(before,commingField.get(comming));
                    }
                }**/
            }
            return before;
     }

}
