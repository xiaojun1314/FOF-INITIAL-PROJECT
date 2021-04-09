package com.fof.init.service;

import com.fof.init.entity.SysDictionaryTypeEntity;

import java.util.List;
import java.util.Map;

public interface IDictionaryTypeService {
	
	public List<SysDictionaryTypeEntity> getAll(Map<String,Object> map, String sorter);
	
	public Integer getCount(Map<String,Object> map);
		
	public SysDictionaryTypeEntity findById(String id);

	public Integer insert(SysDictionaryTypeEntity entity);
	
	public Integer update(SysDictionaryTypeEntity entity);

	public Integer delete(List<String> idList);
	
	boolean  checkCode(SysDictionaryTypeEntity entity);
}
