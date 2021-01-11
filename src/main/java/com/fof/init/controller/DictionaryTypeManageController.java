package com.fof.init.controller;

import com.alibaba.fastjson.JSONObject;
import com.fof.common.bean.SecurityUserInfo;
import com.fof.common.util.Constants;
import com.fof.common.util.StringHelper;
import com.fof.component.redis.service.IRedisDictionaryService;
import com.fof.init.entity.SysDictionaryTypeEntity;
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
  	public String queryDictionaryTypeList(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> searchParams)throws Exception{
      	JSONObject json = new JSONObject();
    	response.setContentType("application/json;charset=UTF-8");
    	try {
			int[] pageParams =initPage(StringHelper.null2String(searchParams.get("current")), StringHelper.null2String(searchParams.get("pageSize")));
		    searchParams.put("limit", pageParams[1]);
		    searchParams.put("offset", pageParams[0]);
		    searchParams.put("deleteFlag", Constants.DELFLG_N);
		    List<SysDictionaryTypeEntity>  list =dictionaryTypeService.getAll(searchParams, StringHelper.null2String(searchParams.get("sorter")));
			json.put("data", list);
		    int count =dictionaryTypeService.getCount(searchParams);
			json.put("IsSuccess", true);
			json.put("total", count);
			json.put("Message", "查询成功");
		} catch (Exception e) {
		    json.put("IsSuccess", false);
		    json.put("Message", "查询失败");
       }
       try {
    	    response.getWriter().write(json.toJSONString());
       } catch (Exception e) {
    	    json.put("IsSuccess", false);
    	    json.put("Message", "查询失败");
       }    
      	return null;
  	}
    
    @RequestMapping(value="/checkDictionaryTypeCode",method= RequestMethod.POST)
  	public String checkDictionaryTypeCode(HttpServletResponse response, HttpServletRequest request, @RequestBody SysDictionaryTypeEntity entity)throws Exception{
    	JSONObject json = new JSONObject();
    	response.setContentType("application/json;charset=UTF-8");
    	try {
    		if(null==entity.getId()||(null!=entity.getId()&&!entity.getCode().equals(entity.getOldCode()))) {
    			boolean checkResult=dictionaryTypeService.checkCode(entity);
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
            json.put("IsSuccess", false);
            json.put("Message", "检查不通过");
        }  
      	return null;
  	}
    
    @RequestMapping(value="/saveDictionaryType",method= RequestMethod.POST)
  	public String saveDictionaryTypeInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysDictionaryTypeEntity entity){
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
    	try {
			SecurityUserInfo securityUserInfo = (SecurityUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		entity.setCreater(securityUserInfo.getId());
    	    int flag= dictionaryTypeService.insert(entity);
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
    	     json.put("IsSuccess", false);
    	     json.put("Message", "保存失败");
         }  
         return null;
  	}
      
    @RequestMapping(value="/editDictionaryType",method= RequestMethod.POST)
  	public String editDictionaryTypeInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysDictionaryTypeEntity entity){
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
    	try {
			SecurityUserInfo securityUserInfo = (SecurityUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		entity.setUpdater(securityUserInfo.getId());
    	    int flag= dictionaryTypeService.update(entity);
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
   	       json.put("IsSuccess", false);
   	       json.put("Message", "更新失败");
        } 
        return null;
  	}
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/removeDictionaryType",method= RequestMethod.POST)
    public Object removeDataInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String,Object> params) {
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        try {
			SecurityUserInfo securityUserInfo = (SecurityUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        	ArrayList<String> idsList=(ArrayList<String>)params.get("ids");
        	if(idsList.size()>0) {
        		//String[] ids = idsList.stream().toArray(String[]::new);
				dictionaryTypeService.delete(securityUserInfo.getId(),idsList);
        		/**重新加载缓存*/
				redisDictionaryService.reloadCache();
                json.put("IsSuccess", true);
                json.put("Message", "删除成功");  		
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
   	        json.put("IsSuccess", false);
   	        json.put("Message", "删除失败");
        }  
        return null;
    }

	@RequestMapping(value="/queryDictionaryTypeDetail",method= RequestMethod.POST)
	public void queryDictionaryTypeDetail(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> params)throws Exception{
		JSONObject json = new JSONObject();
		response.setContentType("text/html; charset=UTF-8");
		try {
			if(params.containsKey("id")) {
				String id=params.get("id").toString();
				if (null != id && id.length() > 0) {
					SysDictionaryTypeEntity sysDictionaryTypeEntity=dictionaryTypeService.findById(id);
					json.put("dictionaryTypeEntity", sysDictionaryTypeEntity);
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
