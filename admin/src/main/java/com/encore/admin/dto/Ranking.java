package com.encore.admin.dto;

import com.encore.admin.domain.Member;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Ranking {
    private String nickname;
    private Long ranking;
    private String email;

    public static Ranking of(Member member) {
        return Ranking.builder()
                .nickname(member.getNickname())
                .ranking(member.getRanking())
                .email(member.getEmail())
                .build();
    }
}
