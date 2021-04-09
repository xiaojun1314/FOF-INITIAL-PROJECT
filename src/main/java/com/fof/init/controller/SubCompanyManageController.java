package com.fof.init.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fof.common.bean.JsonResult;
import com.fof.common.bean.SecurityUserInfo;
import com.fof.common.util.Constants;
import com.fof.common.util.ResultTool;
import com.fof.common.util.StringHelper;
import com.fof.init.entity.SysDepartmentEntity;
import com.fof.init.entity.SysRoleInfoEntity;
import com.fof.init.entity.SysSubCompanyEntity;
import com.fof.init.entity.SysUserInfoEntity;
import com.fof.init.service.IDepartmentInfoService;
import com.fof.init.service.IRoleInfoService;
import com.fof.init.service.ISubCompanyInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
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

    @Autowired
    private IRoleInfoService roleInfoService;

    @Autowired
    private IDepartmentInfoService departmentInfoService;


    @RequestMapping(value="/querySubCompanyList",method=RequestMethod.POST)
    public void querySubCompanyList(HttpServletResponse response,HttpServletRequest request,@RequestBody Map<String, Object> searchParams)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        int[] pageParams =initPage(StringHelper.null2String(searchParams.get("current")), StringHelper.null2String(searchParams.get("pageSize")));
        searchParams.put("limit", pageParams[1]);
        searchParams.put("offset", pageParams[0]);
        List<SysSubCompanyEntity>  list =subCompanyInfoService.getAll(searchParams, searchParams.containsKey("sorter")?StringUtils.strip(searchParams.get("sorter").toString(),"{}"):"");
        initCodeText(list);
        json.put("data", list);
        int count =subCompanyInfoService.getCount(searchParams);
        json.put("total", count);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    /**添加分部公司信息*/
    @RequestMapping(value="/saveSubCompanyInfo",method= RequestMethod.POST)
    public void saveSubCompanyInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysSubCompanyEntity entity) throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        subCompanyInfoService.insert(entity);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    @RequestMapping(value="/editSubCompanyInfo",method=RequestMethod.POST)
    public void editSubCompanyInfo(HttpServletResponse response,HttpServletRequest request,@RequestBody SysSubCompanyEntity entity)throws Exception{

        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        subCompanyInfoService.update(entity);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    @RequestMapping(value="/removeSubCompanyInfo",method=RequestMethod.POST)
    public void removeSubCompanyInfo(HttpServletResponse response,HttpServletRequest request,@RequestBody Map<String, Object> params)throws Exception{

        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        ArrayList<String> idList=(ArrayList<String>)params.get("ids");
        subCompanyInfoService.delete(idList);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    @RequestMapping(value="/checkSubCompanyCode",method=RequestMethod.POST)
    public void checkSubCompanyCode(HttpServletResponse response,HttpServletRequest request,@RequestBody SysSubCompanyEntity entity)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        if(null==entity.getId()||(null!=entity.getId()&&!entity.getCode().equals(entity.getOldCode()))) {
            boolean checkResult=subCompanyInfoService.checkCode(entity);
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

    private void initCodeText(List<SysSubCompanyEntity> views) {
        if (CollectionUtils.isEmpty(views)) {
            return;
        }
        for (SysSubCompanyEntity entity : views) {
            Map<String, Object> searchParams=new HashMap<String, Object>();
            /**分部Id*/
            searchParams.put("foreignId",entity.getId());
            int roleCount=roleInfoService.getCount(searchParams);
            entity.setRoleCount(roleCount);
            List<SysDepartmentEntity> departmentList =departmentInfoService.findByForeignId(searchParams);
            entity.setDepartmentCount(departmentList.size());
        }
    }

}

