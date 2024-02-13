package com.encore.comment.controller;

import com.encore.comment.domain.Comment;
import com.encore.comment.dto.CommentDetailResDto;
import com.encore.comment.dto.CommentReqDto;
import com.encore.comment.dto.CommentResDto;
import com.encore.comment.service.CommentService;
import com.encore.common.support.ResponseCode;
import com.encore.common.support.SomException;
import com.encore.post.domain.Post;
import com.encore.post.dto.PostDetailResDto;
import com.encore.post.dto.PostReqDto;
import com.encore.post.dto.PostResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/board/comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create") // Post create
    public SomException commentCreate(CommentReqDto commentReqDto, HttpServletRequest httpServletRequest){
        String filteredContents = (String) httpServletRequest.getAttribute("filteredContents"); // 욕설 필터링
        if (filteredContents != null) {
            commentReqDto.setContents(filteredContents);
        }
        Comment comment = commentService.create(commentReqDto);
        return new SomException(ResponseCode.SUCCESS, comment.getId());
    }

    @GetMapping("/list")
    public SomException commentList() {
        List<CommentResDto> commentResDtos = commentService.list();
        return new SomException(ResponseCode.SUCCESS, commentResDtos);
    }
//    @GetMapping("/list")
//    public SomException postList(Pageable pageable) {
//        List<PostResDto> postResDtos= postService.findAll(pageable);
//        return new SomException(ResponseCode.SUCCESS, postResDtos);
//    }

//    @GetMapping("/{id}/detail")
//    public SomException postDetail(@PathVariable Long id){
//        CommentDetailResDto commentDetailResDto = commentService.findCommentDetail(id);
//        return new SomException(ResponseCode.SUCCESS, commentDetailResDto);
//    }
}
