package com.encore.commentreport;


import com.encore.commentreport.dto.CommentReportSaveRequest;
import com.encore.common.support.DefaultResponse;
import com.encore.common.support.ResponseCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board/comment/report")
public class CommentReportController {
    private final CommentReportService service;

    public CommentReportController(CommentReportService service) {
        this.service = service;
    }

    @PostMapping("save")
    public DefaultResponse<Long> save(@RequestBody CommentReportSaveRequest request) {
        CommentReport report = service.save(request);

        return new DefaultResponse<>(report.getId());
    }


}
