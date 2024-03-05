package com.encore.postreport.dto;

import lombok.Data;

@Data
public class PostReportSaveRequest {
    private Long postId;
    private String reason;
}
