package com.fof.init.service.impl;

import com.fof.common.util.CommonUtil;
import com.fof.init.dao.JobAndTriggerDao;
import com.fof.init.entity.JobAndTriggerEntity;
import com.fof.init.service.IJobAndTriggerService;
import com.fof.scheduler.job.BaseSchedulerJob;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @className: JobAndTriggerServiceImpl
 * @author: jun
 * @date: 2021-04-08 13:54
 * @Depiction:
 **/
@Service
public class JobAndTriggerServiceImpl implements IJobAndTriggerService {
    @Autowired
    private JobAndTriggerDao jobAndTriggerDao;


    public List<JobAndTriggerEntity> getJobAndTriggerAll(Map<String, Object> map, String sorter) {
        String[] sorterParams = initSorter(sorter);
        map.put("sortType", sorterParams[0]);
        map.put("sortField", sorterParams[1]);
        return jobAndTriggerDao.getJobAndTriggerAll(map);
    }

    public Integer getJobAndTriggerCount(Map<String, Object> map) {
        return jobAndTriggerDao.getJobAndTriggerCount(map);
    }

    public boolean addJob(String jobName, String jobClassName, String jobGroupName, String cronExpression, String triggerState, String startTime, String endTime, String jobDescription) throws Exception {
        // 通过SchedulerFactory获取一个调度器实例
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();
        // 启动调度器
        scheduler.start();
        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobName, jobGroupName).withDescription(jobDescription).build();
        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName).withDescription(jobDescription).startAt(sdf.parse(startTime)).endAt(sdf.parse(endTime))
                    .withSchedule(scheduleBuilder).build();

            if (scheduler.isStarted()) {
                scheduler.scheduleJob(jobDetail, trigger);
            }
            if ("PAUSED".equals(triggerState)) {
                scheduler.pauseJob(JobKey.jobKey(jobName, jobGroupName));
            }
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    //更新定时任务
    public boolean updateJob(String jobName, String jobGroupName, String cronExpression, String triggerState, String startTime, String endTime, String jobDescription) throws Exception {
        try {
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler scheduler = sf.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
            // 表达式调度构建器（动态修改后不立即执行）
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withDescription(jobDescription).startAt(sdf.parse(startTime)).endAt(sdf.parse(endTime)).withSchedule(scheduleBuilder).build();
            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
            if ("PAUSED".equals(triggerState)) {
                scheduler.pauseJob(JobKey.jobKey(jobName, jobGroupName));
            }
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    //删除定时任务
    public boolean deleteJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) throws Exception {
        try {
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler scheduler = sf.getScheduler();
            //暂停触发器
            scheduler.pauseTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));
            //移除触发器
            scheduler.unscheduleJob(TriggerKey.triggerKey(triggerName, triggerGroupName));
            //删除定时任务
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void pauseJob(String jobClassName, String jobGroupName) throws Exception {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();
        scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
    }


    public void resumeJob(String jobClassName, String jobGroupName) throws Exception {
        SchedulerFactory sf = new StdSchedulerFactory();

        Scheduler scheduler = sf.getScheduler();
        scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    public static BaseSchedulerJob getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (BaseSchedulerJob) class1.newInstance();
    }


    public boolean checkJobClassName(JobAndTriggerEntity entity) {
        if (jobAndTriggerDao.checkJobClassName(entity) > 0) {
            return false;
        } else {
            return true;
        }
    }

    public JobAndTriggerEntity findJobAndTriggerCountInfo() {

        return jobAndTriggerDao.findJobAndTriggerCountInfo();
    }

    @Override
    public boolean checkJobName(JobAndTriggerEntity entity) {
        if (jobAndTriggerDao.checkJobName(entity) > 0) {
            return false;
        } else {
            return true;
        }
    }

    public  String[] initSorter(String sorter) {
        String sorterRule = StringUtils.defaultIfBlank(sorter.equals("")?"":(sorter.split("=")[1].equals("descend")?"DESC":"ASC"),"DESC");
        String sorterField = StringUtils.defaultIfBlank(sorter.equals("")?"":sorter.split("=")[0],"jobName");
        return new String[] {sorterRule,sorterField};
    }

}