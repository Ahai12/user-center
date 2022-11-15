package com.ahai.usercenter.commons;


import lombok.Data;

/**
 * 错误码
 * @author Ahai
 */

public enum ErrorCode {

    /**
     * 请求响应成功
     */
    SUCCESS(0,"响应成功",""),

    /**
     * 请求参数错误
     */
    PARAMS_ERROR(40000,"请求参数错误",""),

    /**
     * 请求数据为空
     */
    NULL_ERROR(40001,"请求数据为空",""),

    /**
     * 用户未登录
     */
    NOT_LOGIN(400100,"用户未登录",""),

    /**
     * 用户无权限
     */
    NO_AUTH(400101,"用户无权限",""),

    /**
     * 系统内部异常
     */
    SYSTEM_ERROR(50000,"系统内部异常",""),

    /**
     * 用户重复
     */
    USERDuplication_ERROR(400102,"用户重复","");
    ;


    /**
     * 状态码
     */
    private final int code;

    private final String message;

    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
