package com.fof.init.service.impl;

import com.fof.init.dao.RoleAndUserDao;
import com.fof.init.entity.SysRoleToUserEntity;
import com.fof.init.service.IRoleAndUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RoleAndUserServiceImpl implements IRoleAndUserService {
	
	@Autowired
	private RoleAndUserDao roleAndUserDao;
	
	
	@Transactional(value = "transactionManager")
	public void removeRoleAndUser(String roleId, List<String> userIdsList) {
		for(String userId:userIdsList) {
			SysRoleToUserEntity entity=new SysRoleToUserEntity();
			entity.setRoleId(roleId);
			entity.setUserId(userId);
			roleAndUserDao.removeRoleAndUser(entity);
		}
	}
	
	@Transactional(value = "transactionManager")
	public void addRoleAndUser(String roleId,List<String> userIdsList) {
		for(String userId:userIdsList) {
			SysRoleToUserEntity entity=new SysRoleToUserEntity();
			entity.setRoleId(roleId);
			entity.setUserId(userId);
			roleAndUserDao.insertRoleAndUser(entity);
		}

	}

}
