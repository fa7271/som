package com.encore.admin.dto;

import com.encore.common.support.ResponseCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberUpdateRequest {

    private String nickname;
    private String password;

    public MemberUpdateRequest(ResponseCode responseCode, SignInResponse byId) {
    }
}
