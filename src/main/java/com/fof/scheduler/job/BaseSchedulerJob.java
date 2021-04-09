package com.fof.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @className: BaseSchedulerJob
 * @author: jun
 * @date: 2021-04-08 13:56
 * @Depiction:
 **/
public interface BaseSchedulerJob extends Job {
    public void execute(JobExecutionContext context) throws JobExecutionException;
}
