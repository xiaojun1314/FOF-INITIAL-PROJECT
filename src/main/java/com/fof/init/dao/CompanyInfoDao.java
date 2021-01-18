package com.fof.init.dao;

import com.fof.init.entity.SysCompanyEntity;

import java.util.List;
import java.util.Map;

public interface CompanyInfoDao {
	
	public List<SysCompanyEntity> getAll(Map<String,Object> map);

	public SysCompanyEntity findById(String id);

}
