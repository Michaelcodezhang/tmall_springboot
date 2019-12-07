package com.how2java.tmall.util;

import org.aspectj.bridge.IMessage;

public class Result {
    public static int SUCCESS_CODE=0;
    public static int FAIL_CODE=1;

    int code;
    String message;
    Object data;

    public Result(int code,String message,Object data){
        this.code=code;
        this.message= message;
        this.data=data;
    }

    public static Result success(){
        return new Result(SUCCESS_CODE,null,null);
    }
    public static Result success(Object data){
        return new Result(SUCCESS_CODE,"",data);
    }
    public static Result fail(String message){
        return new Result(FAIL_CODE,message,null);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
