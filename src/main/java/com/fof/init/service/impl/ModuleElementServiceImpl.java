package com.fof.init.service.impl;

import com.fof.common.entity.RoutingMenuTree;
import com.fof.common.util.CommonUtil;
import com.fof.common.util.Constants;
import com.fof.init.dao.AuthorityInfoDao;
import com.fof.init.dao.ModuleElementDao;
import com.fof.init.dao.RoleAndAuthorityDao;
import com.fof.init.dao.RoleInfoDao;
import com.fof.init.entity.SysAuthorityEntity;
import com.fof.init.entity.SysMenuInfoEntity;
import com.fof.init.entity.SysModuleElementEntity;
import com.fof.init.entity.SysRoleInfoEntity;
import com.fof.init.service.IModuleElementService;
import com.fof.init.service.IRoleInfoService;
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
public class ModuleElementServiceImpl implements IModuleElementService {

    @Autowired
    private ModuleElementDao moduleElementDao;

    @Autowired
    private AuthorityInfoDao authorityInfoDao;

    @Autowired
    private RoleAndAuthorityDao roleAndAuthorityDao;

    public List<SysModuleElementEntity> getAll(Map<String, Object> searchParams, String sorter) {
        String[] sorterParams = CommonUtil.initSorter(sorter);
        searchParams.put("sortType", sorterParams[0]);
        searchParams.put("sortField", sorterParams[1]);
        return moduleElementDao.getAll(searchParams);
    }
    public Integer getCount(Map<String, Object> map) {
        return moduleElementDao.getCount(map);
    }

    @Transactional(value = "transactionManager")
    public Integer insert(SysModuleElementEntity entity) {
        String creater= CommonUtil.getSecurityUserInfo().getId();
        SysAuthorityEntity sysAuthorityEntity=new SysAuthorityEntity();
        sysAuthorityEntity.setName(entity.getName());
        /**????????????*/
        sysAuthorityEntity.setType(Constants.AuthorityType.ELEMENT.getValue());
        sysAuthorityEntity.setDescription(entity.getDescription());
        sysAuthorityEntity.setCreater(creater);
        /**??????????????????*/
        authorityInfoDao.insert(sysAuthorityEntity);

        /**????????????????????????*/
        entity.setCreater(creater);
        entity.setAuthority_id(sysAuthorityEntity.getId());
        return moduleElementDao.insert(entity);
    }

    @Transactional(value = "transactionManager")
    public Integer update(SysModuleElementEntity entity) {
        String updater= CommonUtil.getSecurityUserInfo().getId();
        SysAuthorityEntity sysAuthorityEntity=new SysAuthorityEntity();
        sysAuthorityEntity.setId(entity.getAuthority_id());
        sysAuthorityEntity.setName(entity.getName());
        sysAuthorityEntity.setDescription(entity.getName());
        sysAuthorityEntity.setUpdater(updater);
        /**??????????????????*/
        authorityInfoDao.update(sysAuthorityEntity);
        entity.setUpdater(updater);

        return moduleElementDao.update(entity);
    }

    @Transactional(value = "transactionManager")
    public void delete(List<String> idList) {
        List<SysModuleElementEntity> moduleElementList = moduleElementDao.getByIdList(idList);
        //??????ID??????
        List<String> authorityIdList = moduleElementList.stream().map(item -> item.getAuthority_id()).collect(Collectors.toList());
        /*????????????????????????*/
        roleAndAuthorityDao.deleteByAuthorityId(authorityIdList);
        /*????????????????????????*/
        moduleElementDao.deleteByIdList(idList);
        /*?????????????????????*/
        authorityInfoDao.deleteByIdList(authorityIdList);
    }


    public boolean checkCode(SysModuleElementEntity entity) {
        if(moduleElementDao.checkCode(entity)>0) {
            return false;
        }else {
            return true;
        }
    }

    public List<SysModuleElementEntity> getElementByRole(Map<String,Object> map) {

        return moduleElementDao.getElementByRole(map);
    }

    public List<SysModuleElementEntity> getByModuleId(String module_id) {

        return moduleElementDao.getByModuleId(module_id);
    }

    public List<String> getElementByUserId() {
        List<RoutingMenuTree> routingMenuTreeList=new ArrayList<RoutingMenuTree>();
        Map<String, Object> params=new HashMap<String, Object>();
        params.put("userId",CommonUtil.getSecurityUserInfo().getId());
        List<SysModuleElementEntity> moduleElementList= moduleElementDao.getElementByUserId(params);

        List<String> elementCodeList = moduleElementList.stream().map(item -> item.getCode()).collect(Collectors.toList());


        return elementCodeList;
    }
}
