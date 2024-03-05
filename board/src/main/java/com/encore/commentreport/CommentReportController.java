package com.encore.commentreport;


import com.encore.commentreport.dto.CommentReportSaveRequest;
import com.encore.common.support.DefaultResponse;
import com.encore.common.support.ResponseCode;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board/comment/report")
public class CommentReportController {
    private final CommentReportService service;

    public CommentReportController(CommentReportService service) {
        this.service = service;
    }

    @PostMapping("/{id}")
    public DefaultResponse<Long> save(@PathVariable Long id) {
        CommentReport report = service.save(id);

        return new DefaultResponse<>(report.getId());
    }


}
