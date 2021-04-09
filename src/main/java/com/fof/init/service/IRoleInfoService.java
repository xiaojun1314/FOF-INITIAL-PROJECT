package com.fof.init.service;

import com.fof.init.entity.SysRoleInfoEntity;

import java.util.List;
import java.util.Map;

public interface IRoleInfoService {

	public List<SysRoleInfoEntity> getAll(Map<String,Object> map, String sorter);
	
	public Integer getCount(Map<String,Object> map);
	
	public Integer insert(SysRoleInfoEntity entity);
		
	public Integer update(SysRoleInfoEntity entity);
	 
	public Integer delete(List<String> idsList);
	
	public boolean checkCode(SysRoleInfoEntity entity);
	
	public SysRoleInfoEntity getById(String id);
	
}
