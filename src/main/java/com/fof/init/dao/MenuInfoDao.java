package com.fof.init.dao;

import com.fof.init.entity.SysMenuInfoEntity;
import com.fof.init.entity.SysMenuToAuthorityEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @className: MenuInfoDao
 * @author: jun
 * @date: 2021-03-22 17:54
 * @Depiction:菜单管理
 **/
public interface MenuInfoDao{

    public List<SysMenuInfoEntity> getAll(Map<String,Object> map);

    public Integer insert(SysMenuInfoEntity entity);

    public Integer deleteById(@Param(value = "deleter") String deleter, @Param(value = "id") String id);

    public Integer update(SysMenuInfoEntity entity);

    public SysMenuInfoEntity findById(String id);

    public List<SysMenuInfoEntity> getAllById(String id);

    public List<SysMenuInfoEntity> findByIdList(@Param("idList") List<String> idList);

    public Integer deleteByIdList(@Param("idList") List<String> idList);

    List<SysMenuInfoEntity> getMenuToAuthorityByRoleId(String role_id);

    /**查询所有菜单信息通过子节点*/
    List<SysMenuInfoEntity> getMenuByleafMenuId(@Param("idList") List<String> idList);

    List<SysMenuInfoEntity> getMenuByUserId(Map<String,Object> map);


    List<SysMenuInfoEntity> getLeafMenuByUserId(Map<String,Object> map);


}
