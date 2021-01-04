package com.fof.init.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fof.common.util.CommonUtil;
import com.fof.common.util.Constants;
import com.fof.common.util.StringHelper;
import com.fof.init.entity.SysUserInfoEntity;
import com.fof.init.service.IUserInfoService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
@RequestMapping("/userManage")
public class UserManageController {
    @Autowired
	private IUserInfoService userInfoService;

    
    @RequestMapping(value="/queryUserList",method= RequestMethod.POST)
  	public void queryUserList(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> params)throws Exception{
    	JSONObject json = new JSONObject();
    	response.setContentType("application/json;charset=UTF-8");
    	try {
			Map<String, Object> searchParams= CommonUtil.getSearchParameters("search_", params);
    		int[] pageParams =initPage(StringHelper.null2String(params.get("current")), StringHelper.null2String(params.get("pageSize")));
    		searchParams.put("limit", pageParams[1]);
    		searchParams.put("offset", pageParams[0]);
    		searchParams.put("deleteFlag", Constants.DELFLG_N);
    		System.out.println("sorter"+StringUtils.strip(params.get("sorter").toString(),"{}"));
    		List<SysUserInfoEntity>  list =userInfoService.getAll(searchParams, StringUtils.strip(params.get("sorter").toString(),"{}"));
    		json.put("data", list);
    		int count =userInfoService.getCount(searchParams);
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
        }
  	}

	public  int[] initPage(String currentPage,String pageSize1) {
		int pageNumber = Integer.parseInt(StringUtils.defaultIfBlank(currentPage,"1"));
		int pageSize = Integer.parseInt(StringUtils.defaultIfBlank(pageSize1,"5"));
		return new int[] { pageNumber, pageSize };
	}
}
