package com.encore.post.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PostReqDto {
    @NotNull
    private String title;
    @NotBlank
    @Size(min = 1, max = 1000)
    private String contents;
}
