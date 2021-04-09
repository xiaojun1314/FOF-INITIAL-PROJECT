package com.fof.init.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fof.common.bean.JsonResult;
import com.fof.common.entity.RoutingMenuTree;
import com.fof.common.util.CommonUtil;
import com.fof.common.util.ResultTool;
import com.fof.init.entity.SysMenuInfoEntity;
import com.fof.init.service.IMenuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: RoutingManageController
 * @author: jun
 * @date: 2021-03-31 14:25
 * @Depiction:
 **/

@Controller
@RequestMapping("/routingManage")
public class RoutingManageController {

    @Autowired
    private IMenuInfoService menuInfoService;

    @RequestMapping(value="/getRouting",method= RequestMethod.POST)
    public void queryUserList1(HttpServletResponse response, HttpServletRequest request) throws Exception{
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        List<RoutingMenuTree>  routingMenuTreeList=menuInfoService.getMenuByUserId();
        JsonResult result = ResultTool.success(routingMenuTreeList);
        response.getWriter().write(JSON.toJSONString(result));
    }
}
