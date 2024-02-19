package com.encore.post.controller;

import com.encore.post.service.FeignService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    private final FeignService service;

    public IndexController(FeignService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/board")
    public String index() {
        return "board OK";

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/board/admin")
    public String adminHealthCheck() {
        return service.adminPing();
    }
}
