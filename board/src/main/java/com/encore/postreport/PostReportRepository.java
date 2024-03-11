package com.encore.postreport;

import com.encore.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostReportRepository extends JpaRepository<PostReport, Long> {

    List<PostReport> findByPostAndEmail(Post post,String email);

    int countByPost(Post post);
}
