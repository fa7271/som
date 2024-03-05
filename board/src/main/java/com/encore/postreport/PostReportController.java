package com.encore.postreport;

import com.encore.common.support.DefaultResponse;
import com.encore.post.domain.Post;
import com.encore.postreport.dto.PostReportSaveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board/post/report")
public class PostReportController {
    private final PostReportService postReportService;

    @Autowired
    public PostReportController(PostReportService postReportService) {
        this.postReportService = postReportService;
    }

    @PostMapping("/save") // Post create
    public DefaultResponse<Long> postReportSave(@RequestBody PostReportSaveRequest request){
        PostReport postReport = postReportService.save(request);
        return new DefaultResponse<>(postReport.getId());
    }

}
