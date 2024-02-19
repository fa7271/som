package com.encore.post.feign.admin;


import com.encore.post.dto.MemberDto;
import com.encore.post.dto.MemberReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(value = "admin")
public interface AdminInternalClient {

    @GetMapping("/admin")
    String adminPing();

    @PostMapping("/admin/internal/info")
    MemberDto memberList(@RequestBody MemberReqDto MemberReqDto);

}
