package com.encore.comment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentReqDto {
    private String contents;
    private LocalDateTime createdAt;
}
