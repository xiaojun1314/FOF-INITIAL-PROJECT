package com.fof.init.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fof.common.bean.JsonResult;
import com.fof.common.util.Constants;
import com.fof.common.util.ResultTool;
import com.fof.common.util.StringHelper;
import com.fof.init.entity.SysDictionaryTypeEntity;
import com.fof.init.entity.SysUserInfoEntity;
import com.fof.init.service.IUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/userManage")
public class UserManageController {
    @Autowired
	private IUserInfoService userInfoService;
	/**查询信息*/
	@RequestMapping(value="/queryUserList",method= RequestMethod.POST)
	public void queryUserList(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> searchParams) throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=UTF-8");
		int[] pageParams =initPage(StringHelper.null2String(searchParams.get("current")), StringHelper.null2String(searchParams.get("pageSize")));
		searchParams.put("limit", pageParams[1]);
		searchParams.put("offset", pageParams[0]);
		List<SysUserInfoEntity>  list =userInfoService.getAll(searchParams, searchParams.containsKey("sorter")?StringUtils.strip(searchParams.get("sorter").toString(),"{}"):"");
		json.put("data", list);
		int count =userInfoService.getCount(searchParams);
		json.put("total", count);
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}
	/**删除信息*/
	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/removeUserInfo",method=RequestMethod.POST)
	public void removeUserInfo(HttpServletResponse response,HttpServletRequest request,@RequestBody Map<String, Object> params) throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("text/html; charset=UTF-8");
		ArrayList<String> idList=(ArrayList<String>)params.get("ids");
		userInfoService.delete(idList);
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}

	/**添加信息*/
	@RequestMapping(value="/saveUserInfo",method=RequestMethod.POST)
	public void saveUserInfo(HttpServletResponse response,HttpServletRequest request,@RequestBody SysUserInfoEntity entity) throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("text/html; charset=UTF-8");
		userInfoService.insert(entity);
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}
	/**更新信息*/
	@RequestMapping(value="/editUserInfo",method= RequestMethod.POST)
	public void editUserInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysUserInfoEntity entity)throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("text/html; charset=UTF-8");
		userInfoService.update(entity);
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}
	/**检查用户名称*/
	@RequestMapping(value="/checkUserName",method= RequestMethod.POST)
	public void checkInfoCode(HttpServletResponse response, HttpServletRequest request, @RequestBody SysUserInfoEntity entity)throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=UTF-8");
		if(null==entity.getId()||(null!=entity.getId()&&!entity.getUserName().equals(entity.getOldUserName()))) {
			boolean checkResult=userInfoService.checkUserName(entity);
			json.put("checkResult", checkResult);
		}else{
			json.put("checkResult", true);
		}
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}
	/**检查用户编码*/
	@RequestMapping(value="/checkUserCode",method= RequestMethod.POST)
	public void checkUserCode(HttpServletResponse response, HttpServletRequest request, @RequestBody SysUserInfoEntity entity)throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=UTF-8");
		if(null==entity.getId()||(null!=entity.getId()&&null==entity.getOldUserCode())||(null!=entity.getId()&&!entity.getUserCode().equals(entity.getOldUserCode()))) {
			boolean checkResult=userInfoService.checkUserCode(entity);
			json.put("checkResult", checkResult);
		}else{
			json.put("checkResult", true);
		}
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}

	/**重置用户密码*/
	@RequestMapping(value="/resetUserInfo",method= RequestMethod.POST)
	public void resetUserInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysUserInfoEntity entity)throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=UTF-8");
		userInfoService.resetPassWord(entity);
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}

	@RequestMapping(value="/queryUserListByRole",method=RequestMethod.POST)
	public void queryUserListByRole(HttpServletResponse response,HttpServletRequest request,@RequestBody Map<String, Object> searchParams)throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=UTF-8");
		int[] pageParams =initPage(StringHelper.null2String(searchParams.get("current")), StringHelper.null2String(searchParams.get("pageSize")));
		searchParams.put("limit", pageParams[1]);
		searchParams.put("offset", pageParams[0]);
		searchParams.put("delete_flag", Constants.DELFLG_N);
		/**查询该角色里面已有的用户信息 flag==1*/
		searchParams.put("flag", 1);
		List<SysUserInfoEntity>  list =userInfoService.getAllByRoleId(searchParams, searchParams.containsKey("sorter")?StringUtils.strip(searchParams.get("sorter").toString(),"{}"):"");
		json.put("data", list);
		int count =userInfoService.getCountByRoleId(searchParams);
		json.put("total", count);
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}

	@RequestMapping(value="/queryNoUserListByRole",method=RequestMethod.POST)
	public void queryNoUserListByRole(HttpServletResponse response,HttpServletRequest request,@RequestBody Map<String, Object> searchParams)throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=UTF-8");
		int[] pageParams =initPage(StringHelper.null2String(searchParams.get("current")), StringHelper.null2String(searchParams.get("pageSize")));
		searchParams.put("limit", pageParams[1]);
		searchParams.put("offset", pageParams[0]);
		searchParams.put("delete_flag", Constants.DELFLG_N);
		searchParams.put("flag", 0);
		List<SysUserInfoEntity>  list =userInfoService.getAllByRoleId(searchParams, searchParams.containsKey("sorter")?StringUtils.strip(searchParams.get("sorter").toString(),"{}"):"");
		json.put("data", list);
		int count =userInfoService.getCountByRoleId(searchParams);
		json.put("total", count);
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}

	public  int[] initPage(String currentPage,String pageSize1) {
		int pageNumber = Integer.parseInt(StringUtils.defaultIfBlank(currentPage,"1"));
		int pageSize = Integer.parseInt(StringUtils.defaultIfBlank(pageSize1,"5"));
		return new int[] { pageNumber, pageSize };
	}
}
