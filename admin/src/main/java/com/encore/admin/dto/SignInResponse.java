package com.encore.admin.dto;

import com.encore.admin.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignInResponse {
    private Long id;
    private String emailId;
    private String nickname;
    private String password;

    public static SignInResponse of(Member member) {
        SignInResponse signInResponse = SignInResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .password(member.getPassword())
                .emailId(member.getEmailId())
                .build();

        return signInResponse;
    }
}
