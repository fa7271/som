package com.encore.admin.controller;

import com.encore.admin.dto.MemberUpdateRequest;
import com.encore.admin.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/member")
public class AdminController {

    private final MemberService memberService;

    @Autowired
    public AdminController(MemberService memberService) {
        this.memberService = memberService;
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    @ResponseBody
    public String getUserList(Model model) {
        model.addAttribute("members", memberService.findAll());
        return "admin/user-list";
    }


    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable(name = "id")Long id, Model model) {
        model.addAttribute("member", memberService.findById(id));
        return "admin/user-detail";
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("create")
    public String creatUserView() {
        return "admin/create-user";
    }


    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("update/{id}")
    public String updateUserView(@PathVariable(value = "id")Long id, Model model) {

        model.addAttribute("member", memberService.findById(id));
        return "admin/update-user";
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("update/{id}")
    public String updateUser(MemberUpdateRequest memberUpdateRequest, Model model) {
        memberService.update(memberUpdateRequest);
        return "admin/update-user";
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    public String deleteUser (@PathVariable(value = "id")Long id) {
        memberService.delete(id);
        return "OK";
    }
}
