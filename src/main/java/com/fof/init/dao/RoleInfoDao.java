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

    public Integer deleteIdList(@Param("idList") List<String> idList);

    public Integer checkCode(SysRoleInfoEntity entity);

    public SysRoleInfoEntity getById(String id);

    public List<SysRoleInfoEntity> getByUserId(String userId);

    public List<SysRoleInfoEntity> getByOperationUrl(String url);
}
