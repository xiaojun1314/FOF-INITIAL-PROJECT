package com.fof.init.controller;


import com.alibaba.fastjson.JSON;
import com.fof.common.bean.JsonResult;
import com.fof.common.util.ResultTool;
import com.fof.init.entity.SysUserInfoEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/initial")
public class SystemInitialController {

    @RequestMapping(value = "/currentUser",method = RequestMethod.POST)
    @ResponseBody
    public void currentUser(HttpServletResponse response) throws IOException {


        response.setContentType("application/json;charset=UTF-8");
        // Subject currentUser = SecurityUtils.getSubject();
        SysUserInfoEntity sysUserInfoEntity=new SysUserInfoEntity();
        sysUserInfoEntity.setFullName("fullname");
        sysUserInfoEntity.setSimpleName("simplename");
        sysUserInfoEntity.setUserName("username");

        //(SysUserInfoEntity)currentUser.getPrincipal();

        //sysUserInfoEntity.setToken(currentUser.getSession().getId().toString());
        JsonResult result = ResultTool.success(sysUserInfoEntity);
        response.getWriter().write(JSON.toJSONString(result));
        //response.getWriter().write(JSONObject.toJSONString(sysUserInfoEntity));

    }
}
