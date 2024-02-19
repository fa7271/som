package com.encore.views;

import com.encore.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ViewsRepository extends JpaRepository<Views,Long> {
    @Query("SELECT v.post FROM Views v WHERE v.createdAt >= :since GROUP BY v.post ORDER BY COUNT(v.post) DESC")
    List<Post> findMostViewedPostsSince(@Param("since") LocalDateTime since);

}
