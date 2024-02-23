package com.encore.admin.controller;


import com.encore.admin.config.JwtTokenProvider;
import com.encore.admin.domain.Member;
import com.encore.admin.dto.SignInRequest;
import com.encore.admin.dto.SignUpRequest;
import com.encore.admin.dto.VertifyCodeDtoReq;
import com.encore.admin.service.AccountService;
import com.encore.common.support.DefaultResponse;
import com.encore.common.support.ResponseCode;
import com.encore.common.support.SomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/admin/account")
public class AccountController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AccountService service;

    public AccountController(JwtTokenProvider jwtTokenProvider, AccountService service) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.service = service;
    }

    @PostMapping("/signup")
    public DefaultResponse<ResponseCode> signup(@RequestBody SignUpRequest signUpRequest) throws MessagingException {
        service.signup(signUpRequest);
        return new DefaultResponse<ResponseCode>(ResponseCode.SUCCESS_CREATE_MEMBER);
    }

    @PostMapping("/signin")
    public DefaultResponse<Map<String, Object>> signin( @RequestBody SignInRequest signInRequest) {

        Member member = service.signin(signInRequest);
        String jwt = jwtTokenProvider.createdToken(member.getEmail(), member.getRole().name());

        Map<String, Object> memberInfo = new HashMap<>();
        memberInfo.put("email", member.getEmail());
        memberInfo.put("token", jwt);
        memberInfo.put("role", member.getRole().name());
        return new DefaultResponse<Map<String, Object>>(memberInfo);
    }

    @GetMapping("/verify-code/{email}/{code}")
    public DefaultResponse<ResponseCode> sendCodeVerify (
            @PathVariable String email,
            @PathVariable String code
    ) {
        service.verifyEmailCode(email, code);
        return new DefaultResponse<>(ResponseCode.SUCCESS_CREATE_MEMBER);
    }


    @GetMapping("/findPassword/{email}")
    public DefaultResponse<ResponseCode> findPassword(@PathVariable String email) throws MessagingException {
        service.findPassword(email);
        return new DefaultResponse<ResponseCode>(ResponseCode.SUCCESS_CREATE_MEMBER);
    }

    @GetMapping("/password/verify-code/{email}/{code}")
    public DefaultResponse<ResponseCode> sendCode (
            @PathVariable String email,
            @PathVariable String code
    ) {
        service.verifyEmailCodeForPassword(email, code);
        return new DefaultResponse<>(ResponseCode.SUCCESS);
    }

    @PostMapping("/vertifycode")
    public DefaultResponse<ResponseCode> vertifyCode(@RequestBody VertifyCodeDtoReq vertifyCodeDtoReq) {
        service.vertifyCode(vertifyCodeDtoReq);
        return new DefaultResponse<>(ResponseCode.SUCCESS_CHANGE_MEMBER_PASSWORD);
    }
}
