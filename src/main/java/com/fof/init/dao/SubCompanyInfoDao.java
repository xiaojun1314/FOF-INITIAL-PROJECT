package com.fof.init.dao;

import com.fof.init.entity.SysSubCompanyEntity;
import java.util.List;
import java.util.Map;

/**
 * @className: SubCompanyInfoDao
 * @author: jun
 * @date: 2021-01-16 13:15
 * @Depiction:
 **/
public interface SubCompanyInfoDao {
    public List<SysSubCompanyEntity> getAll(Map<String,Object> map);

    public SysSubCompanyEntity findById(String id);
}
