package com.fof.init.service;

import com.fof.init.entity.SysCompanyEntity;

import java.util.List;
import java.util.Map;

/**
 * @className: ICompanyInfoService
 * @author: jun
 * @date: 2021-01-16 11:20
 * @Depiction:
 **/
public interface ICompanyInfoService {
    List<SysCompanyEntity> getAll(Map<String,Object> searchParams,String sorter);

    public SysCompanyEntity findById(String id);

    public Integer insert(SysCompanyEntity entity);

    public boolean  checkCode(SysCompanyEntity entity);

    public Integer update(SysCompanyEntity entity);
}
