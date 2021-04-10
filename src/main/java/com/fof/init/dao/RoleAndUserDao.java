package com.fof.init.dao;

import com.fof.init.entity.SysRoleToUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleAndUserDao {
    /**删除角色与用户对应关系通过userId*/
	Integer deleteByUserId(@Param("idList")List<String> idList);

	/**删除角色与用户对应关系通过roleId*/
	Integer deleteByRoleIdList(@Param("idList")List<String> idList);
	
	Integer removeRoleAndUser(SysRoleToUserEntity entity);
	
	Integer insertRoleAndUser(SysRoleToUserEntity entity);


}
