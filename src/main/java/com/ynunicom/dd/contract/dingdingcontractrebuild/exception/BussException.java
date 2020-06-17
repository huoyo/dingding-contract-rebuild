package com.ynunicom.dd.contract.dingdingcontractrebuild.exception;

import lombok.Getter;

/**
 * @author: jinye.Bai
 * @date: 2020/6/17 15:02
 */
@Getter
public class BussException extends Exception {
    String msg;
    public BussException(String msg){
        this.msg = msg;
    }
}
