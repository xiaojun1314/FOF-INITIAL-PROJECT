package com.fof.component.redis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class CodeTableUtil {
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;
	/**存储缓存集合对象*/
    public  boolean setCodesByGroupKey(String groupKey,Map<String,Object> cacheMap) {
	     try {
		       redisTemplate.opsForHash().putAll(groupKey, cacheMap);
		       return true;
		 } catch (Exception e) {
		       e.printStackTrace();
		       return false;
		 }
    }
	
    /**获取缓存集合*/
    public  List<Code> getCodesByGroupKey(String groupKey,String keyFlag) {
    	Map<Object, Object> cacheMap=redisTemplate.opsForHash().entries(groupKey);
		List<Code> codeList =(List<Code>) cacheMap.get(keyFlag);
    	return codeList;
    }
    
    public  Code getCodesByGroupKey(String groupKey,String keyFlag,String fieldValue) {
    	Map<Object, Object> cacheMap=redisTemplate.opsForHash().entries(groupKey);
		List<Code> codeList =(List<Code>) cacheMap.get(keyFlag);
		Code returnCode=new Code();
         for(Code code:codeList) {
        	 if(code.getKey().equals(fieldValue)) {
        		 returnCode=code;
        	 }
         }
    	return returnCode;
    }
    /**删除全部缓存数据*/

    public  void delCacheByGroupKey(String... groupKey) {
        if (groupKey != null && groupKey.length > 0) {
 	       if (groupKey.length == 1) {
 	         redisTemplate.delete(groupKey[0]);
 	       } else {
			   redisTemplate.delete(Arrays.asList(groupKey));
 	       }
 	     }
    }

    /**根据key 删除数据*/
    public  void delCacheByKeyFlag(String groupKey,String keyFlag) {
    	Map<Object, Object> cacheMap=redisTemplate.opsForHash().entries(groupKey);
    	cacheMap.remove(keyFlag);
    	//删除key 
    	redisTemplate.delete(groupKey);
    	//重新加载数据
    	redisTemplate.opsForHash().putAll(groupKey, cacheMap);
    }
    
    /**根据key 删除数据*/
    public  void delCacheByKeyFlag(String groupKey,String[] keyFlag) {
    	Map<Object, Object> cacheMap=redisTemplate.opsForHash().entries(groupKey);
    	for(String str:keyFlag) {
    		cacheMap.remove(str);
    	}
    	//删除key 
    	redisTemplate.delete(groupKey);
    	//重新加载数据
    	redisTemplate.opsForHash().putAll(groupKey, cacheMap);
    }
    
    /**更新 缓存中key的值*/
    public  void updateCacheByKeyValue(String groupKey,String oldkey,String newkey,List<Code> codeList) {
    	Map<Object, Object> cacheMap=redisTemplate.opsForHash().entries(groupKey);
    	cacheMap.remove(oldkey);
    	cacheMap.put(newkey, codeList);
    	//删除key 
    	redisTemplate.delete(groupKey);
    	//重新加载数据
    	redisTemplate.opsForHash().putAll(groupKey,cacheMap);
    }
    
}
