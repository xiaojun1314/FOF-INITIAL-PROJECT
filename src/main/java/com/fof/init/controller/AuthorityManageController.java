package com.fof.init.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fof.common.bean.JsonResult;
import com.fof.common.util.ResultTool;
import com.fof.common.util.StringHelper;
import com.fof.init.entity.SysAuthorityEntity;
import com.fof.init.service.IAuthorityInfoService;
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
 * @className: AuthorityManageController
 * @author: jun
 * @date: 2021-04-08 11:02
 * @Depiction:
 **/

@Controller
@RequestMapping("/authorityManage")
public class AuthorityManageController {
    @Autowired
    private IAuthorityInfoService authorityInfoService;

    @RequestMapping(value="/queryAuthorityList",method= RequestMethod.POST)
    public void queryAuthorityList(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> searchParams) throws Exception{

        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        int[] pageParams =initPage(StringHelper.null2String(searchParams.get("current")), StringHelper.null2String(searchParams.get("pageSize")));
        searchParams.put("limit", pageParams[1]);
        searchParams.put("offset", pageParams[0]);
        List<SysAuthorityEntity>  list =authorityInfoService.getAll(searchParams, searchParams.containsKey("sorter")? StringUtils.strip(searchParams.get("sorter").toString(),"{}"):"");
        json.put("data", list);
        int count =authorityInfoService.getCount(searchParams);
        json.put("total", count);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));

    }

    @RequestMapping(value="/queryAuthorityCountInfo",method= RequestMethod.POST)
    public void queryAuthorityCountInfo(HttpServletResponse response, HttpServletRequest request)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> queryParams=new HashMap<String, Object>();
        SysAuthorityEntity sysAuthorityEntity=authorityInfoService.findAuthCount(queryParams);
        if(null!=sysAuthorityEntity) {
            json.put("authorityData",sysAuthorityEntity);
        }else {
            json.put("authorityData",new SysAuthorityEntity());
        }
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }


    public  int[] initPage(String currentPage,String pageSize1) {
        int pageNumber = Integer.parseInt(StringUtils.defaultIfBlank(currentPage,"1"));
        int pageSize = Integer.parseInt(StringUtils.defaultIfBlank(pageSize1,"5"));
        return new int[] { pageNumber, pageSize };
    }
}
