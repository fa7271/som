package com.encore.postreport;

import com.encore.common.support.DefaultResponse;
import com.encore.post.domain.Post;
import com.encore.postreport.dto.PostReportSaveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board/post/report")
public class PostReportController {
    private final PostReportService postReportService;

    @Autowired
    public PostReportController(PostReportService postReportService) {
        this.postReportService = postReportService;
    }

    @PostMapping("/{id}")
    public DefaultResponse<Long> postReportSave(@PathVariable Long id){
        PostReport postReport = postReportService.save(id);
        return new DefaultResponse<>(postReport.getId());
    }

}
