package com.encore.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteLocatorConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/admin/**")
                        .uri("http://localhost:8001"))
                .route(r -> r.path("/board/**")
                        .uri("http://localhost:8002"))
                .route(r -> r.path("/chat/**")
                        .uri("http://localhost:8003"))
                .build();
    }


}
