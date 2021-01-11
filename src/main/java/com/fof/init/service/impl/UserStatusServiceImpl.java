package com.fof.init.service.impl;
import com.fof.init.dao.UserStatusDao;
import com.fof.init.entity.SysUserStatusEntity;
import com.fof.init.service.IUserStatusService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserStatusServiceImpl implements IUserStatusService {

	@Autowired
	private UserStatusDao userStatusDao;

	@Transactional(value = "transactionManager")
	public Integer insert(SysUserStatusEntity entity) {
		return userStatusDao.insert(entity);
	}

}
