package com.fof.init.dao;

import com.fof.init.entity.JobAndTriggerEntity;

import java.util.List;
import java.util.Map;

/**
 * @className: JobAndTriggerDao
 * @author: jun
 * @date: 2021-04-08 13:51
 * @Depiction:
 **/
public interface JobAndTriggerDao {
    List<JobAndTriggerEntity> getJobAndTriggerAll(Map<String,Object> map);

    Integer getJobAndTriggerCount(Map<String,Object> map);

    Integer checkJobClassName(JobAndTriggerEntity entity);

    Integer checkJobName(JobAndTriggerEntity entity);

    public JobAndTriggerEntity findJobAndTriggerCountInfo();
}
