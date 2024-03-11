package com.encore.post.feign.admin;


import com.encore.common.support.DefaultResponse;
import com.encore.post.dto.MemberReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@FeignClient(url = "http://admin-server")
public interface AdminInternalClient {

    @GetMapping("/admin")
    String adminPing();

    @PostMapping("/admin/internal/info")
    ResponseEntity<Map<String,Object>> memberList(@RequestBody MemberReqDto MemberReqDto);

}
