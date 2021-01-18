package com.fof.init.service;

import com.fof.init.entity.SysCompanyEntity;
import com.fof.init.entity.SysSubCompanyEntity;

import java.util.List;
import java.util.Map;

/**
 * @className: ISubCompanyInfoService
 * @author: jun
 * @date: 2021-01-16 11:20
 * @Depiction:
 **/
public interface ISubCompanyInfoService {
    List<SysSubCompanyEntity> getAll(Map<String,Object> searchParams,String sorter);

    public SysSubCompanyEntity findById(String id);
}
