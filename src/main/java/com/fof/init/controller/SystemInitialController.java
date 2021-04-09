package com.fof.init.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fof.common.bean.JsonResult;
import com.fof.common.bean.SecurityUserInfo;
import com.fof.common.entity.RoutingMenuTree;
import com.fof.common.util.ResultTool;
import com.fof.init.entity.SysUserInfoEntity;
import com.fof.init.service.IMenuInfoService;
import com.fof.init.service.IModuleElementService;
import com.fof.init.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/initial")
public class SystemInitialController {

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IMenuInfoService menuInfoService;

    @Autowired
    private IModuleElementService moduleElementService;

    @RequestMapping(value = "/currentUser",method = RequestMethod.POST)
    @ResponseBody
    public void currentUser(HttpServletResponse response) throws IOException {
        JSONObject json = new JSONObject();
        response.setContentType("text/html; charset=UTF-8");
        SecurityUserInfo securityUserInfo=  (SecurityUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUserInfoEntity  sysUserInfoEntity=userInfoService.findById(securityUserInfo.getId());
        List<RoutingMenuTree> routingMenuTreeList=menuInfoService.getMenuByUserId();
        List<RoutingMenuTree> routingLeafTreeList=menuInfoService.getLeafMenuByUserId();
        List<String> elementCodeList=moduleElementService.getElementByUserId();
        json.put("currentUser", sysUserInfoEntity);
        json.put("menuData", routingMenuTreeList);
        json.put("leafMenuData", routingLeafTreeList);
        json.put("elementCodeData", elementCodeList);
        JsonResult result = ResultTool.success(json);
        response.getWriter().write(JSON.toJSONString(result));
    }

    /*根据标识获取 数据字典列表*/

}
