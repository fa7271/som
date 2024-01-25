package com.encore.springproject.admin.controller;

import com.encore.springproject.admin.dto.MemberSaveRequestDTO;
import com.encore.springproject.admin.dto.MemberUpdateRequestDTO;
import com.encore.springproject.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String getUserList(Model model) {
        model.addAttribute("members",userService.findAll());
        return "admin/user-list";
    }

    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable(name = "id")Long id, Model model) {
        model.addAttribute("member", userService.findById(id));
        return "admin/user-detail";
    }

    @GetMapping("create")
    public String creatUserView() {
        return "admin/create-user";
    }

    @PostMapping("create")
    public String creteUser(MemberSaveRequestDTO memberSaveRequestDto) {
        userService.save(memberSaveRequestDto);
        return "redirect:/admin";
    }

    @GetMapping("update/{id}")
    public String updateUserView(@PathVariable(value = "id")Long id, Model model) {

        model.addAttribute("member",userService.findById(id));
        return "admin/update-user";
    }

    @PatchMapping("update/{id}")
    public String updateUser(MemberUpdateRequestDTO memberUpdateRequestDTO, Model model) {
        userService.update(memberUpdateRequestDTO);
        return "admin/update-user";
    }

    @DeleteMapping("delete/{id}")
    public String deleteUser (@PathVariable(value = "id")Long id) {
        userService.delete(id);
        return "OK";
    }
}
