package com.fof.init.dao;

import com.fof.init.entity.SysModuleElementEntity;
import com.fof.init.entity.SysModuleOperationEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @className: ModuleElementDao
 * @author: jun
 * @date: 2021-04-01 20:21
 * @Depiction: 模块元素
 **/
public interface ModuleOperationDao {
    public List<SysModuleOperationEntity> getAll(Map<String,Object> map);

    public Integer getCount(Map<String,Object> map);

    public Integer insert(SysModuleOperationEntity entity);

    public Integer update(SysModuleOperationEntity entity);

    public Integer deleteByIdList(@Param("idList") List<String> idList);

    public Integer checkCode(SysModuleOperationEntity entity);

    public List<SysModuleOperationEntity> getOperationByRole(Map<String,Object> map);

    public List<SysModuleOperationEntity> getByModuleId(String module_id);

    public List<SysModuleOperationEntity> getByIdList(@Param("idList") List<String> idList);

}
