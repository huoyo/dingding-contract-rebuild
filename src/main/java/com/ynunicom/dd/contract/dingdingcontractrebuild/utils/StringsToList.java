package com.ynunicom.dd.contract.dingdingcontractrebuild.utils;

import java.util.Arrays;
import java.util.List;

/**
 * @author: jinye.Bai
 * @date: 2020/6/18 11:43
 */
public class StringsToList {

    public static List<String> trans(String strings){
        String[] str = strings.split(",");
        return Arrays.asList(str);
    }
}
