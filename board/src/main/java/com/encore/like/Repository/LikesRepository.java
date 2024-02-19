package com.encore.like.Repository;

import com.encore.like.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    Likes findByIdAndEmail(long id, String email);
}
