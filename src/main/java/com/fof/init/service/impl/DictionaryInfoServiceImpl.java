package com.fof.init.service.impl;

import com.fof.common.util.CommonUtil;
import com.fof.init.dao.DictionaryInfoDao;
import com.fof.init.entity.SysDictionaryInfoEntity;
import com.fof.init.service.IDictionaryInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class DictionaryInfoServiceImpl implements IDictionaryInfoService {
	@Autowired
	private DictionaryInfoDao dictionaryInfoDao;
	
	public List<SysDictionaryInfoEntity> getAll(Map<String,Object> map, String sorter) {
		String[] sorterParams = CommonUtil.initSorter(sorter);
		map.put("sortType", sorterParams[0]);
		map.put("sortField", sorterParams[1]);
		return dictionaryInfoDao.getAll(map);
	}
	public Integer getCount(Map<String, Object> map) {
		return dictionaryInfoDao.getCount(map);
	}

	public SysDictionaryInfoEntity findById(String id) {
		return dictionaryInfoDao.findById(id);
	}

	@Transactional(value = "transactionManager")
	public Integer insert(SysDictionaryInfoEntity entity) {
		return dictionaryInfoDao.insert(entity);
	}

	@Transactional(value = "transactionManager")
	public Integer update(SysDictionaryInfoEntity entity) {
		return dictionaryInfoDao.update(entity);
	}

	@Transactional(value = "transactionManager")
	public Integer delete(String deleter,List<String> idsList) {
		return dictionaryInfoDao.delete(deleter,idsList);
	}

    /**验证编码*/
	public boolean  checkCode(SysDictionaryInfoEntity entity) {
		if(dictionaryInfoDao.checkCode(entity)>0) {
			return false;
		}else {
			return true;
		}
	}
}
