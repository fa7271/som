package com.encore.post.dto;

import lombok.Data;

import java.util.List;

@Data
public class MemberReqDto {
    private List<String> emailList;
}
