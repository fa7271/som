package com.encore.util;

import com.encore.post.domain.Post;
import com.encore.post.repository.PostRepository;
import com.encore.views.Views;
import com.encore.views.ViewsRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Transactional
@Component
public class PostScheduler {
    private final PostRepository postRepository;
    private final ViewsRepository viewsRepository;
    private final StringRedisTemplate stringRedisTemplate;

    public PostScheduler(PostRepository postRepository, StringRedisTemplate stringRedisTemplate, ViewsRepository viewsRepository) {
        this.postRepository = postRepository;
        this.viewsRepository = viewsRepository;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void Datainit() {
        Set<String> AllViewEmail = stringRedisTemplate.keys("*@*.*");
        Map<Long, Long> postIdAllUserIds = new HashMap<>();
//        key = email, value = ViewdPostByEmail
        for (String email : AllViewEmail) {
            String[] PostIdViewdByEmail= stringRedisTemplate.opsForValue().get(email).split("_");
            for (String PostIdstr : PostIdViewdByEmail) {
                Long postId = Long.parseLong(PostIdstr);
                postIdAllUserIds.compute(postId, (key, value) -> (value == null) ? 1 : value + 1);
            }
        }
        // save db
        for (Map.Entry<Long, Long> entry : postIdAllUserIds.entrySet()) {
            Long postId = entry.getKey();
            Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

            Long viewsCount = entry.getValue();
            Views init = Views.Init(post, viewsCount);
            viewsRepository.save(init);
        }
//         delete userEmail in redis
        stringRedisTemplate.delete(AllViewEmail);
    }
}


