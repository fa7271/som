package com.encore.admin.dto;

import com.encore.common.support.ResponseCode;
import com.encore.common.support.Role;
import lombok.Getter;

@Getter
public class MemberDetailResponse {


    private Long id;
    private String email;
    private Role role;
    private String nickname;
    private String password;

    public MemberDetailResponse(ResponseCode responseCode, SignInResponse byId) {
    }
}


