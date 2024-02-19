package com.encore.views;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ViewsDto {

    private LocalDateTime createdAt;
    private Long postId;
    private String title;
    private int views;
    private int rank;


    @Builder
    public ViewsDto(LocalDateTime createdAt, Long postId, String title, int views, int rank) {
        this.createdAt = createdAt;
        this.postId = postId;
        this.title = title;
        this.views = views;
        this.rank = rank;
    }
}
