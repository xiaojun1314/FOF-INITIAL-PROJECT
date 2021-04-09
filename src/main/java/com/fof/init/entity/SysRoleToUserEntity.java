package com.fof.init.entity;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @className: SysRoleToUserEntity
 * @author: jun
 * @date: 2021-03-27 09:26
 * @Depiction:
 **/
public class SysRoleToUserEntity {

    private String roleId;
    /**用户ID*/

    private String userId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
