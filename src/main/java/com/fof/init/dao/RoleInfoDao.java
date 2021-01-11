package com.fof.init.dao;

import com.fof.init.entity.SysRoleInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoleInfoDao {

    public List<SysRoleInfoEntity> getAll(Map<String,Object> map);

    public Integer getCount(Map<String,Object> map);

    public Integer insert(SysRoleInfoEntity entity);

    public Integer update(SysRoleInfoEntity entity);

    public Integer delete(@Param(value = "deleter") String deleter, @Param("idList") List<String> idList);

    public Integer checkCode(SysRoleInfoEntity entity);

    public SysRoleInfoEntity getById(String id);
}
