package com.encore.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/admin")
    public String healthcheck() {
        return "OK";
    }
}
