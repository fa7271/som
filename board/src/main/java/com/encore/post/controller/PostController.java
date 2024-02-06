package com.encore.post.controller;

import com.encore.post.dto.PostSaveReqDto;
import com.encore.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post/create")
    public String postSave(PostSaveReqDto postSaveReqDto, HttpSession httpSession){
            postService.save(postSaveReqDto);
            return "OK";
    }

}
