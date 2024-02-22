package com.encore.admin.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String email;
    private String nickname;
    private String password;

}
