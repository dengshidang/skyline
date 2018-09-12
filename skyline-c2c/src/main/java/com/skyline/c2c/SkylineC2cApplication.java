package com.skyline.c2c;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;



@EnableSwagger2
@SpringBootApplication
@MapperScan(basePackages = "com.skyline.*.mapper")
@EnableScheduling
@EnableTransactionManagement
@EnableAsync
@EnableFeignClients
public class SkylineC2cApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkylineC2cApplication.class, args);
	}
}
