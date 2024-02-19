package com.encore.post.feign.admin;

import com.encore.post.dto.MemberDto;
import com.encore.post.dto.MemberReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "admin")
public interface AdminInternalClient {

    @GetMapping("/admin")
    String adminPing();

    @PostMapping("/member/info")
    MemberDto memberList(MemberReqDto MemberReqDto);
}
