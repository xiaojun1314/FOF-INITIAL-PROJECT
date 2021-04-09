package com.fof.init.dao;

import com.fof.init.entity.SysDictionaryTypeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DictionaryTypeDao {

	public List<SysDictionaryTypeEntity> getAll(Map<String,Object> map);

	public Integer getCount(Map<String,Object> map);

	public SysDictionaryTypeEntity findById(String id);

	public Integer insert(SysDictionaryTypeEntity entity);

	public Integer update(SysDictionaryTypeEntity entity);

	public Integer delete(@Param("idList") List<String> idList);

	public Integer checkCode(SysDictionaryTypeEntity entity);

}
