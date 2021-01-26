package com.fof.init.controller;

import com.alibaba.fastjson.JSONObject;
import com.fof.init.entity.SysCompanyEntity;
import com.fof.init.service.ICompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @className: CompanyManageController
 * @author: jun
 * @date: 2021-01-26 09:04
 * @Depiction: 公司信息维护
 **/

@Controller
@RequestMapping("/companyManage")
public class CompanyManageController {
    @Autowired
    private ICompanyInfoService companyInfoService;

    @RequestMapping(value="/saveCompanyInfo",method= RequestMethod.POST)
    public String saveCompanyInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysCompanyEntity entity){
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        try {
            int flag=companyInfoService.insert(entity);
            if(flag==1) {
                json.put("IsSuccess", true);
                json.put("Message", "保存成功");
            }else {
                json.put("IsSuccess", true);
                json.put("Message", "保存失败");
            }
        }catch (Exception e) {
            e.printStackTrace();
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

    @RequestMapping(value="/editCompanyInfo",method=RequestMethod.POST)
    public String editCompanyInfo(HttpServletResponse response,HttpServletRequest request,@RequestBody SysCompanyEntity entity)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        try {
            int flag=companyInfoService.update(entity);
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

    @RequestMapping(value="/checkCompanyCode",method=RequestMethod.POST)
    public String checkCompanyCode(HttpServletResponse response,HttpServletRequest request,@RequestBody SysCompanyEntity entity)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        try {
            if(null==entity.getId()||(null!=entity.getId()&&!entity.getCode().equals(entity.getOldCode()))) {
                boolean checkResult=companyInfoService.checkCode(entity);
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
}
