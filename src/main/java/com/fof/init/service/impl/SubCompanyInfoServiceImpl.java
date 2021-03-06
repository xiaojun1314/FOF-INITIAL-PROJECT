package com.fof.init.service.impl;

import com.fof.common.util.CommonUtil;
import com.fof.common.util.Constants;
import com.fof.init.dao.DepartmentInfoDao;
import com.fof.init.dao.SubCompanyInfoDao;
import com.fof.init.entity.SysCompanyEntity;
import com.fof.init.entity.SysDepartmentEntity;
import com.fof.init.entity.SysSubCompanyEntity;
import com.fof.init.service.ISubCompanyInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private DepartmentInfoDao departmentInfoDao;
    /**查询菜单信息*/
    public List<SysSubCompanyEntity> getAll(Map<String,Object> searchParams,String sorter) {
        String[] sorterParams = CommonUtil.initSorter(sorter);
        searchParams.put("sortType", sorterParams[0]);
        searchParams.put("sortField", sorterParams[1]);
        return subCompanyInfoDao.getAll(searchParams);
    }

    public Integer getCount(Map<String, Object> map) {
        return subCompanyInfoDao.getCount(map);
    }

    public SysSubCompanyEntity findById(String id) {
        return subCompanyInfoDao.findById(id);
    }

    public Integer insert(SysSubCompanyEntity entity) {
        entity.setCreater(CommonUtil.getSecurityUserInfo().getId());
        return subCompanyInfoDao.insert(entity);
    }

    public Integer update(SysSubCompanyEntity entity) {
        entity.setUpdater(CommonUtil.getSecurityUserInfo().getId());
        return subCompanyInfoDao.update(entity);
    }


    public String getIdsByForeignId(Map<String, Object> searchParams) {
        List<SysSubCompanyEntity>  list= subCompanyInfoDao.getAll(searchParams);

        List<String> idsList = list.stream().map(item -> item.getId()).collect(Collectors.toList());

        String ids= StringUtils.join(idsList, ",");

        return ids;
    }

    public boolean  checkCode(SysSubCompanyEntity entity) {
        if(subCompanyInfoDao.checkCode(entity)>0) {
            return false;
        }else {
            return true;
        }
    }

    @Transactional(value = "transactionManager")
    public void delete(List<String> idList) {
        for(String id :idList){
            Map<String, Object> searchParams=new HashMap<String, Object>();
            /**分部Id*/
            searchParams.put("foreignId",id);
            List<SysDepartmentEntity> sysDepartmentList= departmentInfoDao.findByForeignId(searchParams);
            if(sysDepartmentList.size()>0){
                List<String> departmentIdList = sysDepartmentList.stream().map(item -> item.getId()).collect(Collectors.toList());
                departmentInfoDao.deleteByIdList(departmentIdList);
            }
        }
        subCompanyInfoDao.delete(idList);
    }
}
