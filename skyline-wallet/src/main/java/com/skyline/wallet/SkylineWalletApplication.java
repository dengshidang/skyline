package com.skyline.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import tk.mybatis.spring.annotation.MapperScan;
@ServletComponentScan //启动监听
@EnableTransactionManagement //开启事务
@EnableEurekaClient
@SpringBootApplication
@MapperScan(basePackages = "com.skyline.*.mapper")//扫描的是mapper.xml中namespace指向值的包位置

public class SkylineWalletApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkylineWalletApplication.class, args);
    }
}
