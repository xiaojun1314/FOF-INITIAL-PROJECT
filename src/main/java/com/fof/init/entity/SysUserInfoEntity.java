package com.fof.init.entity;

import com.fof.common.entity.BaseNoIdEntity;
import com.fof.common.util.Constants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "SYS_USER_INFO")
public class SysUserInfoEntity extends BaseNoIdEntity {
    /**简称*/
    @Column(name="SIMPLENAME",columnDefinition="varchar(225)")
    private String simpleName;
    /**全名*/
    @Column(name="FULLNAME",columnDefinition="varchar(225)")
    private String fullName;
    /**登录名*/
    @Column(name="USERNAME",columnDefinition="varchar(225)")
    private String userName;
    /**密码*/
    @Column(name="PASSWORD",columnDefinition="varchar(225)")
    private String passWord;
    /**密码盐*/
    @Column(name="SALT",columnDefinition="varchar(225)")
    private String salt;
    /**用户编码*/
    @Column(name="USERCODE",columnDefinition="varchar(225)")
    private String userCode;

    @Transient
    private String oldUserCode;

    @Transient
    private String oldUserName;
    @Transient
    private List<SysRoleInfoEntity> sysRoleInfoList;

    public String getCredentialsSalt(){
        return this.userName+this.salt;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getOldUserName() {
        return oldUserName;
    }

    public void setOldUserName(String oldUserName) {
        this.oldUserName = oldUserName;
    }

    public List<SysRoleInfoEntity> getSysRoleInfoList() {
        return sysRoleInfoList;
    }

    public void setSysRoleInfoList(List<SysRoleInfoEntity> sysRoleInfoList) {
        this.sysRoleInfoList = sysRoleInfoList;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getOldUserCode() {
        return oldUserCode;
    }

    public void setOldUserCode(String oldUserCode) {
        this.oldUserCode = oldUserCode;
    }
}
