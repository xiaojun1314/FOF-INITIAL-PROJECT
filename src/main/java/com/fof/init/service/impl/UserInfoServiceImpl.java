package com.fof.init.service.impl;


import com.fof.init.dao.UserInfoDao;
import com.fof.init.entity.SysUserInfoEntity;
import com.fof.init.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class UserInfoServiceImpl implements IUserInfoService {

	@Autowired
	private UserInfoDao userInfoDao;



	public SysUserInfoEntity findByUserName(String username) {
		return userInfoDao.getByUserName(username);
	}


}
