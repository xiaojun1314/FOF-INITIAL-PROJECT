package com.fof.init.dao;

import com.fof.common.bean.SecurityUserInfo;
import com.fof.init.entity.SysUserInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Map;

public interface UserInfoDao {

	 public SecurityUserInfo getByUserName(String userName);

	 public List<SysUserInfoEntity> getAll(Map<String,Object> searchParams);

	 public Integer getCount(Map<String,Object> map);

	 public Integer delete(@Param("idList") List<String> idList);

	 public Integer insert(SysUserInfoEntity entity);

	 public Integer update(SysUserInfoEntity entity);

	 public Integer checkUserName(SysUserInfoEntity entity);

	 public SysUserInfoEntity findById(String id);

	 public Integer checkUserCode(SysUserInfoEntity entity);

	 public Integer resetPassWord(SysUserInfoEntity entity);

	public List<SysUserInfoEntity> getAllByRoleId(Map<String,Object> searchParams);

	public Integer getCountByRoleId(Map<String,Object> searchParams);


}


