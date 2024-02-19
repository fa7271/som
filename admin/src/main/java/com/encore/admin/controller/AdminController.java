package com.encore.admin.controller;


import com.encore.admin.dto.*;
import com.encore.admin.service.MemberService;
import com.encore.common.support.ResponseCode;
import com.encore.common.support.SomException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
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
    public SomException getUserList(Pageable pageable) {
        return new SomException(ResponseCode.SUCCESS, memberService.findAll(pageable));
    }


    @GetMapping("/detail/{id}")
    public MemberDetailResponse getMemberDetail(@PathVariable(name = "id") Long id) {
        return new MemberDetailResponse(ResponseCode.SUCCESS, memberService.findById(id));
    }


//    @GetMapping("create")
//    public SignUpRequest createUser() {
//        return new SomException(ResponseCode.SUCCESS_CREATE_MEMBER, memberService.save);
//
//    }


    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("update/{id}")
    public MemberUpdateRequest updateUser(@PathVariable(value = "id") Long id) {

        return new MemberUpdateRequest(ResponseCode.SUCCESS, memberService.findById(id));

    }

    //@PreAuthorize("hasRole('ADMIN',)")
    @PatchMapping("update/{id}")
    public String updateUser(MemberUpdateRequest memberUpdateRequest, Model model) {
        memberService.update(memberUpdateRequest);
        return "admin/update-user";
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id) {
        memberService.delete(id);
        return "OK";
    }


    @PostMapping("/ranking")
    public RankingListResponse loadRankingList(@RequestBody RankingListRequest request) {

        return memberService.loadRankingList(request);
    }

    @GetMapping("/ranking/top10")
    public SomException loadRankingTop10List() {
        return new SomException(ResponseCode.SUCCESS, memberService.loadRankingListTop10());
    }

    @GetMapping("/members")
    public SomException members(@PageableDefault(size = 15, sort = "createdTime",
            direction = Sort.Direction.DESC) Pageable pageable) {
        return new SomException(ResponseCode.SUCCESS, memberService.findAllPage(pageable));
    }


    @GetMapping("/myinfo")
    public SomException myInfo(MemberResponse memberResponse) {
        return new SomException(ResponseCode.SUCCESS, memberService.findMyInfo());

    }


    @PatchMapping("/updateUser")
    public SomException UpdateUserPage(@RequestParam("id") Long id) {
        return new SomException(ResponseCode.SUCCESS, memberService.findById(id));
    }

}

