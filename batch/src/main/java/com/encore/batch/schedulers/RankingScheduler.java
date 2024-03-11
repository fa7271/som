package com.encore.batch.schedulers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling // 스케쥴러 기능 활성화
@Component
public class RankingScheduler {

    @Qualifier("monthRankingJob")
    private final JobLauncher jobLauncher;
    private final Job job;

    public RankingScheduler(JobLauncher jobLauncher, @Qualifier("monthRankingJob") Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @Scheduled(fixedDelay = 15000)
    public void startJob() {
        try {
            Map<String, JobParameter> jobParametersMap = new HashMap<>();

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = new Date();

            String time1 = format1.format(time);

            jobParametersMap.put("requestDate", new JobParameter(time1));

            JobParameters parameters = new JobParameters(jobParametersMap);

            JobExecution jobExecution = jobLauncher.run(job, parameters);

        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }
}

