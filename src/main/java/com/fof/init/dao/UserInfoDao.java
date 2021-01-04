package com.fof.init.dao;

import com.fof.common.bean.SecurityUserInfo;
import com.fof.init.entity.SysUserInfoEntity;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface UserInfoDao {

	 public SecurityUserInfo getByUserName(String userName);

	 public List<SysUserInfoEntity> getAll(Map<String,Object> map);

	 public Integer getCount(Map<String,Object> map);

	 public Integer delete(@Param(value = "deleter") String deleter,@Param(value = "ids") String[] ids);

	public Integer insert(SysUserInfoEntity entity);

	public Integer update(SysUserInfoEntity entity);

}


