package com.encore.admin.dto;

import com.encore.admin.domain.Member;
import com.encore.common.support.Role;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignInResponse {
    private Long id;
    private String email;
    private String nickname;
    private String password;
    private Role role;

    public static SignInResponse of(Member member) {
        SignInResponse signInResponse = SignInResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .password(member.getPassword())
                .email(member.getEmail())
                .build();

        return signInResponse;
    }
}
