package com.fof.init.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fof.common.bean.JsonResult;
import com.fof.common.util.Constants;
import com.fof.common.util.ResultTool;
import com.fof.common.util.StringHelper;
import com.fof.component.redis.util.CodeTableUtil;
import com.fof.init.entity.SysModuleElementEntity;
import com.fof.init.entity.SysModuleInfoEntity;
import com.fof.init.entity.SysRoleInfoEntity;
import com.fof.init.service.IModuleElementService;
import com.fof.init.service.IModuleInfoService;
import com.fof.init.service.IRoleAndUserService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @className: ModuleElementManageController
 * @author: jun
 * @date: 2021-04-01 21:03
 * @Depiction:
 **/

@Controller
@RequestMapping("/moduleElementManage")
public class ModuleElementManageController {

    @Autowired
    private IModuleElementService moduleElementService;

    @Autowired
    private IModuleInfoService moduleInfoService;

    @Resource
    private CodeTableUtil codeTableUtil;

    @RequestMapping(value="/queryModuleElementList",method= RequestMethod.POST)
    public void queryModuleElementList(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> searchParams) throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        String selectKey= searchParams.get("selectKey").toString();
        int[] pageParams =initPage(StringHelper.null2String(searchParams.get("current")), StringHelper.null2String(searchParams.get("pageSize")));

        Map<String, Object> temporaryParams=new HashMap<String, Object>();
        temporaryParams.put("id",selectKey);
        List<SysModuleInfoEntity> sysModuleInfoList= moduleInfoService.getLeafModuleById(temporaryParams);

        List<String> moduleIdList = sysModuleInfoList.stream().map(item -> item.getId()).collect(Collectors.toList());
        List<SysModuleElementEntity> list =null;
        if(selectKey.equals("root") || sysModuleInfoList.size()>0){
            if(sysModuleInfoList.size()>0){
                searchParams.put("moduleIdList",moduleIdList);
            }
            searchParams.put("limit", pageParams[1]);
            searchParams.put("offset", pageParams[0]);
            list =moduleElementService.getAll(searchParams, searchParams.containsKey("sorter")? StringUtils.strip(searchParams.get("sorter").toString(),"{}"):"");
            initCodeText(list);
            int count =moduleElementService.getCount(searchParams);
            json.put("data", list);
            json.put("total", count);
        }else{
            json.put("data", list);
            json.put("total", 0);
        }

        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    @RequestMapping(value="/checkModuleElementCode",method= RequestMethod.POST)
    public void checkModuleElementCode(HttpServletResponse response, HttpServletRequest request, @RequestBody SysModuleElementEntity entity) throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        if(null==entity.getId()||(null!=entity.getId()&&!entity.getCode().equals(entity.getOldCode()))) {
            boolean checkResult=moduleElementService.checkCode(entity);
            json.put("checkResult", checkResult);
        }else{
            json.put("checkResult", true);
        }
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    @RequestMapping(value="/saveModuleElementInfo",method= RequestMethod.POST)
    public void saveModuleElementInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysModuleElementEntity entity) throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        moduleElementService.insert(entity);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }


    @RequestMapping(value="/editModuleElementInfo",method= RequestMethod.POST)
    public void editModuleElementInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody SysModuleElementEntity entity)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        moduleElementService.update(entity);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    /**删除数据*/
    @RequestMapping(value="/removeModuleElementInfo",method= RequestMethod.POST)
    public void removeModuleElementInfo(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, Object> params)throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        ArrayList<String> idList=(ArrayList<String>)params.get("ids");
        moduleElementService.delete(idList);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }
    @RequestMapping(value="/getModuleElementInfoByRole",method=RequestMethod.POST)
    public void getModuleElementInfoByRole(HttpServletResponse response,HttpServletRequest request,@RequestBody Map<String, Object> params) throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");

        List<SysModuleInfoEntity> sysModuleInfoList=moduleInfoService.getModuleLevelName();
        // 已授权 元素列表 被选择元素列表
        List<SysModuleElementEntity> authorizedElementList= moduleElementService.getElementByRole(params);

        List<String> allCheckedElementIdList =authorizedElementList.stream().map( item ->item.getId()).collect(Collectors.toList());

        for(SysModuleInfoEntity sysModuleInfoEntity:sysModuleInfoList) {
            List<Map<String,String>> moduleElementList=new ArrayList<Map<String,String>>();
            List<String> pageElementIdList=new ArrayList<String>();
            params.put("module_id", sysModuleInfoEntity.getId());
            List<SysModuleElementEntity> authorizedElementPartList= moduleElementService.getElementByRole(params);

            List<String> partCheckedElementIdList=authorizedElementPartList.stream().map( item ->item.getId()).collect(Collectors.toList());

            List<SysModuleElementEntity>  sysModuleElementList=moduleElementService.getByModuleId(sysModuleInfoEntity.getId());
            for(SysModuleElementEntity sysModuleElementEntity:sysModuleElementList) {
                Map<String,String> map=new HashMap<String,String>();
                map.put("key", sysModuleElementEntity.getId());
                map.put("label", sysModuleElementEntity.getName());
                map.put("value", sysModuleElementEntity.getId());
                moduleElementList.add(map);
                pageElementIdList.add(sysModuleElementEntity.getId());
            }
            /**页面元素 Options*/
            sysModuleInfoEntity.setElementList(moduleElementList);
            /**页面元素ID*/
            sysModuleInfoEntity.setElementIdList(pageElementIdList);
            /**被选择页面元素ID*/
            sysModuleInfoEntity.setCheckedList(partCheckedElementIdList);
        }
        json.put("moduleInfo", sysModuleInfoList);
        json.put("allCheckListId", allCheckedElementIdList);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    public  int[] initPage(String currentPage,String pageSize1) {
        int pageNumber = Integer.parseInt(StringUtils.defaultIfBlank(currentPage,"1"));
        int pageSize = Integer.parseInt(StringUtils.defaultIfBlank(pageSize1,"15"));
        return new int[] { pageNumber, pageSize };
    }

    private void initCodeText(List<SysModuleElementEntity> views) {
        if (CollectionUtils.isEmpty(views)) {
            return;
        }
        for (SysModuleElementEntity entity : views) {
            if (!StringUtils.isEmpty(entity.getState())){
                entity.setStateText(codeTableUtil.getCodesByGroupKey("SSI_CODE_TABLE", "ROLESTATE",entity.getState()).display);
            }
        }
    }

}
