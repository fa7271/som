package com.encore.post.controller;

import com.encore.common.CommonResponse;
import com.encore.post.domain.Post;
import com.encore.post.dto.PostSaveReqDto;
import com.encore.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/board/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<CommonResponse> postCreate(PostSaveReqDto postSaveReqDto){
        Post post = postService.create(postSaveReqDto);
        System.out.println(postSaveReqDto.getTitle());
        System.out.println(postSaveReqDto.getContents());
        return new ResponseEntity<>(
                new CommonResponse(HttpStatus.CREATED, "post succesfully create", post.getEmail())
                , HttpStatus.CREATED);
    }

}
