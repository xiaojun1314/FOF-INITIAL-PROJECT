package com.fof.init.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fof.common.bean.JsonResult;
import com.fof.common.bean.SecurityUserInfo;
import com.fof.common.util.CommonUtil;
import com.fof.common.util.Constants;
import com.fof.common.util.ResultTool;
import com.fof.common.util.StringHelper;
import com.fof.component.redis.service.IRedisDictionaryService;
import com.fof.component.redis.util.Code;
import com.fof.component.redis.util.CodeTableUtil;
import com.fof.component.redis.util.Options;
import com.fof.init.entity.SysDictionaryInfoEntity;
import com.fof.init.entity.SysDictionaryTypeEntity;
import com.fof.init.entity.SysUserInfoEntity;
import com.fof.init.service.IDictionaryInfoService;
import com.fof.init.service.IDictionaryTypeService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dictionaryInfoManage")
public class DictionaryInfoManageController {
	@Autowired
	private IDictionaryInfoService dictionaryInfoService;

	@Autowired
	private IRedisDictionaryService redisDictionaryService;

	@Resource
	private CodeTableUtil codeTableUtil;

	@RequestMapping(value="/queryDictionaryInfoList",method= RequestMethod.POST)
	public void queryDictionaryInfoList(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> searchParams) throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=UTF-8");
		int[] pageParams =initPage(StringHelper.null2String(searchParams.get("current")), StringHelper.null2String(searchParams.get("pageSize")));
		searchParams.put("limit", pageParams[1]);
		searchParams.put("offset", pageParams[0]);
		List<SysDictionaryInfoEntity>  list =dictionaryInfoService.getAll(searchParams, searchParams.containsKey("sorter")?StringUtils.strip(searchParams.get("sorter").toString(),"{}"):"");
		json.put("data", list);
		int count =dictionaryInfoService.getCount(searchParams);
		json.put("total", count);
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}


	@RequestMapping(value="/saveDictionaryInfo",method= RequestMethod.POST)
	public void saveDictionaryInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysDictionaryInfoEntity entity) throws Exception {
		dictionaryInfoService.insert(entity);
		redisDictionaryService.reloadCache();
		JsonResult result = ResultTool.success();
		response.getWriter().write(JSON.toJSONString(result));
	}


	@RequestMapping(value="/editDictionaryInfo",method= RequestMethod.POST)
	public void editDictionaryInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysDictionaryInfoEntity entity) throws Exception {
		dictionaryInfoService.update(entity);
		redisDictionaryService.reloadCache();
		JsonResult result = ResultTool.success();
		response.getWriter().write(JSON.toJSONString(result));
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/removeDictionaryInfo",method= RequestMethod.POST)
	public void removeDictionaryInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String,Object> params)  throws Exception  {
		JSONObject json = new JSONObject();
		response.setContentType("text/html; charset=UTF-8");
		ArrayList<String> idList=(ArrayList<String>)params.get("ids");
		dictionaryInfoService.delete(idList);
		redisDictionaryService.reloadCache();
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}

	@RequestMapping(value="/checkDictionaryInfoCode",method= RequestMethod.POST)
	public void checkDictionaryInfoCode(HttpServletResponse response, HttpServletRequest request, @RequestBody SysDictionaryInfoEntity entity) throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=UTF-8");
		if(null==entity.getId()||(null!=entity.getId()&&!entity.getCode().equals(entity.getOldCode()))) {
			boolean checkResult=dictionaryInfoService.checkCode(entity);
			json.put("checkResult", checkResult);
		}else{
			json.put("checkResult", true);
		}
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}

	@RequestMapping(value="/getDictionaryInfoByKeyFlag",method= RequestMethod.POST)
	public void getDictionaryInfoByKeyFlag(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String,Object> params) throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=UTF-8");
		String keyFlag=params.get("keyFlag").toString();
		List<Code> selectList=codeTableUtil.getCodesByGroupKey("SSI_CODE_TABLE", keyFlag);
		List<Options> optionsList=new ArrayList<Options>();
		if(selectList!=null){
			for(Code code: selectList){
				Options options=new Options();
				options.setLabel(code.display);
				options.setValue(code.key);
				optionsList.add(options);
			}
		}
		json.put("optionsList", optionsList);
		JsonResult result = ResultTool.success(json);
		response.getWriter().write(JSON.toJSONString(result));
	}

    public  int[] initPage(String currentPage,String pageSize1) {
        int pageNumber = Integer.parseInt(StringUtils.defaultIfBlank(currentPage,"1"));
        int pageSize = Integer.parseInt(StringUtils.defaultIfBlank(pageSize1,"10"));
        return new int[] { pageNumber, pageSize };
    }
}
