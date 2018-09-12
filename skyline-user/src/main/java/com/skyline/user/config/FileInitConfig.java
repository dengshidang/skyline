package com.skyline.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 获取application.properties配置文件存储路径
 * @author kwq 
 * time 2018-07-20
 */
@Configuration
@ConfigurationProperties(prefix = "host.file")
@PropertySource(value = "application.properties")
public class FileInitConfig {

	
	private String path;
	
	private String contextAddress;

	public String getContextAddress() {
		return contextAddress;
	}

	public void setContextAddress(String contextAddress) {
		this.contextAddress = contextAddress;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
