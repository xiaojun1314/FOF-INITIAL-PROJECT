package com.fof.init.service.impl;


import com.fof.init.dao.UserInfoDao;
import com.fof.init.entity.SysUserInfoEntity;
import com.fof.init.service.IUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class UserInfoServiceImpl implements IUserInfoService {

	@Autowired
	private UserInfoDao userInfoDao;

	public SysUserInfoEntity findByUserName(String userName) {
		return userInfoDao.getByUserName(userName);
	}

	public List<SysUserInfoEntity> getAll(Map<String,Object> map,String sorter) {
		String[] sorterParams =initSorter(sorter);
		map.put("sortType", sorterParams[0]);
		map.put("sortField", sorterParams[1]);
		return userInfoDao.getAll(map);
	}

	public Integer getCount(Map<String, Object> map) {
		return userInfoDao.getCount(map);
	}

	public  String[] initSorter(String sorter) {
		//String sorterRule = StringUtils.defaultIfBlank(sorter.equals("")?"":(sorter.split("-")[1].equals("descend")?"DESC":"ASC"),"DESC");
		String sorterRule ="DESC";
		String sorterField ="CREATE_TIME";
		//String sorterField = StringUtils.defaultIfBlank(sorter.equals("")?"":sorter.split("-")[0],"CREATE_TIME");
		return new String[] {sorterRule,sorterField};
	}
}
