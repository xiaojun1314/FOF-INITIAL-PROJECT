package com.fof.init.service;

import com.fof.init.entity.SysAuthorityEntity;

import java.util.List;
import java.util.Map;

/**
 * @className: IAuthorityInfoService
 * @author: jun
 * @date: 2021-04-08 10:59
 * @Depiction:
 **/
public interface IAuthorityInfoService {

    List<SysAuthorityEntity> getAll(Map<String,Object> map, String sorter);

    Integer getCount(Map<String,Object> map);

    public SysAuthorityEntity findAuthCount(Map<String, Object> map);
}
