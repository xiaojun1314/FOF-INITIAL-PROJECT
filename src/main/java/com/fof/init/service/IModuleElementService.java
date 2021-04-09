package com.fof.init.service;

import com.fof.common.entity.RoutingMenuTree;
import com.fof.init.entity.SysModuleElementEntity;
import com.fof.init.entity.SysRoleInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * @className: IModuleElementService
 * @author: jun
 * @date: 2021-04-01 20:54
 * @Depiction:
 **/
public interface IModuleElementService {

    public List<SysModuleElementEntity> getAll(Map<String,Object> searchParams, String sorter);

    public Integer getCount(Map<String,Object> searchParams);

    public Integer insert(SysModuleElementEntity entity);

    public Integer update(SysModuleElementEntity entity);

    public void delete(List<String> idsList);

    public boolean checkCode(SysModuleElementEntity entity);

    List<SysModuleElementEntity> getElementByRole(Map<String,Object> searchParams);

    List<SysModuleElementEntity> getByModuleId(String module_id);

    public List<String> getElementByUserId();
}
