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
    private Role role;

    public static SignInResponse of(Member member) {
        SignInResponse signInResponse = SignInResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .role(member.getRole())
                .build();

        return signInResponse;
    }
}
