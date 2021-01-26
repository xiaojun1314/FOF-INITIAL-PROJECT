package com.fof.init.controller;

import com.alibaba.fastjson.JSONObject;
import com.fof.common.util.Constants;
import com.fof.common.util.StringHelper;
import com.fof.init.entity.SysSubCompanyEntity;
import com.fof.init.service.ISubCompanyInfoService;
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
 * @className: SubCompanyManageController
 * @author: jun
 * @date: 2021-01-26 09:08
 * @Depiction:分部信息
 **/
@Controller
@RequestMapping("/subCompanyManage")
public class SubCompanyManageController {
    @Autowired
    private ISubCompanyInfoService subCompanyInfoService;

    @RequestMapping(value="/querySubCompanyList",method=RequestMethod.POST)
    public String querySubCompanyList(HttpServletResponse response,HttpServletRequest request,@RequestBody Map<String, Object> searchParams)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        try {
            int[] pageParams =initPage(StringHelper.null2String(searchParams.get("current")), StringHelper.null2String(searchParams.get("pageSize")));
            searchParams.put("limit", pageParams[1]);
            searchParams.put("offset", pageParams[0]);
            searchParams.put("delete_flag", Constants.DELFLG_N);
            List<SysSubCompanyEntity> list =subCompanyInfoService.getAll(searchParams, StringUtils.strip(searchParams.get("sorter").toString(),"{}"));
            json.put("data", list);
            int count =subCompanyInfoService.getCount(searchParams);
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
    @RequestMapping(value="/saveSubCompanyInfo",method= RequestMethod.POST)
    public String saveSubCompanyInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysSubCompanyEntity entity){
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        try {
            int flag=subCompanyInfoService.insert(entity);
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

    @RequestMapping(value="/editSubCompanyInfo",method=RequestMethod.POST)
    public String editSubCompanyInfo(HttpServletResponse response,HttpServletRequest request,@RequestBody SysSubCompanyEntity entity)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        try {

            int flag=subCompanyInfoService.update(entity);
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

    @RequestMapping(value="/checkSubCompanyCode",method=RequestMethod.POST)
    public String checkSubCompanyCode(HttpServletResponse response,HttpServletRequest request,@RequestBody SysSubCompanyEntity entity)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        try {
            if(null==entity.getId()||(null!=entity.getId()&&!entity.getCode().equals(entity.getOldCode()))) {
                boolean checkResult=subCompanyInfoService.checkCode(entity);
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
        }
        return null;
    }

    public  int[] initPage(String currentPage,String pageSize1) {
        int pageNumber = Integer.parseInt(StringUtils.defaultIfBlank(currentPage,"1"));
        int pageSize = Integer.parseInt(StringUtils.defaultIfBlank(pageSize1,"10"));
        return new int[] { pageNumber, pageSize };
    }
}

