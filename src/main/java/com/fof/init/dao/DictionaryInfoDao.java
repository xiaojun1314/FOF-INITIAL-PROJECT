package com.fof.init.dao;


import com.fof.init.entity.SysDictionaryInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DictionaryInfoDao {

	public List<SysDictionaryInfoEntity> getAll(Map<String,Object> map);
	
	public Integer getCount(Map<String,Object> map);
	
	public Integer checkCode(SysDictionaryInfoEntity entity);
	
	public Integer insert(SysDictionaryInfoEntity entity);
	
	public Integer update(SysDictionaryInfoEntity entity);

	public SysDictionaryInfoEntity findById(String id);
	
	public Integer delete(@Param("idList") List<String> idList);

	public Integer deleteByForeignId(@Param("idList") List<String> idList);
}
