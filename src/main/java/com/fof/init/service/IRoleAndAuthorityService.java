package com.fof.init.service;

import java.util.List;

import com.fof.init.entity.SysMenuToAuthorityEntity;

public interface IRoleAndAuthorityService {
	public void insertMenuToAuthorityByRole(List<String> menuIdList,String role_id);

	public void insertModuleElementToAuthorityByRole(List<String> pageElementIdsList,String role_id);

}
