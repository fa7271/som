package com.encore.like.service;

import com.encore.like.Repository.LikesRepository;
import com.encore.like.domain.Likes;
import com.encore.post.domain.Post;
import com.encore.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikesService {
    private final LikesRepository likeRepository;
    private final PostRepository postRepository;

    public LikesService(LikesRepository likeRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
    }

    public List<String> like(Long id, String email) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("없는 게시글입니다."));
        Likes likeExist = likeRepository.findByPostAndEmail(post, email);
        if (likeExist != null) {
            likeRepository.delete(likeExist);
        } else {
            Likes likes = Likes.builder()
                    .email(email)
                    .post(post)
                    .build();
            likeRepository.save(likes);
        }
        Optional<List<Likes>> likesOptional = likeRepository.findByPostId(id);
        return likesOptional.orElse(Collections.emptyList())
                .stream()
                .map(Likes::getEmail)
                .collect(Collectors.toList());
    }
}
