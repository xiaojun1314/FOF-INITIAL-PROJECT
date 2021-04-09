package com.fof.init.dao;

import com.fof.init.entity.SysMenuInfoEntity;
import com.fof.init.entity.SysModuleInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @className: ModuleInfoDao
 * @author: jun
 * @date: 2021-04-01 18:54
 * @Depiction: 模块信息
 **/
public interface ModuleInfoDao {

    public List<SysModuleInfoEntity> getAll(Map<String,Object> searchParams);

    public Integer insert(SysModuleInfoEntity entity);

    public Integer update(SysModuleInfoEntity entity);

    public Integer deleteByIdList(@Param("idList") List<String> idList);

    public SysModuleInfoEntity findById(String id);

    List<SysModuleInfoEntity> getLeafModuleById(Map<String,Object> map);

    public List<SysModuleInfoEntity> getAllById(String id);

    public List<SysModuleInfoEntity> getModuleLevelName();

}
