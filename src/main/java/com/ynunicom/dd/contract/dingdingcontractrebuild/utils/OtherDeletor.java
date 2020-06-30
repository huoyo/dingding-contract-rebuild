package com.ynunicom.dd.contract.dingdingcontractrebuild.utils;

import java.io.File;

/**
 * @author: jinye.Bai
 * @date: 2020/6/30 16:52
 */
public class OtherDeletor {
    public static boolean delete(String fileName){
        String filePath = SpringHelper.getFilePath();
        File file = new File(filePath+"/"+fileName);
        if (!file.exists()){
            return true;
        }
        if (!file.delete()){
            return false;
        }
        return true;
    }
}
