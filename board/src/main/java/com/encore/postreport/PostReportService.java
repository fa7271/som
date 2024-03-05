package com.encore.postreport;

import com.encore.common.support.ResponseCode;
import com.encore.common.support.SomException;
import com.encore.post.domain.Post;
import com.encore.post.repository.PostRepository;
import com.encore.postreport.dto.PostReportSaveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostReportService {

    private final PostReportRepository repository;

    private final PostRepository postRepository;
    @Autowired
    public PostReportService(PostReportRepository repository, PostRepository postRepository) {
        this.repository = repository;
        this.postRepository = postRepository;
    }

    public PostReport save(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Post post = postRepository.findById(id).orElseThrow(() ->new SomException(ResponseCode.POST_NOT_FOUND));

        //같은 유저가 저장했을 경우 저장 안되도록
        List<PostReport> postReports = repository.findByEmail(email);
        if(!postReports.isEmpty()) {
            throw new SomException(ResponseCode.EXISTING_RESOURCE);
        }
        //이미 4개가 있을 경우 post 안 보이게 처리
        int postReportCount = repository.countByPost(post);
        if(postReportCount > 4) {
            post.deletePost();
        }

        PostReport postReport = PostReport.builder()
                .post(post)
                .build();

        return repository.save(postReport);
    }
}
