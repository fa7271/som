package com.encore.post.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MemberDto {
    private List<String> rankingList;
}
