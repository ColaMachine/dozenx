package com.dozenx.web.core.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * Created by dozen.zhang on 2016/12/2.
 */
public class MyJob implements org.quartz.Job {

    public MyJob() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.err.println("Hello World!  MyJob is executing.");
    }
}
