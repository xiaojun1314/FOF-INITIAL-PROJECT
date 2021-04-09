package com.fof.scheduler.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class HelloJob implements BaseSchedulerJob {
    public void execute(JobExecutionContext context) throws JobExecutionException {  
        System.err.println("Hello Job执行时间: " + new Date());
    }  
}  
