package com.fof.init.service;

import java.util.List;

public interface IRoleAndUserService {

	public void removeRoleAndUser(String roleId,List<String> userIdsList);
	
	public void addRoleAndUser(String roleId,List<String> userIdsList);

}
