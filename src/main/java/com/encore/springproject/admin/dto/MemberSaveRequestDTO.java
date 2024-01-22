package com.encore.springproject.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberSaveRequestDTO {
    private String emailId;
    private String nickname;
    private String password;

}
