package com.ynunicom.dd.contract.dingdingcontractrebuild.dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: jinye.Bai
 * @date: 2020/5/22 17:13
 */
public class ResponseDto<T> {

    static final int FAILED_STATUS = 0;
    static final int SUCCESS_STATUS = 1;
    static final String DEFAULT_FAILED_MSG = "失败";
    static final String DEFAULT_SUCCESS_MSG = "成功";

    @JSONField(ordinal = 1)
    private int status;

    @JSONField(ordinal = 2)
    private int code;

    @JSONField(ordinal = 3)
    private String msg;

    @JSONField(ordinal = 4)
    private T data;

    public static ResponseDto success() {
        return builder().code(200).status(1).msg("成功").build();
    }

    public static ResponseDto success(String msg) {
        return builder().code(200).status(1).msg(msg).build();
    }

    public static ResponseDto success(int code, String msg) {
        return builder().status(1).code(code).msg(msg).build();
    }

    public static <T> ResponseDto success(int code, String msg, T data) {
        return builder().status(1).code(code).msg(msg).data(data).build();
    }

    public static <T> ResponseDto success(String msg, T data) {
        return builder().code(200).status(1).msg(msg).data(data).build();
    }

    public static <T> ResponseDto<T> success(T data) {
        return success("成功", data);
    }

    public static ResponseDto failed() {
        return builder().status(0).msg("失败").build();
    }

    public static ResponseDto failed(String msg) {
        return builder().status(0).msg(msg).build();
    }

    public static ResponseDto failed(int code, String msg) {
        return builder().status(0).code(code).msg(msg).build();
    }

    public static <T> ResponseDto failed(int code, String msg, T data) {
        return builder().status(0).code(code).msg(msg).data(data).build();
    }

    public static <T> ResponseDto failed(String msg, T data) {
        return builder().status(0).msg(msg).data(data).build();
    }

    ResponseDto() {
    }

    ResponseDto(int status, int code, String msg, T data) {
        this.status = status;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResponseDto.ResponseDTOBuilder<T> builder() {
        return new ResponseDto.ResponseDTOBuilder();
    }

    public int getStatus() {
        return this.status;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static class ResponseDTOBuilder<T> {
        private int status;
        private int code;
        private String msg;
        private T data;

        ResponseDTOBuilder() {
        }

        public ResponseDto.ResponseDTOBuilder<T> status(int status) {
            this.status = status;
            return this;
        }

        public ResponseDto.ResponseDTOBuilder<T> code(int code) {
            this.code = code;
            return this;
        }

        public ResponseDto.ResponseDTOBuilder<T> msg(String msg) {
            this.msg = msg;
            return this;
        }

        public ResponseDto.ResponseDTOBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ResponseDto<T> build() {
            return new ResponseDto(this.status, this.code, this.msg, this.data);
        }

        @Override
        public String toString() {
            return "ResponseDTO.ResponseDTOBuilder(status=" + this.status + ", code=" + this.code + ", msg=" + this.msg + ", data=" + this.data + ")";
        }
    }
}
