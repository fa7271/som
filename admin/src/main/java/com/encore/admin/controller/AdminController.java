package com.encore.admin.controller;


import com.encore.admin.domain.Member;
import com.encore.admin.dto.*;
import com.encore.admin.service.MemberService;
import com.encore.common.support.DefaultResponse;
import com.encore.common.support.ResponseCode;
import com.encore.common.support.SomException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin/member")
public class AdminController {

    private final MemberService memberService;


    @Autowired
    public AdminController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public DefaultResponse.ListResponse<SignInResponse> getUserList(Pageable pageable) {
        return new DefaultResponse.ListResponse<SignInResponse>(memberService.findAll(pageable));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/detail/{id}")
    public DefaultResponse<SignInResponse> getMemberDetail(@PathVariable(name = "id") Long id) {
        return new DefaultResponse<SignInResponse>(memberService.findById(id));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("update/{id}")
    public DefaultResponse<ResponseCode> updateMember(@PathVariable(value = "id") Long id, @RequestBody MemberUpdateRequest memberUpdateRequest) {
        memberService.update(id,memberUpdateRequest);
        return new DefaultResponse<ResponseCode>(ResponseCode.SUCCESS);

    }


    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    public DefaultResponse<ResponseCode> deleteUser(@PathVariable(value = "id") Long id) {
        memberService.delete(id);
        return new DefaultResponse<ResponseCode>(ResponseCode.SUCCESS);
    }

    @GetMapping("/ranking/top10")
    public DefaultResponse<RankingListResponse> loadRankingTop10List() {
        return new DefaultResponse<RankingListResponse>(memberService.loadRankingListTop10());
    }

    @GetMapping("/members")
    public DefaultResponse.PagedResponse<Member> members(@PageableDefault(size = 15, sort = "createdTime",
            direction = Sort.Direction.DESC) Pageable pageable) {
        return new DefaultResponse.PagedResponse<Member>(memberService.findAllPage(pageable));
    }



    @PatchMapping("/updateUser")
    public DefaultResponse<SignInResponse> UpdateUserPage(@RequestParam("id") Long id) {
        return new DefaultResponse<SignInResponse>(memberService.findById(id));


    }
}

