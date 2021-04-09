package com.fof.init.dao;

import com.fof.init.entity.SysMenuInfoEntity;
import com.fof.init.entity.SysModuleElementEntity;
import com.fof.init.entity.SysRoleInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @className: ModuleElementDao
 * @author: jun
 * @date: 2021-04-01 20:21
 * @Depiction: 模块元素
 **/
public interface ModuleElementDao {
    public List<SysModuleElementEntity> getAll(Map<String,Object> map);

    public Integer getCount(Map<String,Object> map);

    public Integer insert(SysModuleElementEntity entity);

    public Integer update(SysModuleElementEntity entity);

    public Integer deleteByIdList(@Param("idList") List<String> idList);

    public Integer checkCode(SysModuleElementEntity entity);


    public List<SysModuleElementEntity> getElementByRole(Map<String,Object> map);

    public List<SysModuleElementEntity> getByModuleId(String module_id);

    public List<SysModuleElementEntity> getByIdList(@Param("idList") List<String> idList);

    List<SysModuleElementEntity> getElementByUserId(Map<String,Object> map);
}
