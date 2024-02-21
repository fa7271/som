package com.encore.comment.controller;

import com.encore.comment.domain.Comment;
import com.encore.comment.dto.CommentDetailResDto;
import com.encore.comment.dto.CommentReqDto;
import com.encore.comment.dto.CommentResDto;
import com.encore.comment.service.CommentService;
import com.encore.common.support.DefaultResponse;
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
@RequestMapping("/board")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{id}/comment") // comment create
    public DefaultResponse<ResponseCode> commentCreate(@PathVariable Long id, CommentReqDto commentReqDto, HttpServletRequest httpServletRequest){
        String filteredContents = (String) httpServletRequest.getAttribute("filteredContents"); // 욕설 필터링
        if (filteredContents != null) {
            commentReqDto.setContents(filteredContents);
        }
        System.out.println(id);
        commentService.create(id, commentReqDto);
        return new DefaultResponse<ResponseCode>(ResponseCode.SUCCESS_CREATE_COMMENT);
    }

    @GetMapping("/{id}/comment/list")
    public DefaultResponse.ListResponse<CommentResDto> commentList(@PathVariable Long id){
        List<CommentResDto> commentResDtos= commentService.findAll(id);
        return new DefaultResponse.ListResponse<CommentResDto>(commentResDtos);
    }

    @GetMapping("/{id}/comment/{commentId}/update")
    public DefaultResponse<String> commentUpdate(@PathVariable Long commentId, CommentReqDto commentReqDto) {
        Comment comment = commentService.update(commentId, commentReqDto);
        return new DefaultResponse<>(comment.getContents());
    }

    @GetMapping("/{id}/comment/{commentId}/delete")
    public DefaultResponse<Long> commentDelete(@PathVariable Long commentId) {
        Comment comment = commentService.delete(commentId);
        return new DefaultResponse<Long>(comment.getId());

    }
}
