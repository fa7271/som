package com.encore.springproject.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberRequestDto {
    private String emailId;
    private String nickname;
    private String password;

}
