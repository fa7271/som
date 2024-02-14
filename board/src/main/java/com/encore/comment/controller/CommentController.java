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
@RequestMapping("/board")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{id}/comment") // comment create
    public SomException commentCreate(@PathVariable Long id, CommentReqDto commentReqDto, HttpServletRequest httpServletRequest){
        String filteredContents = (String) httpServletRequest.getAttribute("filteredContents"); // 욕설 필터링
        if (filteredContents != null) {
            commentReqDto.setContents(filteredContents);
        }
        Comment comment = commentService.create(id, commentReqDto);
        return new SomException(ResponseCode.SUCCESS, comment);
    }

    @GetMapping("/{id}/comment/{commentId}/update")
    public SomException commentUpdate(@PathVariable Long id, @PathVariable Long commentId, CommentReqDto commentReqDto) {
        Comment comment = commentService.create(id, commentReqDto);
        return new SomException(ResponseCode.SUCCESS, comment);
//        List<CommentResDto> commentResDtos = commentService.list();
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
