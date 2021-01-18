package com.fof.init.service.impl;

import com.fof.common.util.CommonUtil;
import com.fof.init.dao.DepartmentInfoDao;
import com.fof.init.entity.SysDepartmentEntity;
import com.fof.init.service.IDepartmentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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


}