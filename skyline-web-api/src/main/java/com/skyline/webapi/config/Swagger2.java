package com.skyline.webapi.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.service.Parameter;
/**
 * 
* @ClassName: Swagger2
* @Description: TODO()
* @author xiaozhijian
* @date 2018年7月3日
*
 */
@Configuration
public class Swagger2 {
 
	@Bean
	public Docket createRestApi() {
		   ParameterBuilder tokenPar = new ParameterBuilder();  
	        List<Parameter> pars = new ArrayList<Parameter>();  
	        tokenPar.name("token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();  
	        pars.add(tokenPar.build());  
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.skyline.webapi.controller"))
				.paths(PathSelectors.any())
				.build()
				.globalOperationParameters(pars);
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("springboot利用swagger构建api文档")
				.description("简单优雅的restfun风格，http://blog.csdn.net/saytime")
				.termsOfServiceUrl("http://blog.csdn.net/saytime")
				.version("1.0")
				.build();
	}

	
}
