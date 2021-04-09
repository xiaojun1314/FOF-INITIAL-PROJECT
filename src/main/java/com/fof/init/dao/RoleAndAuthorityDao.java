package com.fof.init.dao;

import java.util.List;
import java.util.Map;

import com.fof.init.entity.SysRoleToAuthorityEntity;
import org.apache.ibatis.annotations.Param;

public interface RoleAndAuthorityDao {

	Integer deleteByRoleIdList(@Param("idList")List<String> idList);
	
	Integer insertRoleAndAuth(SysRoleToAuthorityEntity entity);
	
	Integer deleteRoleAndAuthByRoleId(Map<String,Object> map);

	Integer deleteByAuthorityId(@Param("idList")List<String> idList);
}
