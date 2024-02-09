package com.encore.post.service;


import com.encore.post.domain.Post;
import com.encore.post.dto.PostSaveReqDto;
import com.encore.post.repository.PostRepository;
import org.jboss.jandex.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class PostService{
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post create(PostSaveReqDto postSaveReqDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        LocalDate today = LocalDate.now();
        List<Post> pst = postRepository.findByEmailAndCreatedAtBetween(email, today.atStartOfDay(), today.atTime(23, 59, 59));
        if (pst.size() >= 5) {
            throw new IllegalArgumentException("하루 최대 포스팅 횟수를 넘겼습니다.");
        }

        Post new_post = Post.builder()
                .title(postSaveReqDto.getTitle())
                .contents(postSaveReqDto.getContents())
                .email(email)
                .build();

        Post post = postRepository.save(new_post);
        return post;
    }
}
