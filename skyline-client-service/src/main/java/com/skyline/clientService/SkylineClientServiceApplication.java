package com.skyline.clientService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@EnableAutoConfiguration
@EnableTransactionManagement //开启事务
@EnableEurekaClient
@SpringBootApplication
@MapperScan(basePackages = "com.skyline.clientService.mapper")//扫描的是mapper.xml中namespace指向值的包位置
@EnableSwagger2
@ServletComponentScan //启动监听
@EnableAsync
@EnableScheduling//启动定时
public class SkylineClientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkylineClientServiceApplication.class, args);
	}
}
