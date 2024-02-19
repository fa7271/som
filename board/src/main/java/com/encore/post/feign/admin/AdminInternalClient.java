package com.encore.post.feign.admin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "admin")
public interface AdminInternalClient {

    @GetMapping("/admin")
    String adminPing();
}
