package com.encore.post.controller;

import com.encore.common.CommonResponse;
import com.encore.common.support.ResponseCode;
import com.encore.common.support.SomException;
import com.encore.post.domain.Post;
import com.encore.post.dto.PostReqDto;
import com.encore.post.dto.PostResDto;
import com.encore.post.dto.PostSearchDto;
import com.encore.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/board/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create") // Post create
    public SomException postCreate(PostReqDto postReqDto, HttpServletRequest httpServletRequest){
        String filteredContents = (String) httpServletRequest.getAttribute("filteredContents"); // 욕설 필터링
        if (filteredContents != null) {
            postReqDto.setContents(filteredContents);
        }

        Post post = postService.create(postReqDto);
        return new SomException(ResponseCode.SUCCESS, post.getId());
    }

    @GetMapping("/list")
    public SomException postList(Pageable pageable) {
        List<PostResDto> postResDtos= postService.findAll(pageable);
        return new SomException(ResponseCode.SUCCESS, postResDtos);
    }

    @PatchMapping("/{id}/update")
    public SomException itemUpdate(@PathVariable Long id, PostReqDto postReqDto, HttpServletRequest httpServletRequest) {
        String filteredContents = (String) httpServletRequest.getAttribute("filteredContents"); // 욕설 필터링
        if (filteredContents != null) {
            postReqDto.setContents(filteredContents);
        }
        Post post = postService.update(id, postReqDto);
        return new SomException(ResponseCode.SUCCESS, post);
    }

    @DeleteMapping("/{id}/delete")
    public SomException itemDelete(@PathVariable Long id) {
        Post post = postService.delete(id);

        return new SomException(ResponseCode.SUCCESS, post.getId());
    }
}
