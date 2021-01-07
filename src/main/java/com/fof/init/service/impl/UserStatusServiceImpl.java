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

	public  String[] initSorter(String sorter) {
		String sorterRule = StringUtils.defaultIfBlank(sorter.equals("")?"":(sorter.split("=")[1].equals("descend")?"DESC":"ASC"),"DESC");
		//String sorterRule ="DESC";
		//String sorterField ="CREATE_TIME";
		String sorterField = StringUtils.defaultIfBlank(sorter.equals("")?"":sorter.split("=")[0],"CREATE_TIME");
		return new String[] {sorterRule,sorterField};
	}
}
