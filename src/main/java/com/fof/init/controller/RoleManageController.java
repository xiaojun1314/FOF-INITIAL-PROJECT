package com.fof.init.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fof.common.bean.SecurityUserInfo;
import com.fof.common.util.CommonUtil;
import com.fof.common.util.Constants;
import com.fof.common.util.StringHelper;
import com.fof.component.redis.util.CodeTableUtil;
import com.fof.init.entity.SysRoleInfoEntity;
import com.fof.init.entity.SysUserInfoEntity;
import com.fof.init.service.IRoleInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/roleManage")
public class RoleManageController {
    @Autowired
	private IRoleInfoService roleInfoService;

    
    // @Autowired
	// private IMenuAndAuthorityService menuAndAuthorityService;
    
    // @Autowired
	// private IPageElementAndAuthorityService pageElementAndAuthorityService ;
    
    // @Autowired
	// private IRoleAndUserService roleAndUserService;

	@Resource
	private CodeTableUtil codeTableUtil;

    @RequestMapping(value="/queryRoleList",method= RequestMethod.POST)
  	public void queryRoleList(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> searchParams)throws Exception{
    	JSONObject json = new JSONObject();
    	response.setContentType("application/json;charset=UTF-8");
    	try {
			int[] pageParams =initPage(StringHelper.null2String(searchParams.get("current")), StringHelper.null2String(searchParams.get("pageSize")));
			searchParams.put("limit", pageParams[1]);
			searchParams.put("offset", pageParams[0]);
			searchParams.put("delete_flag", Constants.DELFLG_N);
    		List<SysRoleInfoEntity>  data =roleInfoService.getAll(searchParams, StringUtils.strip(searchParams.get("sorter").toString(),"{}"));
            initCodeText(data);
    		int count =roleInfoService.getCount(searchParams);
        	json.put("data", data);
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
    
    @RequestMapping(value="/checkRoleCode",method= RequestMethod.POST)
  	public void checkRoleCode(HttpServletResponse response, HttpServletRequest request, @RequestBody SysRoleInfoEntity entity)throws Exception{
    	JSONObject json = new JSONObject();
    	response.setContentType("application/json;charset=UTF-8");  	
    	try {
    		if(null==entity.getId()||(null!=entity.getId()&&!entity.getCode().equals(entity.getOldCode()))) {
    			boolean checkResult=roleInfoService.checkCode(entity);
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
    
    
    /**保存角色信息*/
    @RequestMapping(value="/saveRoleInfo",method= RequestMethod.POST)
  	public void saveRoleInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysRoleInfoEntity entity){
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
    	try {
            SecurityUserInfo securityUserInfo = (SecurityUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		entity.setCreater(securityUserInfo.getId());
      	    int flag=roleInfoService.insert(entity);
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
  	}
    
    @RequestMapping(value="/editRoleInfo",method= RequestMethod.POST)
   	public void editRoleInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysRoleInfoEntity entity)throws Exception{
     	JSONObject json = new JSONObject();
    	response.setContentType("application/json;charset=UTF-8");
    	try {
    		SecurityUserInfo securityUserInfo = (SecurityUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	   entity.setUpdater(securityUserInfo.getId());
    	   int flag=roleInfoService.update(entity);
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
    
    /**删除数据*/
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/removeRoleInfo",method= RequestMethod.POST)
  	public void removeRoleInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> params)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        try {
			SecurityUserInfo securityUserInfo = (SecurityUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		ArrayList<String> idList=(ArrayList<String>)params.get("ids");
        	if(idList.size()>0) {
        		int flag= roleInfoService.delete(securityUserInfo.getId(),idList);
                if(flag>0) {
                    json.put("IsSuccess", true);
                    json.put("Message", "删除成功");
                }else {
                	json.put("IsSuccess", false);
                    json.put("Message", "删除失败");
                }  		
        	}else {
        		json.put("IsSuccess", false);
                json.put("Message", "删除失败");
        	}
        } catch (Exception e) {
        	e.printStackTrace();
            json.put("IsSuccess", false);
            json.put("Message", "删除失败");
        }
        try {
            response.getWriter().write(json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
  	}

    /**保存角色菜单 权限信息*/

    @SuppressWarnings("unchecked")
	@RequestMapping(value="/saveRoleAndMenuAndAuthInfo",method= RequestMethod.POST)
  	public void saveRoleAndMenuAndAuthInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
    	try {
    		/**删除该角色与菜单权限*/
    		if(params.containsKey("menuIds")&&params.containsKey("roleId")) {
    			ArrayList<String> menuIdsList=(ArrayList<String>)params.get("menuIds");
            	String roleId=params.get("roleId").toString();
            	// menuAndAuthorityService.insertMenuToAuthorityByRole(menuIdsList, roleId);
                json.put("IsSuccess", true);
                json.put("Message", "保存成功");
    		}else {
         	  	json.put("IsSuccess", false);
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
  	}

    @SuppressWarnings("unchecked")
	@RequestMapping(value="/saveRoleAndPageElementAndAuthInfo",method= RequestMethod.POST)
  	public String saveRoleAndPageElementAndAuthInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
    	try {
    		/**删除该角色与菜单权限*/
    		if(params.containsKey("pageElementIds")&&params.containsKey("roleId")) {
        		ArrayList<String> pageElementIdsList=(ArrayList<String>)params.get("pageElementIds");
        		String[] pageElementIds = pageElementIdsList.stream().toArray(String[]::new);
        		String roleId =params.get("roleId").toString();
        		//pageElementAndAuthorityService.insertPageElementToAuthorityByRole(pageElementIdsList,roleId);
         	  	json.put("IsSuccess", true);
                json.put("Message", "保存成功");
    		}else {
                json.put("IsSuccess", false);
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
    		json.put("IsSuccess", false);
            json.put("Message", "查询失败");
        }
        return null;
  	}


    /**删除数据*/
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/removeRoleAndUser",method= RequestMethod.POST)
  	public void removeRoleAndUser(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> params)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        try {
        	if(params.containsKey("ids")&&params.containsKey("roleId")) {
        		ArrayList<String> userIdsList=(ArrayList<String>)params.get("ids");
        		String roleId =params.get("roleId").toString();
            	if(userIdsList.size()>0) {
            		//String[] user_ids = idsList.stream().toArray(String[]::new);
            		//roleAndUserService.removeRoleAndUser(roleId, userIdsList);
                    json.put("IsSuccess", true);
                    json.put("Message", "删除成功");
            	}else {
            	    json.put("IsSuccess", false);
                    json.put("Message", "删除失败");
            	}
        	}else {
        	    json.put("IsSuccess", false);
                json.put("Message", "删除失败");
        	}
        } catch (Exception e) {
        	e.printStackTrace();
            json.put("IsSuccess", false);
            json.put("Message", "删除失败");
        }
        try {
            response.getWriter().write(json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
  	}

    /**添加用户与角色对应关系信息*/
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/saveRoleToUser",method= RequestMethod.POST)
  	public String saveRoleToUser(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> params){
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
    	try {
    		if(params.containsKey("userIds")&&params.containsKey("role_id")) {
    			ArrayList<String> userIdsList=(ArrayList<String>)params.get("userIds");
    			if(userIdsList.size()>0) {
    				String[] user_ids = userIdsList.stream().toArray(String[]::new);
    				String role_id =params.get("role_id").toString();
    				//roleAndUserService.addRoleAndUser(role_id,userIdsList);
     	    	   json.put("IsSuccess", true);
    	           json.put("Message", "保存成功");
    			}else {
    	    	   json.put("IsSuccess", false);
    	           json.put("Message", "保存失败");
    			}
    		}else {
     	  	   json.put("IsSuccess", false);
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
        int pageSize = Integer.parseInt(StringUtils.defaultIfBlank(pageSize1,"15"));
        return new int[] { pageNumber, pageSize };
    }
    
    private void initCodeText(List<SysRoleInfoEntity> views) {
        if (CollectionUtils.isEmpty(views)) {
            return;
        }
        for (SysRoleInfoEntity entity : views) {
            if (!StringUtils.isEmpty(entity.getState())){
                entity.setStateText(codeTableUtil.getCodesByGroupKey("SSI_CODE_TABLE", "ROLESTATE",entity.getState()).display);
            }
        }
   }
    
}
