package com.fof.init.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fof.common.bean.SecurityUserInfo;
import com.fof.common.util.CommonUtil;
import com.fof.common.util.Constants;
import com.fof.common.util.StringHelper;
import com.fof.init.entity.SysUserInfoEntity;
import com.fof.init.service.IUserInfoService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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

	@RequestMapping(value="/removeUserInfo",method=RequestMethod.POST)
	public String removeUserInfo(HttpServletResponse response,HttpServletRequest request,@RequestBody Map<String, Object> params)throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("text/html; charset=UTF-8");
		try {
			SecurityUserInfo securityUserInfo = (SecurityUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ArrayList<String> idsList=(ArrayList<String>)params.get("ids");
			if(idsList.size()>0) {
				String[] ids = idsList.stream().toArray(String[]::new);
				int flag= userInfoService.delete(securityUserInfo.getId(),ids);
				if(flag>0) {
					json.put("success", true);
					json.put("Message", "删除成功");
				}else {
					json.put("success", false);
					json.put("Message", "删除失败");
				}
			}else {
				json.put("success", false);
				json.put("Message", "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("success", false);
			json.put("Message", "删除失败");
		}
		try {
			response.getWriter().write(json.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
			json.put("success", false);
			json.put("Message", "删除失败");
		}
		return null;
	}

	/**添加用户信息*/
	@RequestMapping(value="/saveUserInfo",method=RequestMethod.POST)
	public String saveUserInfo(HttpServletResponse response,HttpServletRequest request,@RequestBody SysUserInfoEntity entity){
		JSONObject json = new JSONObject();
		response.setContentType("text/html; charset=UTF-8");
		try {
			SecurityUserInfo securityUserInfo = (SecurityUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


			SysUserInfoEntity sysUserInfoEntity=new SysUserInfoEntity();

			String randomSalt = UUID.randomUUID().toString().replace("-", "").toUpperCase();
			sysUserInfoEntity.setSalt(randomSalt);
			// String password= new Md5Hash(securityUserInfo.getPassword(),entity.getCredentialsSalt(),3).toString();
			// sysUserInfoEntity.setPassWord(password);
			sysUserInfoEntity.setCreater(sysUserInfoEntity.getId());
			int flag=userInfoService.insert(entity);
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
			json.put("IsSuccess", false);
			json.put("Message", "保存失败");
		}
		return null;
	}

	public  int[] initPage(String currentPage,String pageSize1) {
		int pageNumber = Integer.parseInt(StringUtils.defaultIfBlank(currentPage,"1"));
		int pageSize = Integer.parseInt(StringUtils.defaultIfBlank(pageSize1,"5"));
		return new int[] { pageNumber, pageSize };
	}
}
