package com.encore.admin.controller;


import com.encore.admin.config.JwtTokenProvider;
import com.encore.admin.domain.Member;
import com.encore.admin.dto.SignInRequest;
import com.encore.admin.dto.SignUpRequest;
import com.encore.admin.service.AccountService;
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
    public SomException signup(@RequestBody SignUpRequest signUpRequest) throws MessagingException {
        service.signup(signUpRequest);
        return new SomException(ResponseCode.SUCCESS, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public SomException signin( @RequestBody SignInRequest signInRequest) {
        System.out.println(signInRequest.getEmail());
        Member member = service.signin(signInRequest);
        System.out.println(member.getEmail());

        //json web token
        //토큰 생성 로직
        String jwt = jwtTokenProvider.createdToken(member.getEmail(), member.getRole().name());

        Map<String, Object> memberInfo = new HashMap<>();
        memberInfo.put("email", member.getEmail());
        memberInfo.put("token", jwt);

        return new SomException(ResponseCode.SUCCESS, memberInfo);
    }

    @GetMapping("/verify-code/{email}/{code}")
    public SomException sendCode (
            @PathVariable String email,
            @PathVariable String code
    ) {
        System.out.println("start");
        service.verifyEmailCode(email, code);
        return new SomException(ResponseCode.SUCCESS_CREATE_MEMBER);
    }
}
