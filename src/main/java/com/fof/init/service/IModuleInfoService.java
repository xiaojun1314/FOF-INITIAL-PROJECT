package com.fof.init.service;

import com.fof.common.entity.TreeDataModel;
import com.fof.init.entity.SysMenuInfoEntity;
import com.fof.init.entity.SysModuleInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * @className: IModuleInfoService
 * @author: jun
 * @date: 2021-04-01 19:16
 * @Depiction:
 **/
public interface IModuleInfoService {

    public List<TreeDataModel> getAll(Map<String,Object> searchParams);

    public void insert(SysModuleInfoEntity entity);

    public void update(SysModuleInfoEntity entity);

    public void  delete(String id);

    public SysModuleInfoEntity findById(String id);

    List<SysModuleInfoEntity>  getLeafModuleById(Map<String,Object> searchParams);

    public List<SysModuleInfoEntity> getAllById(String id);

    public List<SysModuleInfoEntity> getModuleLevelName();


}
