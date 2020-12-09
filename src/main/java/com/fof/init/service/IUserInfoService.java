package com.fof.init.service;

import com.fof.init.entity.SysUserInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

public interface IUserInfoService {
	
	 public SysUserInfoEntity findByUserName(String username);


}
