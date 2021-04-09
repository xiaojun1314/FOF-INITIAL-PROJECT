package com.fof.init.service.impl;

import com.fof.common.util.CommonUtil;
import com.fof.init.dao.CompanyInfoDao;
import com.fof.init.entity.SysCompanyEntity;
import com.fof.init.service.ICompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @className: CompanyInfoServiceImpl
 * @author: jun
 * @date: 2021-01-16 11:21
 * @Depiction:
 **/
@Service
public class CompanyInfoServiceImpl implements ICompanyInfoService {
    @Autowired
    private CompanyInfoDao companyInfoDao;
    /**查询菜单信息*/
    public List<SysCompanyEntity> getAll(Map<String,Object> searchParams,String sorter) {

        String[] sorterParams = CommonUtil.initSorter(sorter);
        searchParams.put("sortType", sorterParams[0]);
        searchParams.put("sortField", sorterParams[1]);
        return companyInfoDao.getAll(searchParams);
    }

    public SysCompanyEntity findById(String id) {
        return companyInfoDao.findById(id);
    }

    public Integer insert(SysCompanyEntity entity) {
        entity.setCreater(CommonUtil.getSecurityUserInfo().getId());
        return companyInfoDao.insert(entity);
    }

    public Integer update(SysCompanyEntity entity) {
        entity.setUpdater(CommonUtil.getSecurityUserInfo().getId());
        return companyInfoDao.update(entity);
    }

    public boolean  checkCode(SysCompanyEntity entity) {
        if(companyInfoDao.checkCode(entity)>0) {
            return false;
        }else {
            return true;
        }
    }
}
