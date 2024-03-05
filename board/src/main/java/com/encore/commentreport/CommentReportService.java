package com.encore.commentreport;

import com.encore.comment.domain.Comment;
import com.encore.comment.repository.CommentRepository;
import com.encore.commentreport.dto.CommentReportSaveRequest;
import com.encore.common.support.ResponseCode;
import com.encore.common.support.SomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentReportService {

    private final CommentReportRepository repository;

    private final CommentRepository commentRepository;

    @Autowired
    public CommentReportService(CommentReportRepository repository, CommentRepository commentRepository) {
        this.repository = repository;
        this.commentRepository = commentRepository;
    }

    public CommentReport save(CommentReportSaveRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Comment comment = commentRepository.findById(request.getCommentId()).orElseThrow(() -> new SomException(ResponseCode.COMMENT_NOT_FOUND));

        List<CommentReport> commentReports = repository.findByEmail(email);
        if(!commentReports.isEmpty()) {
            throw new SomException(ResponseCode.EXISTING_RESOURCE);
        }

        //이미 4개가 있을 경우 post 안 보이게 처리
        int commentReportCount = repository.countByComment(comment);
        if(commentReportCount > 4) {
            comment.deleteComment();
        }

        CommentReport commentReport = CommentReport.builder()
                .comment(comment)
                .reason(request.getReason())
                .build();

        return repository.save(commentReport);

    }
}
