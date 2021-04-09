package com.fof.init.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fof.common.bean.JsonResult;
import com.fof.common.entity.TreeDataModel;
import com.fof.common.util.Constants;
import com.fof.common.util.ResultTool;
import com.fof.component.redis.util.Code;
import com.fof.component.redis.util.CodeTableUtil;
import com.fof.init.entity.SysMenuInfoEntity;
import com.fof.init.entity.SysModuleInfoEntity;
import com.fof.init.service.IMenuInfoService;
import com.fof.init.service.IModuleInfoService;
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
@RequestMapping("/moduleManage")
public class ModuleManageController {
    @Autowired
	private IModuleInfoService moduleInfoService;
	@Resource
	private CodeTableUtil codeTableUtil;

	@RequestMapping(value="/queryModuleTreeInfo",method= RequestMethod.POST)
  	public void queryModuleTreeInfo(HttpServletResponse response, HttpServletRequest request) throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=UTF-8");
		Map<String,Object> searchParams=new HashMap<String,Object>();
		List<TreeDataModel>  treeDataModelList=moduleInfoService.getAll(searchParams);
		json.put("unitTreeData",treeDataModelList);
		List<Code> moduleElementTypeCode=codeTableUtil.getCodesByGroupKey("SSI_CODE_TABLE", "METYPE");
		json.put("moduleElementTypeCode", moduleElementTypeCode);
		List<Code> moduleElementStateCode=codeTableUtil.getCodesByGroupKey("SSI_CODE_TABLE", "MESTATE");
		json.put("moduleElementStateCode", moduleElementStateCode);
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
  	}

	@RequestMapping(value="/queryModuleInfoById",method= RequestMethod.POST)
	public void queryModuleInfoById(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> searchParams)throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=UTF-8");
		SysModuleInfoEntity entity =moduleInfoService.findById(searchParams.get("id").toString());
		if(entity.getParent_id().equals("0")) {
			entity.setParentName("ROOT");
		}else {
			SysModuleInfoEntity parentEntity =moduleInfoService.findById(entity.getParent_id());
			entity.setParentName(parentEntity.getName());
		}
		initCodeText(entity);
		json.put("entity",entity);
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}

	@RequestMapping(value="/editModuleInfo",method= RequestMethod.POST)
	public void editModuleInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysModuleInfoEntity entity) throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("text/html; charset=UTF-8");
		moduleInfoService.update(entity);
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}

	@RequestMapping(value="/saveModuleInfo",method= RequestMethod.POST)
	public void saveModuleInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysModuleInfoEntity entity) throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("text/html; charset=UTF-8");
		moduleInfoService.insert(entity);
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}

	@RequestMapping(value="/removeModuleInfo",method= RequestMethod.POST)
	public void removeModuleInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String,Object> searchParams) throws Exception {
		JSONObject json = new JSONObject();
		response.setContentType("text/html; charset=UTF-8");
		String id=searchParams.get("id").toString();
		moduleInfoService.delete(id);
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}

	
    private void initCodeText(SysModuleInfoEntity entity) {
        if (!StringUtils.isEmpty(entity.getIs_leaf())){
            entity.setIsLeafText(codeTableUtil.getCodesByGroupKey("SSI_CODE_TABLE", "NODETYPE",
            		entity.getIs_leaf()).display);
        }
    }
    
}
