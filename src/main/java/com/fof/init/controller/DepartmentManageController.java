package com.fof.init.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fof.common.bean.JsonResult;
import com.fof.common.bean.SecurityUserInfo;
import com.fof.common.util.Constants;
import com.fof.common.util.ResultTool;
import com.fof.common.util.StringHelper;
import com.fof.init.entity.SysDepartmentEntity;
import com.fof.init.entity.SysSubCompanyEntity;
import com.fof.init.service.IDepartmentInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @className: DepartmentManageController
 * @author: jun
 * @date: 2021-01-26 09:10
 * @Depiction: 部门信息
 **/
@Controller
@RequestMapping("/departmentManage")
public class DepartmentManageController {

    @Autowired
    private IDepartmentInfoService departmentInfoService;

    @RequestMapping(value="/queryDepartPartList",method=RequestMethod.POST)
    public void queryDepartPartList(HttpServletResponse response,HttpServletRequest request,@RequestBody Map<String, Object> searchParams)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        int[] pageParams =initPage(StringHelper.null2String(searchParams.get("current")), StringHelper.null2String(searchParams.get("pageSize")));
        searchParams.put("limit", pageParams[1]);
        searchParams.put("offset", pageParams[0]);
        List<SysDepartmentEntity>  list =departmentInfoService.getAllDepartPart(searchParams, searchParams.containsKey("sorter")?StringUtils.strip(searchParams.get("sorter").toString(),"{}"):"");
        json.put("data", list);
        int count =departmentInfoService.getCountDepartPart(searchParams);
        json.put("total", count);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    /**添加分部公司信息*/
    @RequestMapping(value="/saveDepartmentInfo",method= RequestMethod.POST)
    public void saveDepartmentInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysDepartmentEntity entity) throws Exception {
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        departmentInfoService.insert(entity);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    @RequestMapping(value="/editDepartmentInfo",method=RequestMethod.POST)
    public void editDepartmentInfo(HttpServletResponse response,HttpServletRequest request,@RequestBody SysDepartmentEntity entity)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        departmentInfoService.update(entity);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    @RequestMapping(value="/removeDepartmentInfo",method=RequestMethod.POST)
    public void removeDepartmentInfo(HttpServletResponse response,HttpServletRequest request,@RequestBody Map<String, Object> params)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        ArrayList<String> idList=(ArrayList<String>)params.get("ids");
        departmentInfoService.delete(idList);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    @RequestMapping(value="/checkDepartmentCode",method=RequestMethod.POST)
    public void checkDepartmentCode(HttpServletResponse response,HttpServletRequest request,@RequestBody SysDepartmentEntity entity)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        if(null==entity.getId()||(null!=entity.getId()&&!entity.getCode().equals(entity.getOldCode()))) {
            boolean checkResult=departmentInfoService.checkCode(entity);
            json.put("checkResult", checkResult);
        }else{
            json.put("checkResult", true);
        }
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    public  int[] initPage(String currentPage,String pageSize1) {
        int pageNumber = Integer.parseInt(StringUtils.defaultIfBlank(currentPage,"1"));
        int pageSize = Integer.parseInt(StringUtils.defaultIfBlank(pageSize1,"10"));
        return new int[] { pageNumber, pageSize };
    }
}
