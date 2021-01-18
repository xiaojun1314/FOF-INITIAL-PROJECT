package com.fof.init.service.impl;

import com.fof.common.util.CommonUtil;
import com.fof.init.dao.SubCompanyInfoDao;
import com.fof.init.entity.SysSubCompanyEntity;
import com.fof.init.service.ISubCompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * @className: SubCompanyInfoServiceImpl
 * @author: jun
 * @date: 2021-01-16 11:21
 * @Depiction:
 **/
@Service
public class SubCompanyInfoServiceImpl implements ISubCompanyInfoService {
    @Autowired
    private SubCompanyInfoDao subCompanyInfoDao;
    /**查询菜单信息*/
    public List<SysSubCompanyEntity> getAll(Map<String,Object> searchParams,String sorter) {
        String[] sorterParams = CommonUtil.initSorter(sorter);
        searchParams.put("sortType", sorterParams[0]);
        searchParams.put("sortField", sorterParams[1]);
        return subCompanyInfoDao.getAll(searchParams);
    }
    public SysSubCompanyEntity findById(String id) {
        return subCompanyInfoDao.findById(id);
    }
}
