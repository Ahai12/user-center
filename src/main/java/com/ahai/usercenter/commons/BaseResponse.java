package com.ahai.usercenter.commons;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private String message;

    private T data;

    private String description;

    public BaseResponse(int code, String message, T data,String description) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.description = description;
    }

    public BaseResponse(int code, String message,String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data) {

        this(code,"",data,"");
    }

    public BaseResponse(ErrorCode errorCode){

        this(errorCode.getCode(),errorCode.getMessage(),null,errorCode.getDescription());
    }





}
