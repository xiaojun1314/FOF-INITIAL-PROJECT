package com.fof.init.service;

import com.fof.init.entity.SysDictionaryInfoEntity;

import java.util.List;
import java.util.Map;

public interface IDictionaryInfoService {
	public List<SysDictionaryInfoEntity> getAll(Map<String,Object> map, String sorter);
    
	public Integer getCount(Map<String,Object> map);

	public SysDictionaryInfoEntity findById(String id);
    
	public Integer insert(SysDictionaryInfoEntity entity);
	
	public Integer update(SysDictionaryInfoEntity entity);

	public Integer delete(List<String> idList);
	
    boolean  checkCode(SysDictionaryInfoEntity entity);
    
}
