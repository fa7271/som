package com.encore.comment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDetailResDto {
    private Long id;
    private String contents;
    private Long post_id;
    private String member_email;
    private LocalDateTime createdAt;
}
