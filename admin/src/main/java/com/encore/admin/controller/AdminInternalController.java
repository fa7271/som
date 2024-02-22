package com.encore.admin.controller;

import com.encore.admin.dto.RankingListRequest;
import com.encore.admin.dto.RankingListResponse;
import com.encore.admin.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/internal")
public class AdminInternalController {
    private final MemberService memberService;


    @Autowired
    public AdminInternalController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/info")
    public RankingListResponse loadRankingList(@RequestBody RankingListRequest request) {
        return memberService.loadRankingList(request);
    }
}
