package com.skyline.c2c.exception;

import com.skyline.common.constant.C2cStatusCode;
import com.skyline.common.constant.StatusCode;

public class BusinessException extends RuntimeException  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code;
    private String msg;

    public BusinessException(String code,String msg){
        this.code = code;
        this.msg = msg;
    }
    public BusinessException(C2cStatusCode c2cStatusCode){
        this.code = c2cStatusCode.getCode();
        this.msg = c2cStatusCode.getMsg();
    }
    public BusinessException(StatusCode statusCode){
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
