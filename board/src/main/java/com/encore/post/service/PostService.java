package com.encore.post.service;

import com.encore.post.domain.Post;
import com.encore.post.dto.PostSaveReqDto;
import com.encore.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
//    public void save(PostSaveReqDto postSaveReqDto){
//        Post post = new Post(postSaveReqDto.getTitle(),
//                postSaveReqDto.getContents());
//        postRepository.save(post);
//    }

}
