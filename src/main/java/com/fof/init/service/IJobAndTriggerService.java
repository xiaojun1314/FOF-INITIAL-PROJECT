package com.fof.init.service;

import com.fof.init.entity.JobAndTriggerEntity;

import java.util.List;
import java.util.Map;

/**
 * @className: IJobAndTriggerService
 * @author: jun
 * @date: 2021-04-08 13:54
 * @Depiction:
 **/
public interface IJobAndTriggerService {
    public List<JobAndTriggerEntity> getJobAndTriggerAll(Map<String, Object> map, String sorter);

    public Integer getJobAndTriggerCount(Map<String,Object> map);

    /**
     * @Title: updateJob
     * @Description: TODO(更新定时任务)
     * @param @param jobClassName 任务路径名称
     * @param @param jobGroupName 任务分组
     * @param @param cronExpression cron时间规则
     * @param @throws Exception    参数
     * @return void    返回类型
     * @throws
     */
    public boolean updateJob(String jobClassName, String jobGroupName, String cronExpression,String triggerState,String startTime,String endTime,String jobDescription) throws Exception;


    public boolean addJob(String jobName,String jobClassName, String jobGroupName, String cronExpression,String triggerState,String prevFireTime,String nextFireTime,String jobDescription)  throws Exception;

    /**
     * @Title: deleteJob
     * @Description: TODO(删除定时任务)
     * @param @param jobClassName 任务路径名称
     * @param @param jobGroupName 任务分组
     * @param @throws Exception    参数
     * @return void    返回类型
     * @throws
     */
    public boolean deleteJob(String jobName, String jobGroupName,String triggerName, String triggerGroupName) throws Exception;

    /**
     * @Title: pauseJob
     * @Description: TODO(暂停定时任务)
     * @param @param jobClassName 任务路径名称
     * @param @param jobGroupName 任务分组
     * @param @throws Exception    参数
     * @return void    返回类型
     * @throws
     */
    public void pauseJob(String jobClassName, String jobGroupName) throws Exception;

    /**
     * @Title: resumejob
     * @Description: TODO(恢复任务)
     * @param @param jobClassName 任务路径名称
     * @param @param jobGroupName 任务分组
     * @param @throws Exception    参数
     * @return void    返回类型
     * @throws
     */
    public void resumeJob(String jobClassName, String jobGroupName) throws Exception;

    public boolean checkJobClassName(JobAndTriggerEntity entity);

    public boolean checkJobName(JobAndTriggerEntity entity);

    public JobAndTriggerEntity findJobAndTriggerCountInfo();
}
