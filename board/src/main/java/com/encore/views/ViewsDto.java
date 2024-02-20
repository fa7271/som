package com.encore.views;

import com.encore.post.domain.Post;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ViewsDto {

    private LocalDateTime createdAt;
    private Long postId;
    private String title;
    private int views;
    private int rank;


    public static ViewsDto ToViewsDto(Post post, int rank) {
        ViewsDtoBuilder builder = ViewsDto.builder();
        builder
                .postId(post.getId())
                .title(post.getTitle())
                .views(post.getViews().size())
                .createdAt(post.getCreatedAt())
                .rank(rank)
                .build();
        return builder.build();
    }
}
