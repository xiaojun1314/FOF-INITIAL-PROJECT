package com.fof.init.dao;

import com.fof.init.entity.SysUserInfoEntity;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface UserInfoDao {

	 public SysUserInfoEntity getByUserName(String userName);

	 public List<SysUserInfoEntity> getAll(Map<String,Object> map);

	 public Integer getCount(Map<String,Object> map);

}


