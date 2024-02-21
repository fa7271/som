package com.encore.views;

import com.encore.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface ViewsRepository extends JpaRepository<Views, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM (" +
            "SELECT v.post_id " +
            "FROM Views v " +
            "WHERE v.created_at > :since " +
            "ORDER BY v.views_count " +
            "DESC LIMIT 10) AS sub_query")
    List<Long> findTop10PostIdsByCreatedAtAfterOrderByViewsCountDesc(@Param("since") LocalDateTime since);


    @Query(value = "SELECT v.post_id, SUM(v.views_count) AS weekly_views " +
            "FROM Views v " +
            "WHERE v.created_at > DATE_SUB(CURRENT_DATE(), INTERVAL :number DAY) " +
            "GROUP BY v.post_id " +
            "ORDER BY weekly_views DESC " +
            "LIMIT 10", nativeQuery = true)
    List<Object[]> findTop10PostIdsOrderByWeeklyViewsDesc(@Param("number") int number);
}

