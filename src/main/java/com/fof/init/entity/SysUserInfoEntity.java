package com.fof.init.entity;

import com.fof.common.entity.BaseNoIdEntity;
import com.fof.common.util.Constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
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
    /**用户状态*/
    @Column(name="STATE",columnDefinition="varchar(225)")
    private String state;
    /**表示帐号是否未过期*/
    @Column(name="ACCOUNTNONEXPIRED",columnDefinition="char")
    private boolean accountNonExpired;
    /**表示帐号是否未锁定*/
    @Column(name="ACCOUNTNONLOCKED",columnDefinition="char")
    private boolean accountNonLocked;
    /**表示登录凭据是否未过期*/
    @Column(name="CREDENTIALSNONEXPIRED",columnDefinition="char")
    private boolean credentialsNonExpired;
    /**用户可用状态*/
    @Column(name="ENABLED",columnDefinition="char")
    private boolean enabled;
    @Transient
    private String stateText;
    @Transient
    private String accountNonExpiredText;
    @Transient
    private String accountNonLockedText;
    @Transient
    private String credentialsNonExpiredText;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateText() {
        return stateText;
    }

    public void setStateText(String stateText) {
        this.stateText = stateText;
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

    public boolean isAccountNonExpired() {
        return accountNonExpired ? true:false;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked? true:false;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired ? true:false;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled ? true:false;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAccountNonExpiredText() {
        return accountNonExpiredText;
    }

    public void setAccountNonExpiredText(String accountNonExpiredText) {
        this.accountNonExpiredText = accountNonExpiredText;
    }

    public String getAccountNonLockedText() {
        return accountNonLockedText;
    }

    public void setAccountNonLockedText(String accountNonLockedText) {
        this.accountNonLockedText = accountNonLockedText;
    }

    public String getCredentialsNonExpiredText() {
        return credentialsNonExpiredText;
    }

    public void setCredentialsNonExpiredText(String credentialsNonExpiredText) {
        this.credentialsNonExpiredText = credentialsNonExpiredText;
    }
}
