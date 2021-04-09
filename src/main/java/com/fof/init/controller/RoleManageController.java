package com.fof.init.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fof.common.bean.JsonResult;
import com.fof.common.util.Constants;
import com.fof.common.util.ResultTool;
import com.fof.common.util.StringHelper;
import com.fof.component.redis.util.CodeTableUtil;
import com.fof.init.entity.SysRoleInfoEntity;
import com.fof.init.service.IRoleAndAuthorityService;
import com.fof.init.service.IRoleAndUserService;
import com.fof.init.service.IRoleInfoService;
import com.fof.init.service.ISubCompanyInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/roleManage")
public class RoleManageController {
    @Autowired
	private IRoleInfoService roleInfoService;

    
    @Autowired
	private IRoleAndAuthorityService roleAndAuthorityService;
    
    //@Autowired
	//private IPageElementAndAuthorityService pageElementAndAuthorityService ;
    
    @Autowired
    private IRoleAndUserService roleAndUserService;

    @Autowired
    private ISubCompanyInfoService subCompanyInfoService;

	@Resource
	private CodeTableUtil codeTableUtil;

    @RequestMapping(value="/queryRoleList",method= RequestMethod.POST)
  	public void queryRoleList(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> searchParams) throws Exception{
    	JSONObject json = new JSONObject();
    	response.setContentType("application/json;charset=UTF-8");
        String type= searchParams.get("type").toString();
        String selectKey= searchParams.get("selectKey").toString();
        if(type.equals("0")){
            // 公司外键查询
            Map<String, Object> searchParams1 =new HashMap<String, Object>();
            searchParams1.put("foreignId",selectKey);
            String ids=subCompanyInfoService.getIdsByForeignId(searchParams1);
            // 分部外键查询
            searchParams.put("foreignIds",ids.equals("")?"-1":ids);
        }
        if(type.equals("1")){
            searchParams.put("foreignIds",selectKey);
        }
        int[] pageParams =initPage(StringHelper.null2String(searchParams.get("current")), StringHelper.null2String(searchParams.get("pageSize")));
        searchParams.put("limit", pageParams[1]);
        searchParams.put("offset", pageParams[0]);
        List<SysRoleInfoEntity>  list =roleInfoService.getAll(searchParams, searchParams.containsKey("sorter")?StringUtils.strip(searchParams.get("sorter").toString(),"{}"):"");
        initCodeText(list);
        int count =roleInfoService.getCount(searchParams);
        json.put("data", list);
        json.put("total", count);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
  	}
    
    @RequestMapping(value="/checkRoleCode",method= RequestMethod.POST)
  	public void checkRoleCode(HttpServletResponse response, HttpServletRequest request, @RequestBody SysRoleInfoEntity entity) throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        if(null==entity.getId()||(null!=entity.getId()&&!entity.getCode().equals(entity.getOldCode()))) {
            boolean checkResult=roleInfoService.checkCode(entity);
            json.put("checkResult", checkResult);
        }else{
            json.put("checkResult", true);
        }
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
  	}
    
    
    /**保存角色信息*/
    @RequestMapping(value="/saveRoleInfo",method= RequestMethod.POST)
  	public void saveRoleInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysRoleInfoEntity entity) throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        roleInfoService.insert(entity);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
  	}
    
    @RequestMapping(value="/editRoleInfo",method= RequestMethod.POST)
   	public void editRoleInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysRoleInfoEntity entity)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        roleInfoService.update(entity);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
   	}
    
    /**删除数据*/
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/removeRoleInfo",method= RequestMethod.POST)
  	public void removeRoleInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> params)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        ArrayList<String> idList=(ArrayList<String>)params.get("ids");
        roleInfoService.delete(idList);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
  	}

    /**保存角色菜单 权限信息*/

    @SuppressWarnings("unchecked")
	@RequestMapping(value="/saveRoleAndMenuAndAuthInfo",method= RequestMethod.POST)
  	public void saveRoleAndMenuAndAuthInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        ArrayList<String> menuIdsList=(ArrayList<String>)params.get("menuIds");
        String role_id=params.get("role_id").toString();
        roleAndAuthorityService.insertMenuToAuthorityByRole(menuIdsList, role_id);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
  	}

    @SuppressWarnings("unchecked")
	@RequestMapping(value="/saveRoleAndMoudleElementAndAuthInfo",method= RequestMethod.POST)
  	public void saveRoleAndMoudleElementAndAuthInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        ArrayList<String> moduleElementIdList=(ArrayList<String>)params.get("moduleElementIds");
        String role_id =params.get("role_id").toString();
        roleAndAuthorityService.insertModuleElementToAuthorityByRole(moduleElementIdList,role_id);
        JsonResult result = ResultTool.success();
        response.getWriter().write(JSON.toJSONString(result));
  	}


    /**删除数据*/
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/removeRoleAndUser",method= RequestMethod.POST)
  	public void removeRoleAndUser(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> params)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        ArrayList<String> userIdsList=(ArrayList<String>)params.get("userIds");
        String roleId =params.get("roleId").toString();
        roleAndUserService.removeRoleAndUser(roleId, userIdsList);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));

  	}

    /**添加用户与角色对应关系信息*/
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/saveRoleToUser",method= RequestMethod.POST)
  	public void saveRoleToUser(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        ArrayList<String> userIdsList=(ArrayList<String>)params.get("userIds");
        String roleId =params.get("roleId").toString();
        roleAndUserService.addRoleAndUser(roleId,userIdsList);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
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
