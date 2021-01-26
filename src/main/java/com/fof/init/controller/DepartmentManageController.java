package com.fof.init.controller;

import com.alibaba.fastjson.JSONObject;
import com.fof.common.util.Constants;
import com.fof.common.util.StringHelper;
import com.fof.init.entity.SysDepartmentEntity;
import com.fof.init.service.IDepartmentInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public String queryDepartPartList(HttpServletResponse response,HttpServletRequest request,@RequestBody Map<String, Object> searchParams)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        try {
            int[] pageParams =initPage(StringHelper.null2String(searchParams.get("current")), StringHelper.null2String(searchParams.get("pageSize")));
            searchParams.put("limit", pageParams[1]);
            searchParams.put("offset", pageParams[0]);
            searchParams.put("delete_flag", Constants.DELFLG_N);
            List<SysDepartmentEntity> list =departmentInfoService.getAllDepartPart(searchParams, StringUtils.strip(searchParams.get("sorter").toString(),"{}"));
            json.put("data", list);
            int count =departmentInfoService.getCountDepartPart(searchParams);
            json.put("IsSuccess", true);
            json.put("total", count);
            json.put("Message", "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("IsSuccess", false);
            json.put("Message", "查询失败");
        }
        try {
            response.getWriter().write(json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
            json.put("IsSuccess", false);
            json.put("Message", "查询失败");
        }
        return null;
    }

    /**添加分部公司信息*/
    @RequestMapping(value="/saveDepartmentInfo",method= RequestMethod.POST)
    public String saveDepartmentInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysDepartmentEntity entity){
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        try {
            int flag=departmentInfoService.insert(entity);
            if(flag==1) {
                json.put("IsSuccess", true);
                json.put("Message", "保存成功");
            }else {
                json.put("IsSuccess", true);
                json.put("Message", "保存失败");
            }
        }catch (Exception e) {
            json.put("IsSuccess", false);
            json.put("Message", "保存失败");
        }
        try {
            response.getWriter().write(json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/editDepartmentInfo",method=RequestMethod.POST)
    public String editDepartmentInfo(HttpServletResponse response,HttpServletRequest request,@RequestBody SysDepartmentEntity entity)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        try {
            int flag=departmentInfoService.update(entity);
            if(flag==1) {
                json.put("IsSuccess", true);
                json.put("Message", "更新成功");
            }else {
                json.put("IsSuccess", true);
                json.put("Message", "更新失败");
            }
        }catch (Exception e) {
            e.printStackTrace();
            json.put("IsSuccess", false);
            json.put("Message", "更新失败");
        }
        try {
            response.getWriter().write(json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/checkDepartmentCode",method=RequestMethod.POST)
    public String checkDepartmentCode(HttpServletResponse response,HttpServletRequest request,@RequestBody SysDepartmentEntity entity)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        try {
            if(null==entity.getId()||(null!=entity.getId()&&!entity.getCode().equals(entity.getOldCode()))) {
                boolean checkResult=departmentInfoService.checkCode(entity);
                if(checkResult) {
                    json.put("IsSuccess",true);
                    json.put("Message", "检查通过");
                }else {
                    json.put("IsSuccess",false);
                    json.put("Message", "检查不通过");
                }
            }else {
                json.put("IsSuccess",true);
                json.put("Message", "检查通过");
            }
        }catch (Exception e) {
            e.printStackTrace();
            json.put("IsSuccess", false);
            json.put("Message", "检查不通过");
        }
        try {
            response.getWriter().write(json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
            json.put("IsSuccess", false);
            json.put("Message", "检查不通过");
        }
        return null;
    }

    public  int[] initPage(String currentPage,String pageSize1) {
        int pageNumber = Integer.parseInt(StringUtils.defaultIfBlank(currentPage,"1"));
        int pageSize = Integer.parseInt(StringUtils.defaultIfBlank(pageSize1,"10"));
        return new int[] { pageNumber, pageSize };
    }
}
