package com.fof.init.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fof.init.dao.*;
import com.fof.init.entity.*;
import com.fof.init.service.IRoleAndAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleAndAuthorityServiceImpl implements IRoleAndAuthorityService {

	@Autowired
	private RoleAndAuthorityDao roleAndAuthorityDao;

	@Autowired
	private MenuInfoDao menuInfoDao;

	@Autowired
	private ModuleElementDao moduleElementDao;

	@Autowired
	private ModuleOperationDao moduleOperationDao;


	@Transactional(value = "transactionManager")
	public void insertMenuToAuthorityByRole(List<String> menuIdList, String role_id) {

		/**先删除菜单与权限对应关系*/
		Map<String, Object> params =new HashMap<String, Object>();
		params.put("role_id", role_id);
		params.put("type", "0");
		/**删除角色权限对应关系 通过 角色ID*/
		roleAndAuthorityDao.deleteRoleAndAuthByRoleId(params);
		/**添加菜单与权限对应关系*/
		if(menuIdList.size()>0) {
			List<SysMenuInfoEntity> sysMenuInfoList=menuInfoDao.findByIdList(menuIdList);
			for(SysMenuInfoEntity sysMenuInfoEntity:sysMenuInfoList) {
				SysRoleToAuthorityEntity sysRoleToAuthorityEntity=new SysRoleToAuthorityEntity();
				sysRoleToAuthorityEntity.setAuthority_id(sysMenuInfoEntity.getAuthority_id());
				sysRoleToAuthorityEntity.setRole_id(role_id);
				roleAndAuthorityDao.insertRoleAndAuth(sysRoleToAuthorityEntity);
			}
		}
	}

	@Transactional(value = "transactionManager")
	public void insertModuleElementToAuthorityByRole(List<String> pageElementIdsList, String role_id) {
		/**先删除菜单与权限对应关系*/
		Map<String, Object> params =new HashMap<String, Object>();
		params.put("role_id", role_id);
		params.put("type", "1");
		/**删除角色权限对应关系 通过 角色ID*/
		roleAndAuthorityDao.deleteRoleAndAuthByRoleId(params);
		/**添加菜单与权限对应关系*/
		if(pageElementIdsList.size()>0) {
			List<SysModuleElementEntity> moduleElementList= moduleElementDao.getByIdList(pageElementIdsList);
			for(SysModuleElementEntity  sysModuleElementEntity:moduleElementList){
				SysRoleToAuthorityEntity sysRoleToAuthorityEntity=new SysRoleToAuthorityEntity();
				sysRoleToAuthorityEntity.setAuthority_id(sysModuleElementEntity.getAuthority_id());
				sysRoleToAuthorityEntity.setRole_id(role_id);
				roleAndAuthorityDao.insertRoleAndAuth(sysRoleToAuthorityEntity);
			}
		}
	}

	@Transactional(value = "transactionManager")
	public void insertModuleOperationToAuthorityByRole(List<String> pageOperationIdsList, String role_id) {
		/**先删除菜单与权限对应关系*/
		Map<String, Object> params =new HashMap<String, Object>();
		params.put("role_id", role_id);
		params.put("type", "2");
		/**删除角色权限对应关系 通过 角色ID*/
		roleAndAuthorityDao.deleteRoleAndAuthByRoleId(params);
		/**添加菜单与权限对应关系*/
		if(pageOperationIdsList.size()>0) {
			List<SysModuleOperationEntity> moduleOperationList= moduleOperationDao.getByIdList(pageOperationIdsList);
			for(SysModuleOperationEntity  sysModuleOperationEntity:moduleOperationList){
				SysRoleToAuthorityEntity sysRoleToAuthorityEntity=new SysRoleToAuthorityEntity();
				sysRoleToAuthorityEntity.setAuthority_id(sysModuleOperationEntity.getAuthority_id());
				sysRoleToAuthorityEntity.setRole_id(role_id);
				roleAndAuthorityDao.insertRoleAndAuth(sysRoleToAuthorityEntity);
			}
		}
	}
}
