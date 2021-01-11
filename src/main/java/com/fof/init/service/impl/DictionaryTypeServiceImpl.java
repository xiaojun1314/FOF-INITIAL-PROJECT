package com.fof.init.service.impl;

import com.fof.common.util.CommonUtil;
import com.fof.init.dao.DictionaryInfoDao;
import com.fof.init.dao.DictionaryTypeDao;
import com.fof.init.entity.SysDictionaryTypeEntity;
import com.fof.init.service.IDictionaryTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class DictionaryTypeServiceImpl implements IDictionaryTypeService {
	@Autowired
	private DictionaryTypeDao dictionaryTypeDao;
	@Autowired
	private DictionaryInfoDao dictionaryInfoDao;
	
	public List<SysDictionaryTypeEntity> getAll(Map<String,Object> map, String sorter) {
		String[] sorterParams = CommonUtil.initSorter(sorter);
		map.put("sortType", sorterParams[0]);
		map.put("sortField", sorterParams[1]);
		return dictionaryTypeDao.getAll(map);
	}
	
	public Integer getCount(Map<String, Object> map) {
		return dictionaryTypeDao.getCount(map);
	}
	
	public SysDictionaryTypeEntity findById(String id) {
		return dictionaryTypeDao.findById(id);
	}

	public Integer insert(SysDictionaryTypeEntity entity) {
		return dictionaryTypeDao.insert(entity);
	}

	public Integer update(SysDictionaryTypeEntity entity) {
		return dictionaryTypeDao.update(entity);
	}

	@Transactional(value = "transactionManager")
	public Integer delete(String deleter,List<String> idsList) {
		       dictionaryInfoDao.deleteByForeignId(deleter,idsList);
		return dictionaryTypeDao.delete(deleter,idsList);
	}
	
	public boolean  checkCode(SysDictionaryTypeEntity entity) {
		if(dictionaryTypeDao.checkCode(entity)>0) {
			return false;
		}else {
			return true;
		}
	}
}
