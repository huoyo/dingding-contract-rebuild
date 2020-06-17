package com.ynunicom.dd.contract.dingdingcontractrebuild.controller.exceptioncontroller;

import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ResponseDto;
import com.ynunicom.dd.contract.dingdingcontractrebuild.exception.BussException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: jinye.Bai
 * @date: 2020/6/17 15:55
 */
@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(BussException.class)
    public ResponseDto bussException(BussException e){
        log.error(e.getMsg());
        return ResponseDto.failed(e.getMsg());
    }


}
