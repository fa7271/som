package com.encore.like.service;

import com.encore.like.Repository.LikesRepository;
import com.encore.like.domain.Likes;
import com.encore.post.domain.Post;
import com.encore.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class LikesService {
    private final LikesRepository likeRepository;
    private final PostRepository postRepository;

    public LikesService(LikesRepository likeRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
    }

    public void like(Long id, String email) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("없는 게시글 입니다."));
        Likes likeExist = likeRepository.findByIdAndEmail(id, email);
        if (likeExist != null) {
            likeRepository.delete(likeExist);
        }else{
            Likes likes = Likes.builder()
                    .email(email)
                    .post(post)
                    .build();

            likeRepository.save(likes);
        }
    }
}
