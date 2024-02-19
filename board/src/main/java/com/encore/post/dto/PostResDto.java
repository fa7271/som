package com.encore.post.dto;

import com.encore.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResDto {
    private Long id;
    private String title;
    private String contents;
    private String member_email;

    public static PostResDto ToPostRestDto(Post post) {

        PostResDtoBuilder builder = PostResDto.builder();

        builder.contents(post.getContents())
                .id(post.getId())
                .title(post.getTitle())
                .member_email(post.getEmail())
                .build();
        return builder.build();
    }
}
