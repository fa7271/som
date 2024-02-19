package com.encore.admin.dto;

import com.encore.common.support.ResponseCode;
import lombok.Data;

@Data
public class MemberUpdateRequest {
    private Long id;
    private String nickname;
    private String password;

    public MemberUpdateRequest(ResponseCode responseCode, SignInResponse byId) {
    }
}
