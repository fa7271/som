package com.encore.like.controller;

import com.encore.common.support.ResponseCode;
import com.encore.common.support.SomException;
import com.encore.like.service.LikesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("board/post")
public class LikesController {
    private final LikesService likeService;

    public LikesController(LikesService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{id}/detail")
    public SomException likePost(@PathVariable Long id, Authentication authentication) {
        List<String> likeUser = likeService.like(id, authentication.getName());
        return new SomException(ResponseCode.SUCCESS, likeUser);
    }
}
