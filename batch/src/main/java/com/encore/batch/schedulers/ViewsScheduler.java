package com.encore.batch.schedulers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableScheduling
@AllArgsConstructor
@Component
public class ViewsScheduler {

//    private final Job job;

    private final JobLauncher jobLauncher;

    private final ApplicationContext context;

    @Scheduled(cron = "0 0 0 * * *")
    public void startJob() {
        try {
            Map<String, JobParameter> jobParametersMap = new HashMap<>();

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = new Date();

            String time1 = format1.format(time);

            jobParametersMap.put("requestDate", new JobParameter(time1));

            JobParameters parameters = new JobParameters(jobParametersMap);

            Job job = context.getBean("helloJob", Job.class);
            JobExecution jobExecution = jobLauncher.run(job, parameters);


        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }
}
