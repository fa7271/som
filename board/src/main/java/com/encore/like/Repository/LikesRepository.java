package com.encore.like.Repository;

import com.encore.like.domain.Likes;
import com.encore.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    Likes findByPostAndEmail(Post post, String email);
    Optional<List<Likes>> findByPostId(Long postId);

}

