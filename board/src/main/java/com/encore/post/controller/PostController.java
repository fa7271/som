package com.encore.post.controller;

import com.encore.common.CommonResponse;
import com.encore.common.support.DefaultResponse;
import com.encore.common.support.ResponseCode;
import com.encore.common.support.SomException;
import com.encore.post.domain.Post;
import com.encore.post.dto.PostDetailResDto;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    public DefaultResponse<Long> postCreate(@Valid PostReqDto postReqDto, HttpServletRequest httpServletRequest, BindingResult bindingResult){
        String filteredContents = (String) httpServletRequest.getAttribute("filteredContents"); // 욕설 필터링
        if (bindingResult.hasErrors()) {
            new SomException(ResponseCode.valueOf(bindingResult.getAllErrors().get(0).getDefaultMessage()), HttpStatus.BAD_REQUEST);
        }
        if (filteredContents != null) {
            postReqDto.setContents(filteredContents);
        }

        Post post = postService.create(postReqDto);
        return new DefaultResponse<>(post.getId());
    }

    @GetMapping("/list")
    public DefaultResponse.ListResponse<PostResDto> postList(Pageable pageable) {
        List<PostResDto> postResDtos= postService.findAll(pageable);
        System.out.println(postResDtos);
        return new DefaultResponse.ListResponse<>(postResDtos);
    }

    @GetMapping("/{id}/detail")
    public DefaultResponse<PostDetailResDto> postDetail(@PathVariable Long id){
        PostDetailResDto postDetailResDto = postService.findPostDetail(id);
        return new DefaultResponse<>(postDetailResDto);
    }

    @PatchMapping("/{id}/update")
    public DefaultResponse<Post> postUpdate(@PathVariable Long id, PostReqDto postReqDto, HttpServletRequest httpServletRequest) {
        String filteredContents = (String) httpServletRequest.getAttribute("filteredContents"); // 욕설 필터링
        if (filteredContents != null) {
            postReqDto.setContents(filteredContents);
        }
        Post post = postService.update(id, postReqDto);
        return new DefaultResponse<>(post);
    }

    @DeleteMapping("/{id}/delete")
    public DefaultResponse<Long> postDelete(@PathVariable Long id) {
        Post post = postService.delete(id);

        return new DefaultResponse<Long>(post.getId());
    }
}
