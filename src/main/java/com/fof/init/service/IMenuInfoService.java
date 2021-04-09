package com.fof.init.service;

import com.fof.common.entity.RoutingMenuTree;
import com.fof.common.entity.TreeDataModel;
import com.fof.init.entity.SysAuthorityEntity;
import com.fof.init.entity.SysMenuInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * @className: IMenuInfoService
 * @author: jun
 * @date: 2021-03-22 18:07
 * @Depiction:
 **/
public interface IMenuInfoService {

    public List<TreeDataModel> getAll(Map<String,Object> searchParams);

    public SysMenuInfoEntity findById(String id);

    public void update(SysMenuInfoEntity entity);

    public void  deleteById(String id);

    public void insert(SysMenuInfoEntity entity);

    public List<SysMenuInfoEntity> getMenuToAuthorityByRoleId(String role_id);

    public List<RoutingMenuTree> getMenuByUserId();

    public List<RoutingMenuTree> getLeafMenuByUserId();
}
