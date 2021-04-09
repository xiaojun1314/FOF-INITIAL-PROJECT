package com.fof.common.util;

import com.alibaba.fastjson.JSONObject;
import com.fof.Application;
import com.fof.init.entity.SysDepartmentEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
/**
        String str="{userName:'ascend'}";
        //Object data1 = JSONObject.parse(str);
        //System.out.println(data1.toString());

        JSONObject data = JSONObject.parseObject(str);
        Set<Map.Entry<String, Object>> entries = data.entrySet();
        Iterator<Map.Entry<String, Object>> it = entries.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> next = it.next();
            String key = next.getKey();
            Object value = next.getValue();
            System.out.println(key);
            System.out.println(value);
        }
*/
        System.out.println(new BCryptPasswordEncoder().encode("123456"));

        List<SysDepartmentEntity> sysDepartmentList= new ArrayList<SysDepartmentEntity>();
        List<String> idList = sysDepartmentList.stream().map(item -> item.getId()).collect(Collectors.toList());
        String[] ids = idList.stream().toArray(String[]::new);
        System.out.println(ids.length);
        System.out.println("----");

        StringUtils.strip("","{}");
    }

}
