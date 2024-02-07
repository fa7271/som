package com.encore.post.service;

import com.encore.post.feign.admin.AdminInternalClient;
import org.springframework.stereotype.Service;

@Service
public class FeignService {
    private final AdminInternalClient adminInternalClient;

    public FeignService(AdminInternalClient adminInternalClient) {
        this.adminInternalClient = adminInternalClient;
    }

    public String adminPing() {
        return adminInternalClient.adminPing();
    }
}
