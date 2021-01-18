package com.fof.init.dao;

import com.fof.init.entity.SysDepartmentEntity;

import java.util.List;
import java.util.Map;

/**
 * @className: DepartmentInfoDao
 * @author: jun
 * @date: 2021-01-16 13:58
 * @Depiction:
 **/
public interface DepartmentInfoDao {

    public List<SysDepartmentEntity> getAll(Map<String,Object> map);

    public SysDepartmentEntity findById(String id);
}
