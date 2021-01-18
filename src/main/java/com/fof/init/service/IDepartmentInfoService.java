package com.fof.init.service;

import com.fof.init.entity.SysDepartmentEntity;

import java.util.List;
import java.util.Map;

/**
 * @className: IDepartmentInfoService
 * @author: jun
 * @date: 2021-01-16 14:05
 * @Depiction:
 **/
public interface IDepartmentInfoService {
    List<SysDepartmentEntity> getAll(Map<String,Object> searchParams, String sorter);

    public SysDepartmentEntity findById(String id);
}
