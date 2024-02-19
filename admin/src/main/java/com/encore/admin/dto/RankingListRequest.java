package com.encore.admin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RankingListRequest {
    private List<String> emailList;
}
