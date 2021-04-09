package com.fof.init.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fof.common.bean.JsonResult;
import com.fof.common.bean.SecurityUserInfo;
import com.fof.common.util.Constants;
import com.fof.common.util.ResultTool;
import com.fof.common.util.StringHelper;
import com.fof.component.redis.service.IRedisDictionaryService;
import com.fof.init.entity.SysDictionaryTypeEntity;
import com.fof.init.entity.SysUserInfoEntity;
import com.fof.init.service.IDictionaryTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dictionaryTypeManage")
public class DictionaryTypeManageController {
    @Autowired
	private IDictionaryTypeService dictionaryTypeService;
	@Autowired
	private IRedisDictionaryService redisDictionaryService;

    @RequestMapping(value="/queryDictionaryTypeList",method= RequestMethod.POST)
  	public void queryDictionaryTypeList(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> searchParams) throws Exception{
      	JSONObject json = new JSONObject();
    	response.setContentType("application/json;charset=UTF-8");
		int[] pageParams =initPage(StringHelper.null2String(searchParams.get("current")), StringHelper.null2String(searchParams.get("pageSize")));
		searchParams.put("limit", pageParams[1]);
		searchParams.put("offset", pageParams[0]);
		List<SysDictionaryTypeEntity>  list =dictionaryTypeService.getAll(searchParams, searchParams.containsKey("sorter")?StringUtils.strip(searchParams.get("sorter").toString(),"{}"):"");
		json.put("data", list);
		int count =dictionaryTypeService.getCount(searchParams);
		json.put("total", count);
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
  	}

	@RequestMapping(value="/saveDictionaryType",method= RequestMethod.POST)
	public void saveDictionaryTypeInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysDictionaryTypeEntity entity) throws Exception{
		dictionaryTypeService.insert(entity);
		redisDictionaryService.reloadCache();
		JsonResult result = ResultTool.success();
		response.getWriter().write(JSON.toJSONString(result));
	}

	@RequestMapping(value="/editDictionaryType",method= RequestMethod.POST)
	public void editDictionaryTypeInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysDictionaryTypeEntity entity) throws Exception{
		dictionaryTypeService.update(entity);
		redisDictionaryService.reloadCache();
		JsonResult result = ResultTool.success();
		response.getWriter().write(JSON.toJSONString(result));
	}


	@RequestMapping(value="/removeDictionaryType",method= RequestMethod.POST)
	public void removeDataInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String,Object> params) throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("text/html; charset=UTF-8");
		ArrayList<String> idList=(ArrayList<String>)params.get("ids");
		dictionaryTypeService.delete(idList);
		redisDictionaryService.reloadCache();
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}
    
    @RequestMapping(value="/checkDictionaryTypeCode",method= RequestMethod.POST)
  	public void checkDictionaryTypeCode(HttpServletResponse response, HttpServletRequest request, @RequestBody SysDictionaryTypeEntity entity) throws Exception{
    	JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=UTF-8");
		if(null==entity.getId()||(null!=entity.getId()&&!entity.getCode().equals(entity.getOldCode()))) {
			boolean checkResult=dictionaryTypeService.checkCode(entity);
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
}
