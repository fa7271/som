package com.encore.admin.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RankingListResponse {
    private List<Ranking> rankingList;
}