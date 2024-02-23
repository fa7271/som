package com.encore.admin.dto;

import lombok.Data;

@Data
public class VertifyCodeDtoReq {
    private String email;
    private String password;
    private String code;
}
