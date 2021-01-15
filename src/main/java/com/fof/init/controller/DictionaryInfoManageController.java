package com.fof.init.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fof.common.bean.SecurityUserInfo;
import com.fof.common.util.CommonUtil;
import com.fof.common.util.Constants;
import com.fof.common.util.StringHelper;
import com.fof.component.redis.service.IRedisDictionaryService;
import com.fof.init.entity.SysDictionaryInfoEntity;
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

	@RequestMapping(value="/queryDictionaryInfoList",method= RequestMethod.POST)
	public void queryDictionaryInfoList(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> searchParams)throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=UTF-8");
		try {
			int[] pageParams =initPage(StringHelper.null2String(searchParams.get("current")), StringHelper.null2String(searchParams.get("pageSize")));
			searchParams.put("limit", pageParams[1]);
			searchParams.put("offset", pageParams[0]);
			searchParams.put("delete_flag", Constants.DELFLG_N);
			System.out.println(searchParams.get("foreignId"));
			List<SysDictionaryInfoEntity>  list =dictionaryInfoService.getAll(searchParams, StringUtils.strip(searchParams.get("sorter").toString(),"{}"));
			json.put("data", list);
			int count =dictionaryInfoService.getCount(searchParams);
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

	@RequestMapping(value="/checkDictionaryInfoCode",method= RequestMethod.POST)
	public void checkDictionaryInfoCode(HttpServletResponse response, HttpServletRequest request, @RequestBody SysDictionaryInfoEntity entity)throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=UTF-8");
		try {
			if(null==entity.getId()||(null!=entity.getId()&&!entity.getCode().equals(entity.getOldCode()))) {
				boolean checkResult=dictionaryInfoService.checkCode(entity);
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

	@RequestMapping(value="/saveDictionaryInfo",method= RequestMethod.POST)
	public void saveDictionaryInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysDictionaryInfoEntity entity){
		JSONObject json = new JSONObject();
		response.setContentType("text/html; charset=UTF-8");
		try {
			SecurityUserInfo securityUserInfo = (SecurityUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			entity.setCreater(securityUserInfo.getId());
			int flag= dictionaryInfoService.insert(entity);
			/**重新加载缓存*/
			redisDictionaryService.reloadCache();
			if(flag==1) {
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


	@RequestMapping(value="/editDictionaryInfo",method= RequestMethod.POST)
	public void editDictionaryInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysDictionaryInfoEntity entity){
		JSONObject json = new JSONObject();
		response.setContentType("text/html; charset=UTF-8");
		try {
			SecurityUserInfo securityUserInfo = (SecurityUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			entity.setUpdater(securityUserInfo.getId());
			int flag= dictionaryInfoService.update(entity);
			/**重新加载缓存*/
			redisDictionaryService.reloadCache();
			if(flag==1) {
				json.put("IsSuccess", true);
				json.put("Message", "更新成功");
			}else {
				json.put("IsSuccess", false);
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



	@SuppressWarnings("unchecked")
	@RequestMapping(value="/removeDictionaryInfo",method= RequestMethod.POST)
	public void removeDictionaryInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String,Object> params) {
		JSONObject json = new JSONObject();
		response.setContentType("text/html; charset=UTF-8");
		try {
			SecurityUserInfo securityUserInfo = (SecurityUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			ArrayList<String> idsList=(ArrayList<String>)params.get("ids");
			if(idsList.size()>0) {
				// String[] ids = idsList.stream().toArray(String[]::new);
				dictionaryInfoService.delete(securityUserInfo.getId(),idsList);
				/**重新加载缓存*/
				redisDictionaryService.reloadCache();
				json.put("IsSuccess", true);
				json.put("Message", "删除成功");
			}else {
				json.put("IsSuccess", false);
				json.put("Message", "删除失败");
			}
		} catch (Exception e) {
			json.put("IsSuccess", false);
			json.put("Message", "删除失败");
		}
		try {
			response.getWriter().write(json.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value="/queryDictionaryInfoDetail",method= RequestMethod.POST)
	public void queryDictionaryInfoDetail(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> params)throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("text/html; charset=UTF-8");
		try {
			if(params.containsKey("id")) {
				String id=params.get("id").toString();
				if (null != id && id.length() > 0) {
					SysDictionaryInfoEntity sysDictionaryInfoEntity=dictionaryInfoService.findById(id);
					json.put("dictionaryInfoEntity", sysDictionaryInfoEntity);
					json.put("IsSuccess", true);
					json.put("Message", "操作成功");
				}else {
					json.put("IsSuccess", false);
					json.put("Message", "操作失败");
				}
			}else {
				json.put("IsSuccess", false);
				json.put("Message", "操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("IsSuccess", false);
			json.put("Message", "操作失败");
		}
		try {
			response.getWriter().write(json.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public  int[] initPage(String currentPage,String pageSize1) {
        int pageNumber = Integer.parseInt(StringUtils.defaultIfBlank(currentPage,"1"));
        int pageSize = Integer.parseInt(StringUtils.defaultIfBlank(pageSize1,"10"));
        return new int[] { pageNumber, pageSize };
    }
}
