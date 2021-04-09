package com.fof.init.dao;


import com.fof.init.entity.SysAuthorityEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AuthorityInfoDao {

	public List<SysAuthorityEntity> getAll(Map<String,Object> map);
	
	public Integer getCount(Map<String,Object> map);
	
	public Integer insert(SysAuthorityEntity entity);
	
	public Integer update(SysAuthorityEntity entity);

	/**删除一条权限信息*/
	public Integer deleteById(@Param(value = "deleter") String deleter, @Param(value = "id") String id);
	
	/**批量删除权限信息*/
	public void  deleteByIdList(@Param("idList") List<String> idList);
	
	public SysAuthorityEntity findAuthCountInfo(Map<String,Object> map);

	public void  deleteByModuleElementIdList(@Param("idList") List<String> idList);
	
}
