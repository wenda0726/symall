package com.sjtu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * swagger可以帮助我们生成接口文档
     * 1.配置文档信息
     * 2.配置生成的接口规则
     */

    //Docket 就是用来封装接口文档信息的
    @Bean
    public Docket getDocket(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2); //指定文档的风格
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        apiInfoBuilder.title("《思源商城》后端接口说明")
                .description("此文档详细说明了《思源商城》后端接口的规范")
                .version("2.0.1")
                .contact(new Contact("wendaSu","www.wendaSu.com","wendaSu@sjtu.edu.cn"));//支持链式调用
        ApiInfo apiInfo = apiInfoBuilder.build();
        docket.apiInfo(apiInfo)//指定生成文档的封面信息：文档标题、版本、作者
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sjtu.controller"))//指定要为哪些控制器生成接口文档
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
