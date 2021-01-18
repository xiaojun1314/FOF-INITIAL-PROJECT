package com.fof.init.service.impl;

import com.fof.common.util.CommonUtil;
import com.fof.init.dao.CompanyInfoDao;
import com.fof.init.entity.SysCompanyEntity;
import com.fof.init.service.ICompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
