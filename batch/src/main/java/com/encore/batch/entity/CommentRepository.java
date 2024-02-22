package com.encore.batch.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    Long countByCreatedAtBetweenAndEmail(LocalDateTime localDateTime, LocalDateTime now, String email);
}
