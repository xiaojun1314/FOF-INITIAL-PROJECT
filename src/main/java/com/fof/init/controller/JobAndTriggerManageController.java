package com.fof.init.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fof.common.bean.JsonResult;
import com.fof.common.util.ResultTool;
import com.fof.common.util.StringHelper;
import com.fof.init.entity.JobAndTriggerEntity;
import com.fof.init.service.IJobAndTriggerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: JobAndTriggerManageController
 * @author: jun
 * @date: 2021-04-08 13:57
 * @Depiction:
 **/
@Controller
@RequestMapping("/jobAndTriggerManage")
public class JobAndTriggerManageController {

    @Autowired
    private IJobAndTriggerService jobAndTriggerService;

    @RequestMapping(value = "/queryJobAndTriggerList", method = RequestMethod.POST)
    public void queryJobAndTriggerList(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> searchParams) throws Exception {
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        int[] pageParams =initPage(StringHelper.null2String(searchParams.get("current")), StringHelper.null2String(searchParams.get("pageSize")));
        searchParams.put("limit", pageParams[1]);
        searchParams.put("offset", pageParams[0]);
        List<JobAndTriggerEntity>  list =jobAndTriggerService.getJobAndTriggerAll(searchParams, searchParams.containsKey("sorter")?StringUtils.strip(searchParams.get("sorter").toString(),"{}"):"");
        json.put("data", list);
        int count =jobAndTriggerService.getJobAndTriggerCount(searchParams);
        json.put("total", count);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    @RequestMapping(value = "/queryJobAndTriggerCountInfo", method = RequestMethod.POST)
    public void queryJobAndTriggerCountInfo(HttpServletResponse response, HttpServletRequest request) throws Exception {
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> searchParams=new HashMap<String, Object>();
        JobAndTriggerEntity jobAndTriggerEntity = jobAndTriggerService.findJobAndTriggerCountInfo();
        if(null!=jobAndTriggerEntity) {
            json.put("jobAndTriggerData",jobAndTriggerEntity);
        }else {
            json.put("jobAndTriggerData",new JobAndTriggerEntity());
        }
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    @RequestMapping(value = "/saveJobAndTriggerInfo", method = RequestMethod.POST)
    public void saveJobAndTriggerInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody JobAndTriggerEntity entity)  throws Exception {
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        jobAndTriggerService.addJob(entity.getJobName(), entity.getJobClassName(), "jobGroup", entity.getCronExpression(), entity.getTriggerState(), entity.getStartTime(), entity.getEndTime(), entity.getJobDescription());
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    @RequestMapping(value = "/editJobAndTriggerInfo", method = RequestMethod.POST)
    public void editJobAndTriggerInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody JobAndTriggerEntity entity) throws Exception {
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        jobAndTriggerService.updateJob(entity.getJobName(), "jobGroup", entity.getCronExpression(), entity.getTriggerState(), entity.getStartTime(), entity.getEndTime(), entity.getJobDescription());
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    @RequestMapping(value = "/removeJobAndTriggerInfo", method = RequestMethod.POST)
    public void removeJobAndTriggerInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {

        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("jobNames", params.get("jobNames").toString());
        List<JobAndTriggerEntity> list = jobAndTriggerService.getJobAndTriggerAll(searchParams, "");
        for (JobAndTriggerEntity jobAndTriggerEntity : list) {
            jobAndTriggerService.deleteJob(jobAndTriggerEntity.getJobClassName(), jobAndTriggerEntity.getJobGroupName(), jobAndTriggerEntity.getTriggerName(), jobAndTriggerEntity.getTriggerGroupName());
        }
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    @RequestMapping(value = "/checkJobClassName", method = RequestMethod.POST)
    public void checkJobClassName(HttpServletResponse response, HttpServletRequest request, @RequestBody JobAndTriggerEntity entity) throws Exception {

        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        if (null == entity.getOldJobClassName() || (null != entity.getOldJobClassName() && !entity.getOldJobClassName().equals(entity.getJobClassName()))){
            boolean checkResult = jobAndTriggerService.checkJobClassName(entity);
            json.put("checkResult", checkResult);
        }else{
            json.put("checkResult", true);
        }
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    @RequestMapping(value = "/checkJobName", method = RequestMethod.POST)
    public void checkJobName(HttpServletResponse response, HttpServletRequest request, @RequestBody JobAndTriggerEntity entity) throws Exception {
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        if (null == entity.getOldJobName() || (null != entity.getOldJobName() && !entity.getOldJobName().equals(entity.getJobName()))) {
            boolean checkResult = jobAndTriggerService.checkJobName(entity);
            json.put("checkResult", checkResult);
        }else{
            json.put("checkResult", true);
        }
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    public int[] initPage(String currentPage, String pageSize1) {
        int pageNumber = Integer.parseInt(StringUtils.defaultIfBlank(currentPage, "1"));
        int pageSize = Integer.parseInt(StringUtils.defaultIfBlank(pageSize1, "15"));
        return new int[]{pageNumber, pageSize};
    }

}