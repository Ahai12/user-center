package com.ahai.usercenter.commons;

public class ResultUtils<T> {


    /**
     * 方法调用成功
     * @param data
     * @param <T>
     * @return 0
     */
    public static <T> BaseResponse<T> success(T data){

        return new BaseResponse<T>(0,"success",data,"");
    }


    /**
     * 失败
     * @param errorCode
     * @return
     */
    public static  BaseResponse error(ErrorCode errorCode){

        return new BaseResponse<>(errorCode);
    }


    /**
     * 失败
     * @param errorCode
     * @param message
     * @param description
     * @return
     */
    public static  BaseResponse error(ErrorCode errorCode,String message,String description){

        return new BaseResponse(errorCode.getCode(),message,description);
    }

    /**
     * 失败
     * @param errorCode
     * @param description
     * @return
     */
    public static  BaseResponse error(ErrorCode errorCode,String description){

        return new BaseResponse(errorCode.getCode(),description);
    }

    /**
     * 失败
     * @param code
     * @param messsage
     * @param description
     * @return
     */
    public static  BaseResponse error(int code,String messsage,String description){

        return new BaseResponse(code,messsage,description);
    }






}
