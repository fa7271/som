package com.encore.admin.dto;

import lombok.Data;

@Data
public class MemberUpdateRequest {
    private Long id;
    private String nickname;
    private String password;
}
