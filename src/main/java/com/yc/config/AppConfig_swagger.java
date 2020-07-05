package com.yc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


//@Configuration		//在测试时因为有这个注解，所以会读入，但是value="${swagger.enabled}"这个读不到，所以测试的时候注掉
@EnableSwagger2	//启用swagger
public class AppConfig_swagger {
	//swagger正式环境是要关闭的
	@Value(value="true")//通过value获取配置
	Boolean swaggerEnabled=true;
	
	@Bean
	public Docket createRestApi(){
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				//是否开启
				.enable(swaggerEnabled).select()
				//扫描的路径包	只有包中的类有swagger的注解，则启用
				.apis(RequestHandlerSelectors.basePackage("com.yc"))
				//指定路径处理		PathSelectors.any()代表所有路径
				.paths(PathSelectors.any()).build().pathMapping("/");
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("共享单车开关锁接口")
				.description("springboot | swagger")
				.contact(new Contact("wys", "www.baidu.com", "1442044077@qq.com"))
				.version("1.0.0")
				.build();
	}		
	
}
