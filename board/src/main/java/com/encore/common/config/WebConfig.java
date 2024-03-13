package com.encore.common.config;


import com.encore.common.filter.BadWordFiltering;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final BadWordFiltering badWordFiltering;

    public WebConfig(BadWordFiltering badWordFiltering) {
        this.badWordFiltering = badWordFiltering;
    }

    @Bean
    public PasswordEncoder makePassword() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BadWordIntercepter(badWordFiltering))
                .addPathPatterns("/**/post/create")  // 인터셉터를 적용할 경로 패턴
                .addPathPatterns("/**/post/*/update")
                .addPathPatterns("/**/*/comment")
                .excludePathPatterns("/board/post/list/**","/css/**", "/fonts/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("https://www.greatjang.shop","http://www.greatjang.shop")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
