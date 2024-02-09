package com.encore.post.repository;

import com.encore.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByEmailAndCreatedAtBetween(String email, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
