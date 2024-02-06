package com.encore.board.service;

import com.encore.board.feign.admin.AdminInternalClient;
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
