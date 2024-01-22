package com.encore.springproject.admin.dto;

import com.encore.springproject.admin.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberResponseDTO {
    private Long id;
    private String emailId;
    private String nickname;
    private String password;

    public static MemberResponseDTO of(Member member) {
        MemberResponseDTO memberResponseDTO = MemberResponseDTO.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .password(member.getPassword())
                .emailId(member.getEmailId())
                .build();

        return memberResponseDTO;
    }
}
