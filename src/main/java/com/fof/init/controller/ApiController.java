package com.fof.init.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class ApiController {

    @RequestMapping(value = "/getrouteinfo",method = RequestMethod.POST)
    @ResponseBody
    public void getrouteinfo(HttpServletResponse response) throws Exception{
        List<Map<String,Object>> mapList=new ArrayList<Map<String,Object>>();
        Map<String,Object> map1=new HashMap<String,Object>();
        map1.put("path", "/welcome");
        map1.put("name", "welcome");
        map1.put("icon", "smile");
        // map1.put("access", "admin");

        map1.put("component", "./Welcome");
        mapList.add(map1);

        Map<String,Object> map2=new HashMap<String,Object>();
        map2.put("path", "/systemManage");
        map2.put("name", "systemManage");
        map2.put("icon", "table");

        List<Map<String,Object>> list22= new ArrayList<Map<String,Object>>();
        Map<String,Object> map22=new HashMap<String,Object>();
        map22.put("path", "/systemManage/userInfoList");
        map22.put("name", "userInfoList");
        map22.put("icon", "table");
        map22.put("component", "./systemManage/userInfoList");
        list22.add(map22);
        map2.put("routes",list22);
        mapList.add(map2);

        // String jsonArray = JSONArray.toJSONString(mapList);
        // jsonArray=jsonArray.substring(1, jsonArray.length()-1);
        
        String st1="[{path: '/welcome',name: 'welcome',icon: 'smile',component: './welcome'}]";

        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        json.put("data", mapList);

        response.getWriter().write(json.toJSONString());
    }

}
