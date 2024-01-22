package com.encore.springproject.admin.dto;

import lombok.Data;

@Data
public class MemberUpdateRequestDTO {
    private Long id;
    private String nickname;
    private String password;
}
