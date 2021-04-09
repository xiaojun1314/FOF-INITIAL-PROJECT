package com.fof.init.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fof.common.bean.JsonResult;
import com.fof.common.entity.TreeDataModel;
import com.fof.common.util.Constants;
import com.fof.common.util.ResultTool;
import com.fof.component.redis.util.CodeTableUtil;
import com.fof.init.entity.SysMenuInfoEntity;
import com.fof.init.service.IMenuInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *菜单管理
 */
@Controller
@RequestMapping("/menuManage")
public class MenuManageController {
    @Autowired
	private IMenuInfoService menuInfoService;
	@Resource
	private CodeTableUtil codeTableUtil;

	/**查询菜单信息*/
	@RequestMapping(value="/queryMenuTreeInfo",method= RequestMethod.POST)
  	public void queryMenuTreeInfo(HttpServletResponse response, HttpServletRequest request) throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=UTF-8");
		Map<String,Object> searchParams=new HashMap<String,Object>();
		List<TreeDataModel>  treeDataModelList=menuInfoService.getAll(searchParams);
		json.put("unitTreeData",treeDataModelList);
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
  	}

	@RequestMapping(value="/queryMenuInfoById",method= RequestMethod.POST)
	public void queryMenuInfoById(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> params)throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=UTF-8");
		SysMenuInfoEntity entity =menuInfoService.findById(params.get("id").toString());
		if(entity.getParent_id().equals("0")) {
			entity.setParentName("ROOT");
		}else {
			SysMenuInfoEntity parentEntity =menuInfoService.findById(entity.getParent_id());
			entity.setParentName(parentEntity.getName());
		}
		initCodeText(entity);
		json.put("entity",entity);
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}

	@RequestMapping(value="/editMenuInfo",method= RequestMethod.POST)
	public void editMenuInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysMenuInfoEntity entity) throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("text/html; charset=UTF-8");
		menuInfoService.update(entity);
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}

	@RequestMapping(value="/saveMenuInfo",method= RequestMethod.POST)
	public void saveMenuInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysMenuInfoEntity entity) throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("text/html; charset=UTF-8");
		menuInfoService.insert(entity);
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}

	@RequestMapping(value="/removeMenuInfo",method= RequestMethod.POST)
	public void removeMenuInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String,Object> params) throws Exception {
		JSONObject json = new JSONObject();
		response.setContentType("text/html; charset=UTF-8");
		String id=params.get("id").toString();
		menuInfoService.deleteById(id);
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}


	@RequestMapping(value="/getMenuIdsByRoleId",method= RequestMethod.POST)
	public void getMenuIdsByRoleId(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {
		JSONObject json = new JSONObject();
		response.setContentType("text/html; charset=UTF-8");
		String roleId=params.get("roleId").toString();
		List<SysMenuInfoEntity> menuList=menuInfoService.getMenuToAuthorityByRoleId(roleId);
		Object[] menuIds=menuList.stream().map( item ->item.getId()).collect(Collectors.toList()).toArray();
		json.put("menuIds",menuIds);
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}

	
    private void initCodeText(SysMenuInfoEntity entity) {
        if (!StringUtils.isEmpty(entity.getIs_leaf())){
            entity.setIsLeafText(codeTableUtil.getCodesByGroupKey("SSI_CODE_TABLE", "NODETYPE",
            		entity.getIs_leaf()).display);
        }
        if (!StringUtils.isEmpty(entity.getIs_hide())){
            entity.setIsHideText(codeTableUtil.getCodesByGroupKey("SSI_CODE_TABLE", "NODEISHIDE",
            		entity.getIs_hide()).display);
        }
            
    }
    
}
