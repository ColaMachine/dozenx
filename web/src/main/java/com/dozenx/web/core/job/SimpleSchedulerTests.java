package com.dozenx.web.core.job;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by dozen.zhang on 2016/12/2.
 */
public class SimpleSchedulerTests {

    private static Logger logger = LoggerFactory.getLogger(SimpleSchedulerTests.class);

    private Scheduler createScheduler() throws SchedulerException {//创建调度器
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        return schedulerFactory.getScheduler();
    }

    public void scheduleZZWJob(Scheduler scheduler)throws SchedulerException{

        JobDetail job = newJob(MyJob.class)
                .withIdentity("job1", "group1")
                .build();

        // Trigger the job to run now, and then repeat every 40 seconds
        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(1)
                        .repeatForever())
                .build();

        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(job, trigger);
    }
    @Test
    public void ScanDirectoryTests() {
        SimpleSchedulerTests simpleSchedulerTests = new SimpleSchedulerTests();
        try {
            Scheduler scheduler = simpleSchedulerTests.createScheduler();

            // Start the scheduler running
            scheduler.start();

            logger.info("Scheduler started at " + new java.util.Date());
            simpleSchedulerTests.scheduleZZWJob(scheduler);

            // Stop the scheduler after 10 second
            Thread.sleep(10000);
            scheduler.shutdown();
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
