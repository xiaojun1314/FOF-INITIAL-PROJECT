package com.fof.init.service.impl;

import com.fof.common.util.CommonUtil;
import com.fof.init.dao.AuthorityInfoDao;
import com.fof.init.entity.SysAuthorityEntity;
import com.fof.init.service.IAuthorityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * @className: AuthorityInfoServiceImpl
 * @author: jun
 * @date: 2021-04-08 10:59
 * @Depiction:
 **/
@Service
public class AuthorityInfoServiceImpl implements IAuthorityInfoService {

    @Autowired
    private AuthorityInfoDao authorityInfoDao;

    public List<SysAuthorityEntity> getAll(Map<String,Object> searchParams, String sorter) {
        String[] sorterParams = CommonUtil.initSorter(sorter);
        searchParams.put("sortType", sorterParams[0]);
        searchParams.put("sortField", sorterParams[1]);
        return authorityInfoDao.getAll(searchParams);
    }

    public Integer getCount(Map<String, Object> map) {
        return authorityInfoDao.getCount(map);
    }

    public SysAuthorityEntity findAuthCount(Map<String, Object> map) {
        return authorityInfoDao.findAuthCountInfo(map);
    }


}
