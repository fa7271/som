package com.encore.post.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDetailResDto {
    private Long id;
    private String email;
    private String title;
    private String contents;
    private LocalDateTime createdAt;

    private int views;
}
