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
                        .filters( f ->
                                f.rewritePath("/admin/(.*)", "/$1")
                        )
                        .uri("lb://admin"))
                .route(r -> r.path("/board/**")
                        .filters( f ->
                                f.rewritePath("/board/(.*)", "/$1")
                        )
                        .uri("lb://board"))
                .route(r -> r.path("/chat/**")
                        .filters( f ->
                                f.rewritePath("/chat/(.*)", "/$1")
                        )
                        .uri("lb://chat"))
                .build();
    }


}
