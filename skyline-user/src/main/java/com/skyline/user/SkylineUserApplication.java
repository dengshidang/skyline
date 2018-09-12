package com.skyline.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@EnableTransactionManagement //开启事务
@EnableEurekaClient
@SpringBootApplication
@MapperScan(basePackages = "com.skyline.*.mapper")//扫描的是mapper.xml中namespace指向值的包位置
@EnableSwagger2
@EnableAsync  
public class SkylineUserApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SkylineUserApplication.class, args);
		
	}
}
