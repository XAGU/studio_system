package com.xagu.studio.studiosystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author xagu
 * Created on 2020/6/22
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Configuration
public class Swagger2Configuration {

    /**
     * 版本
     */
    public static final String VERSION = "1.0.0";

    /**
     * 门户api，接口前缀：portal
     *
     * @return
     */
    @Bean
    public Docket portalApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(portalApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xagu.studio.studiosystem.controller"))
                // 可以根据url路径设置哪些请求加入文档，忽略哪些请求
                .paths(PathSelectors.any())
                .build()
                .groupName("API");
    }

    private ApiInfo portalApiInfo() {
        return new ApiInfoBuilder()
                //设置文档的标题
                .title("Studio接口文档")
                // 设置文档的描述
                .description("门户接口文档")
                // 设置文档的版本信息-> 1.0.0 Version information
                .version(VERSION)
                .build();
    }

}
