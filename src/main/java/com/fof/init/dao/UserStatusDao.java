package com.fof.init.dao;

import com.fof.init.entity.SysUserInfoEntity;
import com.fof.init.entity.SysUserStatusEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @className: UserStatusDao
 * @author: jun
 * @date: 2021-01-07 13:13
 * @Depiction: 用户状态
 **/
public interface UserStatusDao {

    public Integer insert(SysUserStatusEntity entity);

    public Integer delete(@Param(value = "deleter") String deleter,@Param(value = "ids") String[] ids);
}
