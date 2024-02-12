package com.encore.admin.dto;

import com.encore.admin.domain.Member;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Ranking {
    private String email;
    private Long ranking;

    public static Ranking of(Member member) {
        return Ranking.builder()
                .email(member.getEmail())
                .ranking(member.getRanking())
                .build();
    }
}
