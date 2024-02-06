import com.encore.ad

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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class PostService{
    private final PostRepository postRepository;
    private final

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post create(PostSaveReqDto postSaveReqDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member post = postRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("not found email"));




        Post new_post = Post.builder()
                .title(postSaveReqDto.getTitle())
                .contents(postSaveReqDto.getContents())
                .build();
        Post post = postRepository.save(new_post);
        return post;
    }
}
