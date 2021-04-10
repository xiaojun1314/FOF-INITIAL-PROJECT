package com.fof.init.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fof.common.bean.JsonResult;
import com.fof.common.util.ResultTool;
import com.fof.common.util.StringHelper;
import com.fof.init.entity.SysModuleInfoEntity;
import com.fof.init.entity.SysModuleOperationEntity;
import com.fof.init.service.IModuleInfoService;
import com.fof.init.service.IModuleOperationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;

/*
 * @className: ModuleElementManageController
 * @author: jun
 * @date: 2021-04-01 21:03
 * @Depiction:
 */

@Controller
@RequestMapping("/moduleOperationManage")
public class ModuleOperationManageController {

    @Autowired
    private IModuleOperationService moduleOperationService;

    @Autowired
    private IModuleInfoService moduleInfoService;

    @RequestMapping(value="/queryModuleOperationList",method= RequestMethod.POST)
    public void queryModuleOperationList(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> searchParams) throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        String selectKey= searchParams.get("selectKey").toString();
        int[] pageParams =initPage(StringHelper.null2String(searchParams.get("current")), StringHelper.null2String(searchParams.get("pageSize")));

        Map<String, Object> temporaryParams=new HashMap<String, Object>();
        temporaryParams.put("id",selectKey);
        List<SysModuleInfoEntity> sysModuleInfoList= moduleInfoService.getLeafModuleById(temporaryParams);

        List<String> moduleIdList = sysModuleInfoList.stream().map(item -> item.getId()).collect(Collectors.toList());
        List<SysModuleOperationEntity> list =null;
        if(selectKey.equals("root") || sysModuleInfoList.size()>0){
            if(sysModuleInfoList.size()>0){
                searchParams.put("moduleIdList",moduleIdList);
            }
            searchParams.put("limit", pageParams[1]);
            searchParams.put("offset", pageParams[0]);
            list =moduleOperationService.getAll(searchParams, searchParams.containsKey("sorter")? StringUtils.strip(searchParams.get("sorter").toString(),"{}"):"");
            int count =moduleOperationService.getCount(searchParams);
            json.put("data", list);
            json.put("total", count);
        }else{
            json.put("data", list);
            json.put("total", 0);
        }

        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    @RequestMapping(value="/checkModuleOperationCode",method= RequestMethod.POST)
    public void checkModuleOperationCode(HttpServletResponse response, HttpServletRequest request, @RequestBody SysModuleOperationEntity entity) throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        if(null==entity.getId()||(null!=entity.getId()&&!entity.getCode().equals(entity.getOldCode()))) {
            boolean checkResult=moduleOperationService.checkCode(entity);
            json.put("checkResult", checkResult);
        }else{
            json.put("checkResult", true);
        }
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    @RequestMapping(value="/saveModuleOperationInfo",method= RequestMethod.POST)
    public void saveModuleOperationInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysModuleOperationEntity entity) throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        moduleOperationService.insert(entity);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }


    @RequestMapping(value="/editModuleOperationInfo",method= RequestMethod.POST)
    public void editModuleOperationInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysModuleOperationEntity entity)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        moduleOperationService.update(entity);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    /**删除数据*/
    @RequestMapping(value="/removeModuleOperationInfo",method= RequestMethod.POST)
    public void removeModuleOperationInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> params)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        ArrayList<String> idList=(ArrayList<String>)params.get("ids");
        moduleOperationService.delete(idList);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    @RequestMapping(value="/getModuleOperationInfoByRole",method=RequestMethod.POST)
    public void getModuleOperationInfoByRole(HttpServletResponse response,HttpServletRequest request,@RequestBody Map<String, Object> params) throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");

        List<SysModuleInfoEntity> sysModuleInfoList=moduleInfoService.getModuleLevelName();
        // 已授权 元素列表 被选择元素列表
        List<SysModuleOperationEntity> authorizedOperationList= moduleOperationService.getOperationByRole(params);

        List<String> allCheckedOperationIdList =authorizedOperationList.stream().map( item ->item.getId()).collect(Collectors.toList());

        for(SysModuleInfoEntity sysModuleInfoEntity:sysModuleInfoList) {
            List<Map<String,String>> moduleOperationList=new ArrayList<Map<String,String>>();
            List<String> pageOperationIdList=new ArrayList<String>();
            params.put("module_id", sysModuleInfoEntity.getId());
            List<SysModuleOperationEntity> authorizedOperationPartList= moduleOperationService.getOperationByRole(params);
            List<String> partCheckedOperationIdList=authorizedOperationPartList.stream().map( item ->item.getId()).collect(Collectors.toList());
            List<SysModuleOperationEntity>  sysModuleOperationList=moduleOperationService.getByModuleId(sysModuleInfoEntity.getId());
            for(SysModuleOperationEntity sysModuleOperationEntity:sysModuleOperationList) {
                Map<String,String> map=new HashMap<String,String>();
                map.put("key", sysModuleOperationEntity.getId());
                map.put("label", sysModuleOperationEntity.getName());
                map.put("value", sysModuleOperationEntity.getId());
                moduleOperationList.add(map);
                pageOperationIdList.add(sysModuleOperationEntity.getId());
            }
            /**模块操作 Options*/
            sysModuleInfoEntity.setOperationList(moduleOperationList);
            /**模块操作ID*/
            sysModuleInfoEntity.setOperationIdList(pageOperationIdList);
            /**被选择页面元素ID*/
            sysModuleInfoEntity.setCheckedList(partCheckedOperationIdList);
        }
        json.put("moduleInfo", sysModuleInfoList);
        json.put("allCheckListId", allCheckedOperationIdList);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    public  int[] initPage(String currentPage,String pageSize1) {
        int pageNumber = Integer.parseInt(StringUtils.defaultIfBlank(currentPage,"1"));
        int pageSize = Integer.parseInt(StringUtils.defaultIfBlank(pageSize1,"15"));
        return new int[] { pageNumber, pageSize };
    }

}
