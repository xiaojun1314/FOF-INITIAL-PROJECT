package com.fof.init.entity;

/**
 * @className: JobAndTriggerEntity
 * @author: jun
 * @date: 2021-04-08 13:51
 * @Depiction:
 **/

import com.fof.common.util.Constants;
import com.fof.common.util.StringUtil;

/**
 *动态配置定时任务信息
 */
public class JobAndTriggerEntity implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务名称
     */
    private String oldJobName;
    /**
     * 任务描述
     */
    private String jobDescription;
    /**
     * 任务所在组
     */
    private String jobGroupName;
    /**
     * 任务类名
     */
    private String jobClassName;
    /**
     * 任务类名
     */
    private String oldJobClassName;
    /**
     * 触发器名称
     */
    private String triggerName;
    /**
     * 触发器所在组
     */
    private String triggerGroupName;
    /**
     * 任务开启时间
     */
    private String prevFireTime;
    /**
     * 任务结束时间
     */
    private String nextFireTime;
    /**
     * 定时任务表达式
     */
    private String cronExpression;
    /**
     * 触发器状态
     */
    private String triggerState;
    /**
     * 触发器状态
     */
    private String triggerStateName;
    /**
     * 执行数量
     */
    private String acquiredCount;
    /**
     * 暂停数量
     */
    private String pausedCount;
    /**
     * 等待数量
     */
    private String waitingCount;
    /**
     * 错误数量
     */
    private String errorCount;
    /**
     * 阻塞数量
     */
    private String blockedCount;
    /**
     * 开启时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;

    public String getTriggerStateName() {
        if (StringUtil.isNotBlank(triggerStateName)) {
            return triggerStateName;
        } else if (StringUtil.isBlank(this.triggerState)) {
            return "";
        } else {
            return Constants.TriggerStateName.valueOf(this.triggerState).getValue();
        }
    }

    public JobAndTriggerEntity() {
        super();
        this.acquiredCount = "0";
        this.pausedCount = "0";
        this.waitingCount = "0";
        this.errorCount = "0";
        this.blockedCount = "0";

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getOldJobName() {
        return oldJobName;
    }

    public void setOldJobName(String oldJobName) {
        this.oldJobName = oldJobName;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public String getOldJobClassName() {
        return oldJobClassName;
    }

    public void setOldJobClassName(String oldJobClassName) {
        this.oldJobClassName = oldJobClassName;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroupName() {
        return triggerGroupName;
    }

    public void setTriggerGroupName(String triggerGroupName) {
        this.triggerGroupName = triggerGroupName;
    }

    public String getPrevFireTime() {
        return prevFireTime;
    }

    public void setPrevFireTime(String prevFireTime) {
        this.prevFireTime = prevFireTime;
    }

    public String getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(String nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getTriggerState() {
        return triggerState;
    }

    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState;
    }

    public void setTriggerStateName(String triggerStateName) {
        this.triggerStateName = triggerStateName;
    }

    public String getAcquiredCount() {
        return acquiredCount;
    }

    public void setAcquiredCount(String acquiredCount) {
        this.acquiredCount = acquiredCount;
    }

    public String getPausedCount() {
        return pausedCount;
    }

    public void setPausedCount(String pausedCount) {
        this.pausedCount = pausedCount;
    }

    public String getWaitingCount() {
        return waitingCount;
    }

    public void setWaitingCount(String waitingCount) {
        this.waitingCount = waitingCount;
    }

    public String getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(String errorCount) {
        this.errorCount = errorCount;
    }

    public String getBlockedCount() {
        return blockedCount;
    }

    public void setBlockedCount(String blockedCount) {
        this.blockedCount = blockedCount;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}