package com.encore.admin.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RankingListRequst {
    private List<String> emailList;
}
