package com.example.apidocsstudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

//@Configuration
//@EnableSwagger2 // Swagger2 버전 활성화 -> /swagger-ui.html에서 볼 수 있음
public class SwaggerConfig {

    /*
    Docket
    Swagger 버전 설정의 핵심이 되는 Bean
     */
    @Bean
    public Docket apiV1() {
        String version = "V1";
        String title = "holy moly API " + version;
        return new Docket(DocumentationType.SWAGGER_2)
                // false 설정 시 swagger 제공 응답코드(200,401,403,404에 대한 메시지 제거 -> 컨트롤러에서 명시해 줄 것이기 때문
                .useDefaultResponseMessages(false)
                // Docket 빈이 여러개가 존재할경우 충돌하지 않게하기 위해 등록 -> 1개일 경우 생략 가능
                .groupName(version)
                // ApiSelectorBuilder를 생성해줌
                .select()
                // api 스펙이 되어있는 패키지 지정 -> 즉, 컨트롤러가 존재하는 패키지
                .apis(RequestHandlerSelectors.basePackage("com.example.apidocsstudy.controller"))
                // api로 선택된 API 중 특정 Path로 지정된 API 필터링
                .paths(PathSelectors.ant("/api/v1/**"))
                .build()
                // API 문서에 대한 제목, 설명 등을 보여주기위해 설정
                .apiInfo(setApiInfo(title, version))
                ;
    }

    // API 문서에 대한 제목, 설명 등을 세팅
    private ApiInfo setApiInfo(String title, String version) {
        return new ApiInfo(
                title,
                "Swagger API Docs",
                version,
                "www.example.com",
                new Contact("Contact Me", "www.example.com", "hol22mol22@gmail.com"),
                "Licenses",
                "www.example.com",
                new ArrayList<>());
    }

}
