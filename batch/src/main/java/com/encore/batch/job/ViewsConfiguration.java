package com.encore.batch.job;

import com.encore.batch.entity.Post;
import com.encore.batch.entity.PostRepository;
import com.encore.batch.entity.Views;
import com.encore.batch.entity.ViewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ViewsConfiguration {
    private final JobLauncher jobLauncher;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final PostRepository postRepository;
    private final ViewsRepository viewsRepository;
    private final StringRedisTemplate stringRedisTemplate;

    @Bean(name = "ViewsJob")
    @Qualifier("ViewsJob")
    public Job helloJob2() throws Exception{
        String uniqueJobName = "scheduleViewsJob-" + System.currentTimeMillis();
        return jobBuilderFactory.get(uniqueJobName)
                .start(simpleStep())
                .build();
    }
    public Step simpleStep() {
        return this.stepBuilderFactory
                .get("simple-step1")
                .tasklet(simpleTasklet())
                .build();
    }

    public Tasklet simpleTasklet() {
        return(stepContribution, chunkContext) ->{
            try{
                System.out.println("redis_scheduler");
                Set<String> AllViewEmail = stringRedisTemplate.keys("*@*.*");
                System.out.println("AllViewEmail = " + AllViewEmail);
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
                stringRedisTemplate.delete(AllViewEmail);
            }catch (Exception e) {
                log.error("Error in tasklet execution", e);
                throw e; // Ensure that exceptions are propagated
            }

            return RepeatStatus.FINISHED;
        };
    }
}
