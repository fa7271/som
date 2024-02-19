package com.encore.admin.dto;

import com.encore.admin.domain.Member;
import com.encore.common.support.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
public class MemberResponse {
    private Long id;
    private String email;
    private Role role;
    private String nickname;
    private String password;
    private String tempPassword;
    private boolean shouldChangePw;
    private Long ranking;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MemberResponse from(Member member) {
        MemberResponseBuilder memberResponseBuilder = MemberResponse.builder();
        memberResponseBuilder.id(member.getId());
        memberResponseBuilder.nickname(member.getNickname());
        memberResponseBuilder.email(member.getEmail());
        memberResponseBuilder.password(member.getPassword());

        return memberResponseBuilder.build();
    }
}


