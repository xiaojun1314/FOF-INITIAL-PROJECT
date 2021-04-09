package com.fof.init.service.impl;


import com.fof.common.bean.SecurityUserInfo;
import com.fof.common.util.CommonUtil;
import com.fof.init.dao.RoleAndUserDao;
import com.fof.init.dao.UserInfoDao;
import com.fof.init.dao.UserStatusDao;
import com.fof.init.entity.SysUserInfoEntity;
import com.fof.init.entity.SysUserStatusEntity;
import com.fof.init.service.IUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class UserInfoServiceImpl implements IUserInfoService {

	@Autowired
	private UserInfoDao userInfoDao;

	@Autowired
	private UserStatusDao userStatusDao;

	@Autowired
	private RoleAndUserDao roleAndUserDao;

	public SecurityUserInfo findByUserName(String userName) {
		return userInfoDao.getByUserName(userName);
	}

	public List<SysUserInfoEntity> getAll(Map<String,Object> map,String sorter) {
		String[] sorterParams = CommonUtil.initSorter(sorter);
		map.put("sortType", sorterParams[0]);
		map.put("sortField", sorterParams[1]);
		return userInfoDao.getAll(map);
	}

	public Integer getCount(Map<String, Object> map) {
		return userInfoDao.getCount(map);
	}

	@Transactional(value = "transactionManager")
	public Integer delete(List<String> idList) {
		roleAndUserDao.deleteByUserId(idList);
		userStatusDao.delete(idList);
		return userInfoDao.delete(idList);
	}

	@Transactional(value = "transactionManager")
	public Integer insert(SysUserInfoEntity entity) {
		entity.setPassWord(new BCryptPasswordEncoder().encode(entity.getPassWord()));
		entity.setCreater(CommonUtil.getSecurityUserInfo().getId());
		userInfoDao.insert(entity);
		SysUserStatusEntity sysUserStatusEntity=new SysUserStatusEntity();
		sysUserStatusEntity.setSysUserInfoEntity(entity);
		sysUserStatusEntity.setCreater(entity.getCreater());
		return userStatusDao.insert(sysUserStatusEntity);
	}

	@Transactional(value = "transactionManager")
	public Integer update(SysUserInfoEntity entity) {
		entity.setUpdater(CommonUtil.getSecurityUserInfo().getId());
		return userInfoDao.update(entity);
	}

	public boolean  checkUserName(SysUserInfoEntity entity) {
		if(userInfoDao.checkUserName(entity)>0) {
			return false;
		}else {
			return true;
		}
	}

	public boolean  checkUserCode(SysUserInfoEntity entity) {
		if(userInfoDao.checkUserCode(entity)>0) {
			return false;
		}else {
			return true;
		}
	}

	public SysUserInfoEntity findById(String id) {
		return userInfoDao.findById(id);
	}

	@Transactional(value = "transactionManager")
	public Integer resetPassWord(SysUserInfoEntity entity) {
		entity.setPassWord(new BCryptPasswordEncoder().encode(entity.getPassWord()));
		entity.setUpdater(CommonUtil.getSecurityUserInfo().getId());
		return userInfoDao.resetPassWord(entity);
	}

	public List<SysUserInfoEntity> getAllByRoleId(Map<String, Object> map,String sorter) {
		String[] sorterParams = CommonUtil.initSorter(sorter);
		map.put("sortType", sorterParams[0]);
		map.put("sortField", sorterParams[1]);
		return userInfoDao.getAllByRoleId(map);
	}

	public Integer getCountByRoleId(Map<String, Object> map) {
		return userInfoDao.getCountByRoleId(map);
	}

}
