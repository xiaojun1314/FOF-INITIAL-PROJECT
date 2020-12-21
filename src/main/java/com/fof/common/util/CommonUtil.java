package com.fof.common.util;


import org.apache.commons.lang3.StringUtils;

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
    
	public  static String[] initSorter(String sorter,String param1,String param2) {
        String sorterRule = StringUtils.defaultIfBlank(sorter.equals("")?"":(sorter.split("-")[1].equals("descend")?"DESC":"ASC"),param1);
        String sorterField = StringUtils.defaultIfBlank(sorter.equals("")?"":sorter.split("-")[0],param2);
        return new String[] {sorterRule,sorterField};
    }
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
	    Map<Object, Boolean> concurrentHashMap = new ConcurrentHashMap<>();
	    return t -> concurrentHashMap.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
}
