package com.fof.common.util;

import com.alibaba.fastjson.JSONObject;
import com.fof.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
    }

}
