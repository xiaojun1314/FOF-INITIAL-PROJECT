package com.fof.init.service;

import com.fof.common.bean.SecurityUserInfo;
import com.fof.init.entity.SysUserInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

public interface IUserInfoService {
	
	public SecurityUserInfo findByUserName(String username);

	public List<SysUserInfoEntity> getAll(Map<String,Object> map, String sorter);

	public Integer getCount(Map<String,Object> map);

	public Integer delete(String deleter,String[] ids);

	public Integer insert(SysUserInfoEntity entity);

	public Integer update(SysUserInfoEntity entity);

	public boolean checkUserName(SysUserInfoEntity entity);

	public SysUserInfoEntity findById(String id);

	public boolean checkUserCode(SysUserInfoEntity entity);

	public Integer resetPassWord(SysUserInfoEntity entity);

}
