package com.waylau.spring.boot.blog.Swagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration //让Spring来加载该类配置
@EnableSwagger2 //启用Swagger2
public class Swagger2 {
    @Bean
    public Docket MapperApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("MapperApi接口")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo18.Mapper"))
                .paths(PathSelectors.any()).build();
    }
    @Bean
    public Docket PersonApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("PersonApi接口")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo18.Person"))
                .paths(PathSelectors.any()).build();
    }
    @Bean
    public Docket ServiceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Service接口")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo18.Service"))
                .paths(PathSelectors.any()).build();
    }

    @Bean
    public Docket UsersApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Users接口")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.waylau.spring.boot.blog.api"))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("APi接口")
                .description("增删改查服务")
                .termsOfServiceUrl("http://www.baidu.com")
                .version("1.0").build();
    }
}
