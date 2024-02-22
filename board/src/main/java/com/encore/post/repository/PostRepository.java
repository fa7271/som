package com.encore.post.repository;

import com.encore.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Specification<Post> spec, Pageable pageable);

    Page<Post> findAllByOrderByCreatedAtDesc(Specification<Post> spec, Pageable pageable);

    List<Post> findByEmailAndCreatedAtBetween(String email, LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<Post> findFirst10ByViewsCreatedAtAfterOrderByViewsCreatedAtDesc(LocalDateTime since);

}
