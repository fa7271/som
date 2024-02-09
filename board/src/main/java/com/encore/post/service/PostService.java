package com.encore.post.service;


import com.encore.post.domain.Post;
import com.encore.post.dto.PostReqDto;
import com.encore.post.dto.PostResDto;
import com.encore.post.dto.PostSearchDto;
import com.encore.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostService{
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post create(PostReqDto postReqDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Post new_post = Post.builder()
                .title(postReqDto.getTitle())
                .contents(postReqDto.getContents())
                .email(email)
                .build();
        Post post = postRepository.save(new_post);
        return post;
    }

    public Page<PostResDto> findAll(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Page<Post> posts = postRepository.findAll(pageable); // select * from post
        Page<PostResDto> postResDtos
                = posts.map(p -> new PostResDto(p.getId(), p.getTitle(), p.getEmail()==null? "익명유저" : email));
        return postResDtos;
    }
}
