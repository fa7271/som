package com.encore.admin.dto;

import com.encore.admin.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignUpRequest {
    private String emailId;
    private String nickname;
    private String password;

//    @Setter
//    @Builder.Default
//    @JsonIgnore
//    private Role role = Role.USER;

    public Member toEntity() {
        return Member.builder()
                .nickname(nickname)
                .emailId(emailId)
                .password(password)
                .build();
    }

}
