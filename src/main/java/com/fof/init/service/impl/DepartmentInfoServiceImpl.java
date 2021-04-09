package com.fof.init.service.impl;

import com.fof.common.util.CommonUtil;
import com.fof.init.dao.DepartmentInfoDao;
import com.fof.init.entity.SysDepartmentEntity;
import com.fof.init.service.IDepartmentInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @className: DepartmentInfoServiceImpl
 * @author: jun
 * @date: 2021-01-16 14:07
 * @Depiction:
 **/
@Service
public class DepartmentInfoServiceImpl implements IDepartmentInfoService {

    @Autowired
    private DepartmentInfoDao departmentInfoDao;

    public List<SysDepartmentEntity> getAll(Map<String,Object> searchParams, String sorter) {
        String[] sorterParams = CommonUtil.initSorter(sorter);
        searchParams.put("sortType", sorterParams[0]);
        searchParams.put("sortField", sorterParams[1]);
        return departmentInfoDao.getAll(searchParams);
    }

    public SysDepartmentEntity findById(String id) {
        return departmentInfoDao.findById(id);
    }


    public List<SysDepartmentEntity> findByForeignId(Map<String,Object> searchParams) {
        return departmentInfoDao.findByForeignId(searchParams);
    }

    public Integer insert(SysDepartmentEntity entity) {
        entity.setCreater(CommonUtil.getSecurityUserInfo().getId());
        return departmentInfoDao.insert(entity);
    }

    public Integer update(SysDepartmentEntity entity) {
        entity.setUpdater(CommonUtil.getSecurityUserInfo().getId());
        return departmentInfoDao.update(entity);
    }

    @Transactional(value = "transactionManager")
    public void delete(List<String> idList) {
        departmentInfoDao.deleteByIdList(idList);
    }

    public List<SysDepartmentEntity> getAllDepartPart(Map<String, Object> searchParams,String sorter) {
        String[] sorterParams = CommonUtil.initSorter(sorter);
        searchParams.put("sortType", sorterParams[0]);
        searchParams.put("sortField", sorterParams[1]);
        return departmentInfoDao.getAllDepartPart(searchParams);
    }

    public Integer getCountDepartPart(Map<String, Object> map) {
        return departmentInfoDao.getCountDepartPart(map);
    }

    public boolean  checkCode(SysDepartmentEntity entity) {
        if(departmentInfoDao.checkCode(entity)>0) {
            return false;
        }else {
            return true;
        }
    }

}
