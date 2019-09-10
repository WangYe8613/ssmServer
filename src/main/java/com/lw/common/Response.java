package com.lw.common;

import com.google.gson.Gson;

/**
 * @author wangye
 * 返回结果集对象
 */

public class Response<T> {

    //返回错误码
    private Integer code;
    //返回信息
    private String message;
    //返回对象
    private T data;

    public Response(){
        this.code = ResponseCode.SUCCESS.value();
        this.message = ResponseCode.SUCCESS.getReasonPhrase();
    }

    public Response( ResponseCode resultCode, T data){
        this.data = data;
        this.code = resultCode.value ();
        this.message = resultCode.getReasonPhrase ();

    }
    public Response(ResponseCode resultCode){
        this(resultCode,null);
    }

    public void setResponse(ResponseCode resultCode){
        this.code = resultCode.value();
        this.message = resultCode.getReasonPhrase();
    }

    public Integer getCode ( ) {
        return code;
    }

    public void setCode ( Integer code ) {
        this.code = code;
    }

    public String getMessage ( ) {
        return message;
    }

    public void setMessage ( String message ) {
        this.message = message;
    }

    public T getData ( ) {
        return data;
    }

    public void setData ( T data ) {
        this.data = data;
    }
    public String toJson(){
        return new Gson().toJson(this);
    }
}
