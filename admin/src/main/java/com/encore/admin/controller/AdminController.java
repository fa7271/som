package com.encore.admin.controller;


import com.encore.admin.domain.Member;
import com.encore.admin.dto.*;
import com.encore.admin.service.MemberService;
import com.encore.common.support.DefaultResponse;
import com.encore.common.support.ResponseCode;
import io.lettuce.core.ScriptOutputType;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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
    public DefaultResponse.PagedResponse<SignInResponse> getUserList(@RequestParam(value = "nickname") String nickname, Pageable pageable) {

        return new DefaultResponse.PagedResponse<SignInResponse>(memberService.findAll(nickname, pageable));
    }



    @GetMapping("/mypage")
    public DefaultResponse<SignInResponse> getMemberDetail() {
        return new DefaultResponse<SignInResponse>(memberService.findMyInfo());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("update/{id}")
    public DefaultResponse<ResponseCode> updateMember(@PathVariable(value = "id") Long id, @RequestBody MemberUpdateRequest memberUpdateRequest) {
        memberService.update(id, memberUpdateRequest);
        return new DefaultResponse<ResponseCode>(ResponseCode.SUCCESS);

    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    public DefaultResponse<ResponseCode> deleteUser(@PathVariable(value = "id") Long id) {
        memberService.delete(id);
        return new DefaultResponse<ResponseCode>(ResponseCode.SUCCESS);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("active/{id}")
    public DefaultResponse<ResponseCode> activeUser(@PathVariable(value = "id") Long id) {
        System.out.println("########### actite");

        memberService.active(id);
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

