package com.encore.post.dto;

import com.encore.post.domain.Post;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostDetailResDto {
    private Long id;
    private String email;
    private String title;
    private String contents;
    private LocalDateTime createdAt;

    private int views;


    public static PostDetailResDto ToPostDto(Post post) {
        PostDetailResDtoBuilder builder = PostDetailResDto.builder();
        builder
                .id(post.getId())
                .title(post.getTitle())
                .views(post.getViews().size())
                .contents(post.getContents())
                .createdAt(post.getCreatedAt())
                .email(post.getEmail())
                .build();

        return builder.build();
    }
}