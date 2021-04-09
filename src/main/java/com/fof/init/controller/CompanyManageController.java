package com.fof.init.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fof.common.bean.JsonResult;
import com.fof.common.util.ResultTool;
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
    public void saveCompanyInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysCompanyEntity entity) throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        companyInfoService.insert(entity);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    @RequestMapping(value="/editCompanyInfo",method=RequestMethod.POST)
    public void editCompanyInfo(HttpServletResponse response,HttpServletRequest request,@RequestBody SysCompanyEntity entity)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        companyInfoService.update(entity);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    @RequestMapping(value="/checkCompanyCode",method=RequestMethod.POST)
    public void checkCompanyCode(HttpServletResponse response,HttpServletRequest request,@RequestBody SysCompanyEntity entity)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        if(null==entity.getId()||(null!=entity.getId()&&!entity.getCode().equals(entity.getOldCode()))) {
            boolean checkResult=companyInfoService.checkCode(entity);
            json.put("checkResult", checkResult);
        }else{
            json.put("checkResult", true);
        }
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }
}
