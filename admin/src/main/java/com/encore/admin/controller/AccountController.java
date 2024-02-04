package com.encore.admin.controller;


import com.encore.admin.dto.SignInResponse;
import com.encore.admin.dto.SignUpRequest;
import com.encore.admin.service.AccountService;
import com.encore.admin.service.MemberService;
import com.encore.common.support.ResponseCode;
import com.encore.common.support.SomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/account")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/signup")
    public SomException signup(SignUpRequest signUpRequest) {
        service.signup(signUpRequest);
        return new SomException(ResponseCode.SUCCESS, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public SomException signin(SignUpRequest signUpRequest) {
        SignInResponse response = service.signin(signUpRequest);
        return new SomException(ResponseCode.SUCCESS, response);
    }
}
