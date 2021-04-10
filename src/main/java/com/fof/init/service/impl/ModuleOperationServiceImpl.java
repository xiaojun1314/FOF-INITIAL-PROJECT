package com.fof.init.service.impl;

import com.fof.common.entity.RoutingMenuTree;
import com.fof.common.util.CommonUtil;
import com.fof.common.util.Constants;
import com.fof.init.dao.AuthorityInfoDao;
import com.fof.init.dao.ModuleElementDao;
import com.fof.init.dao.ModuleOperationDao;
import com.fof.init.dao.RoleAndAuthorityDao;
import com.fof.init.entity.SysAuthorityEntity;
import com.fof.init.entity.SysModuleElementEntity;
import com.fof.init.entity.SysModuleOperationEntity;
import com.fof.init.service.IModuleElementService;
import com.fof.init.service.IModuleOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @className: ModuleElementServiceImpl
 * @author: jun
 * @date: 2021-04-01 20:56
 * @Depiction:
 **/
@Service
public class ModuleOperationServiceImpl implements IModuleOperationService {

    @Autowired
    private ModuleOperationDao moduleOperationDao;

    @Autowired
    private AuthorityInfoDao authorityInfoDao;

    @Autowired
    private RoleAndAuthorityDao roleAndAuthorityDao;

    public List<SysModuleOperationEntity> getAll(Map<String, Object> searchParams, String sorter) {
        String[] sorterParams = CommonUtil.initSorter(sorter);
        searchParams.put("sortType", sorterParams[0]);
        searchParams.put("sortField", sorterParams[1]);
        return moduleOperationDao.getAll(searchParams);
    }
    public Integer getCount(Map<String, Object> map) {
        return moduleOperationDao.getCount(map);
    }

    @Transactional(value = "transactionManager")
    public Integer insert(SysModuleOperationEntity entity) {
        String creater= CommonUtil.getSecurityUserInfo().getId();
        SysAuthorityEntity sysAuthorityEntity=new SysAuthorityEntity();
        sysAuthorityEntity.setName(entity.getName());
        /**权限类型*/
        sysAuthorityEntity.setType(Constants.AuthorityType.OPERATION.getValue());
        sysAuthorityEntity.setDescription(entity.getDescription());
        sysAuthorityEntity.setCreater(creater);
        /**添加权限信息*/
        authorityInfoDao.insert(sysAuthorityEntity);

        /**添加模板元素信息*/
        entity.setCreater(creater);
        entity.setAuthority_id(sysAuthorityEntity.getId());
        return moduleOperationDao.insert(entity);
    }

    @Transactional(value = "transactionManager")
    public Integer update(SysModuleOperationEntity entity) {
        String updater= CommonUtil.getSecurityUserInfo().getId();
        SysAuthorityEntity sysAuthorityEntity=new SysAuthorityEntity();
        sysAuthorityEntity.setId(entity.getAuthority_id());
        sysAuthorityEntity.setName(entity.getName());
        sysAuthorityEntity.setDescription(entity.getName());
        sysAuthorityEntity.setUpdater(updater);
        /**更新权限信息*/
        authorityInfoDao.update(sysAuthorityEntity);
        entity.setUpdater(updater);

        return moduleOperationDao.update(entity);
    }

    @Transactional(value = "transactionManager")
    public void delete(List<String> idList) {
        List<SysModuleOperationEntity> moduleOperationList = moduleOperationDao.getByIdList(idList);
        //权限ID集合
        List<String> authorityIdList = moduleOperationList.stream().map(item -> item.getAuthority_id()).collect(Collectors.toList());
        /*删除角色权限信息*/
        roleAndAuthorityDao.deleteByAuthorityId(authorityIdList);
        /*删除模块元素信息*/
        moduleOperationDao.deleteByIdList(idList);
        /*删除权限表信息*/
        authorityInfoDao.deleteByIdList(authorityIdList);
    }


    public boolean checkCode(SysModuleOperationEntity entity) {
        if(moduleOperationDao.checkCode(entity)>0) {
            return false;
        }else {
            return true;
        }
    }

    public List<SysModuleOperationEntity> getOperationByRole(Map<String,Object> map) {

        return moduleOperationDao.getOperationByRole(map);
    }

    public List<SysModuleOperationEntity> getByModuleId(String module_id) {

        return moduleOperationDao.getByModuleId(module_id);
    }

}
