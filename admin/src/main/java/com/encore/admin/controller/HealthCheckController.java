package com.encore.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;

@RestController
public class HealthCheckController {

    @GetMapping("/admin")
    public String healthcheck() {
        System.out.println("여기까지 들어옴 ");
        return "OK";
    }
}
