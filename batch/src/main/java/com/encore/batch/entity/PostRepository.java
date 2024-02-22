package com.encore.batch.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    Long countByCreatedAtBetweenAndEmail(LocalDateTime startDate, LocalDateTime endDate, String email);

}
