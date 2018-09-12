package com.skyline.wallet.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常统一处理方法
 */
@ControllerAdvice
public class MyHandlerAdvice {
	/**
     * 全局捕获异常，只要作用在@RequestMapping方法上，所有的信息都会被捕捉到
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map<String,Object> errorHandler(Exception ex){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",-1);
        map.put("msg",ex.getMessage());
        ex.printStackTrace();
        return  map;
    }
    
    /**
     * 自定义异常
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public Map<String,Object> errorBussinessHandler(BusinessException ex){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",ex.getCode());
        map.put("msg",ex.getMsg());
        return  map;
    }
}
