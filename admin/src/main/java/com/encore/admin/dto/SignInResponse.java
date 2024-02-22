package com.encore.admin.dto;

import com.encore.admin.domain.Member;
import com.encore.common.support.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponse {
    private Long id;
    private String email;
    private String nickname;
    private Role role;
    private Long ranking;
    private boolean active;
    public static SignInResponse of(Member member) {
        SignInResponse signInResponse = SignInResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .role(member.getRole())
                .active(member.isActive())
                .ranking(member.getRanking()) // 멤버조회 랭킹 추가
                .build();

        return signInResponse;
    }
}
