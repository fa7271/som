package com.encore.admin.securities;

//CORS 설정 vue 사용시 포트 달라지기 때문

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("https://www.greatjang.shop") //Route53 레코드이름
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true); //보안처리 관련 credentials
    }
}
