package com.encore.commentreport;

import com.encore.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {
    List<CommentReport> findByCommentAndEmail(Comment comment,String email);

    int countByComment(Comment comment);
}
