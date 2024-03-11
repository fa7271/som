package com.encore.batch.job;

import com.encore.batch.entity.CommentRepository;
import com.encore.batch.entity.Member;
import com.encore.batch.entity.MemberRepository;
import com.encore.batch.entity.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class TaskletRankingConfiguration {



    private final JobLauncher jobLauncher;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    @Bean
    public Job monthRankingJob() throws Exception {


        String uniqueJobName = "scheduleRankingJob-" + System.currentTimeMillis();

        log.info("Creating job with name: {}", uniqueJobName);

        return jobBuilderFactory.get(uniqueJobName)
//                .preventRestart()
                .start(simpleStep())
                .preventRestart()
                .build();
    }

    @Bean
    public Step simpleStep() {
        return this.stepBuilderFactory
                .get("simple-step")
                .tasklet(simpleTasklet())
                .build();
    }

    @Bean
    public Tasklet simpleTasklet() {
        return (stepContribution, chunkContext) -> {

            try {
                log.info("Executing tasklet...");
                List<Member> members = memberRepository.findAll();

                for (Member member : members) {

                    Long postCount = postRepository.countByCreatedAtBetweenAndEmail(
                            LocalDateTime.now().minusMonths(1),
                            LocalDateTime.now(),
                            member.getEmail());

                    Long commentCount = commentRepository.countByCreatedAtBetweenAndEmail(
                            LocalDateTime.now().minusMonths(1),
                            LocalDateTime.now(),
                            member.getEmail());

                    member.updatePoint(postCount*3L + commentCount * 10L);
                }

                Collections.sort(members, Comparator.comparing(Member::getPoint).reversed());

                for (int i = 0; i < members.size(); i++) {
                    members.get(i).updateRanking(i+1L);
                }

                memberRepository.saveAll(members);

                log.info("Tasklet execution completed.");
            } catch (Exception e) {
                log.error("Error in tasklet execution", e);
                throw e; // Ensure that exceptions are propagated
            }

            return RepeatStatus.FINISHED;
        };
    }
}

