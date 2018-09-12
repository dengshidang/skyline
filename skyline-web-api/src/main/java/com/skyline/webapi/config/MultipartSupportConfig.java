package com.skyline.webapi.config;


import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
/**
 * 
* <p>Title: MultipartSupportConfig</p>  
* <p>Description: </p>  
* @author kuangwenqiang  
* @date 2018年7月20日
 */
@Configuration
public class MultipartSupportConfig {
	/*
	 * 把默认的编码器换成 SpringFormEncoder
	 * 解决文件上传MultipartFile 对象无法传入到 服务层问题
	 */
	@Autowired  
    private ObjectFactory<HttpMessageConverters> messageConverters;  
	@Bean  
    public Encoder feignFormEncoder() {  
        return new SpringFormEncoder(new SpringEncoder(messageConverters));  
        //如果使用下面注释代码 他在用委托模式的时候就没找到对应的处理器，就抛一个异常出来
        //return new SpringFormEncoder();
    } 
	

}
