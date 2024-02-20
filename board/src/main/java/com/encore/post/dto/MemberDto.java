package com.encore.post.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MemberDto {
    private String nickname;
    private String email;
    private Long ranking;
}
