package com.encore.springproject.admin.controller;

import com.encore.springproject.admin.domain.Member;
import com.encore.springproject.admin.dto.MemberRequestDto;
import com.encore.springproject.admin.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @GetMapping("")
    public String getUserList() {
        return "admin/user-list";
    }

    @GetMapping("create")
    public String creatUserView() {
        return "admin/create-user";
    }

    @PostMapping("member/create")
    public String creteUser(MemberRequestDto memberRequestDto) {
        userService.save(memberRequestDto);
        return "redirect:/admin";
    }

    @GetMapping("update")
    public String updateUserView() {
        return "admin/update-user";
    }
}
