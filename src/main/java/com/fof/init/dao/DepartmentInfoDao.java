package com.fof.init.dao;

import com.fof.init.entity.SysDepartmentEntity;
import org.apache.ibatis.annotations.Param;

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

    public List<SysDepartmentEntity> findByForeignId(Map<String,Object> map);

    public Integer insert(SysDepartmentEntity entity);

    public Integer update(SysDepartmentEntity entity);

    public Integer checkCode(SysDepartmentEntity entity);

    public List<SysDepartmentEntity> getAllDepartPart(Map<String,Object> map);

    public Integer getCountDepartPart(Map<String,Object> map);

    public Integer deleteByIdList(@Param("idList") List<String> idList);

}
