package com.fof.common.util;


import com.fof.common.bean.SecurityUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class CommonUtil {
	public static Map<String, Object> getSearchParameters(String subParameters ,Map<String, Object> queryParams) {
    	Map<String, Object> searchParams=new HashMap<String, Object>();
    	Set<String> keys = queryParams.keySet();
		for(String key:keys){
			if(key.contains(subParameters)) {
				searchParams.put(key.substring(key.indexOf("_")+1,key.length()),queryParams.get(key));
			}
		}
		return searchParams;
	}	
    public  static int[] initPage(String currentPage,String pageSize1) {
        int pageNumber = Integer.parseInt(StringUtils.defaultIfBlank(currentPage,"1"));
        int pageSize = Integer.parseInt(StringUtils.defaultIfBlank(pageSize1,"10"));
        return new int[] { pageNumber, pageSize };
    }

	public static String[] initSorter(String sorter) {
		String sorterRule = StringUtils.defaultIfBlank(sorter.equals("")?"":(sorter.split("=")[1].equals("descend")?"DESC":"ASC"),"DESC");
		String sorterField = StringUtils.defaultIfBlank(sorter.equals("")?"":sorter.split("=")[0],"CREATE_TIME");
		return new String[] {sorterRule,sorterField};
	}
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
	    Map<Object, Boolean> concurrentHashMap = new ConcurrentHashMap<>();
	    return t -> concurrentHashMap.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	public static SecurityUserInfo getSecurityUserInfo(){
		return (SecurityUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
