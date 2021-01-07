package com.fof.init.controller;

import com.alibaba.fastjson.JSONObject;
import com.fof.common.bean.SecurityUserInfo;
import com.fof.common.util.Constants;
import com.fof.common.util.StringHelper;
import com.fof.init.entity.SysUserInfoEntity;
import com.fof.init.service.IUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @RequestMapping(value="/queryUserList",method= RequestMethod.POST)
  	public void queryUserList(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> searchParams)throws Exception{
    	JSONObject json = new JSONObject();
    	response.setContentType("application/json;charset=UTF-8");
    	try {
    		int[] pageParams =initPage(StringHelper.null2String(searchParams.get("current")), StringHelper.null2String(searchParams.get("pageSize")));
    		searchParams.put("limit", pageParams[1]);
    		searchParams.put("offset", pageParams[0]);
    		searchParams.put("deleteFlag", Constants.DELFLG_N);
    		List<SysUserInfoEntity>  list =userInfoService.getAll(searchParams, StringUtils.strip(searchParams.get("sorter").toString(),"{}"));
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
			entity.setPassWord(new BCryptPasswordEncoder().encode(entity.getPassWord()));
			entity.setCreater(securityUserInfo.getId());
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


	@RequestMapping(value="/editUserInfo",method= RequestMethod.POST)
	public void editUserInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysUserInfoEntity entity)throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=UTF-8");
		try {
			SecurityUserInfo securityUserInfo = (SecurityUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			entity.setUpdater(securityUserInfo.getId());
			int flag=userInfoService.update(entity);
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
	}


	@RequestMapping(value="/checkUserName",method= RequestMethod.POST)
	public void checkInfoCode(HttpServletResponse response, HttpServletRequest request, @RequestBody SysUserInfoEntity entity)throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=UTF-8");
		try {
			if(null==entity.getId()||(null!=entity.getId()&&!entity.getUserName().equals(entity.getOldUserName()))) {
				boolean checkResult=userInfoService.checkUserName(entity);
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
	}

	@RequestMapping(value="/checkUserCode",method= RequestMethod.POST)
	public void checkUserCode(HttpServletResponse response, HttpServletRequest request, @RequestBody SysUserInfoEntity entity)throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=UTF-8");
		try {
			if(null==entity.getId()||(null!=entity.getId()&&!entity.getUserCode().equals(entity.getOldUserCode()))) {
				boolean checkResult=userInfoService.checkUserCode(entity);
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
	}

	/**重置用户密码*/
	@RequestMapping(value="/resetUserInfo",method= RequestMethod.POST)
	public void resetUserInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysUserInfoEntity entity)throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=UTF-8");
		try {
			SecurityUserInfo securityUserInfo = (SecurityUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			entity.setUpdater(securityUserInfo.getId());
			entity.setPassWord(new BCryptPasswordEncoder().encode(entity.getPassWord()));
			int flag=userInfoService.resetPassWord(entity);
			if(flag==1) {
				json.put("IsSuccess", true);
				json.put("Message", "重置成功");
			}else {
				json.put("IsSuccess", true);
				json.put("Message", "重置失败");
			}
		}catch (Exception e) {
			e.printStackTrace();
			json.put("IsSuccess", false);
			json.put("Message", "重置失败");
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
