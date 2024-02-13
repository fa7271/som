package com.encore.comment.service;

import com.encore.comment.domain.Comment;
import com.encore.comment.dto.CommentReqDto;
import com.encore.comment.dto.CommentResDto;
import com.encore.comment.repository.CommentRepository;
import com.encore.post.domain.Post;
import com.encore.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public Comment create(Long id, CommentReqDto commentReqDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Comment new_comment = Comment.builder()
                .contents(commentReqDto.getContents())
                .email(email)
                .post(post)
                .createdAt(commentReqDto.getCreatedAt())
                .build();
//        commentRepository.save(new_comment);
//        Comment comment =
        return commentRepository.save(new_comment);
//        return comment;
    }

    public List<CommentResDto> list(){ //⭐⭐중요패턴 코드 외우기
        List<Comment> comments = commentRepository.findAll();
        List<CommentResDto> commentResDtos = new ArrayList<>();
        for(Comment comment : comments){
            CommentResDto commentResDto = new CommentResDto();
            commentResDto.setId(comment.getId());
            commentResDto.setMember_email(commentResDto.getMember_email());
            commentResDto.setContents(commentResDto.getContents());
            commentResDtos.add(commentResDto);
        }
        return commentResDtos;
    }
}
