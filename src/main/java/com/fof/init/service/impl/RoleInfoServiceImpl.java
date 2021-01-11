package com.fof.init.service.impl;

import com.fof.common.util.CommonUtil;
import com.fof.init.dao.RoleInfoDao;
import com.fof.init.entity.SysRoleInfoEntity;
import com.fof.init.service.IRoleInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class RoleInfoServiceImpl implements IRoleInfoService {

	@Autowired
	private RoleInfoDao roleInfoDao;

	public List<SysRoleInfoEntity> getAll(Map<String, Object> map, String sorter) {
		String[] sorterParams = CommonUtil.initSorter(sorter);
		map.put("sortType", sorterParams[0]);
		map.put("sortField", sorterParams[1]);
		return roleInfoDao.getAll(map);
	}

	public Integer getCount(Map<String, Object> map) {
		return roleInfoDao.getCount(map);
	}


	public Integer insert(SysRoleInfoEntity entity) {
		return roleInfoDao.insert(entity);
	}

	public Integer update(SysRoleInfoEntity entity) {
		return roleInfoDao.update(entity);
	}

	@Transactional(value = "transactionManager")
	public Integer delete(String deleter,List<String> idList) {

		/**删除角色信息*/
		return roleInfoDao.delete(deleter,idList);
	}


	public boolean checkCode(SysRoleInfoEntity entity) {
		if(roleInfoDao.checkCode(entity)>0) {
			return false;
		}else {
			return true;
		}
	}

	public SysRoleInfoEntity getById(String id) {
		return roleInfoDao.getById(id);
	}

}
