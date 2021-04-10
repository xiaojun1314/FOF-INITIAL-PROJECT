package com.fof.init.service;

import com.fof.init.entity.SysModuleElementEntity;
import com.fof.init.entity.SysModuleOperationEntity;

import java.util.List;
import java.util.Map;

/**
 * @className: IModuleElementService
 * @author: jun
 * @date: 2021-04-01 20:54
 * @Depiction:
 **/
public interface IModuleOperationService {

    public List<SysModuleOperationEntity> getAll(Map<String,Object> searchParams, String sorter);

    public Integer getCount(Map<String,Object> searchParams);

    public Integer insert(SysModuleOperationEntity entity);

    public Integer update(SysModuleOperationEntity entity);

    public void delete(List<String> idsList);

    public boolean checkCode(SysModuleOperationEntity entity);

    List<SysModuleOperationEntity> getOperationByRole(Map<String,Object> searchParams);

    List<SysModuleOperationEntity> getByModuleId(String module_id);

}
