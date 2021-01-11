package com.fof.init.entity;

import com.fof.common.entity.BaseNoIdEntity;

import javax.persistence.*;

/**
 * @className: SysUserStatusEntity
 * @author: jun
 * @date: 2021-01-04 10:51
 * @Depiction:用户状态
 **/
@Entity
@Table(name = "SYS_USERSTATUS_INFO")
public class SysUserStatusEntity extends BaseNoIdEntity {

    /**表示帐号是否未过期*/
    @Column(name="ACCOUNTNONEXPIRED",columnDefinition="char")
    private boolean accountNonExpired = true;
    /**表示帐号是否未锁定*/
    @Column(name="ACCOUNTNONLOCKED",columnDefinition="char")
    private boolean accountNonLocked = true;
    /**表示登录凭据是否未过期*/
    @Column(name="CREDENTIALSNONEXPIRED",columnDefinition="char")
    private boolean credentialsNonExpired = true;
    /**用户可用状态*/
    @Column(name="ENABLED",columnDefinition="char")
    private boolean enabled = true;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="FOREIGNID",unique=true)
    private SysUserInfoEntity sysUserInfoEntity;


    @Transient
    private String accountNonExpiredText;

    @Transient
    private String accountNonLockedText;

    @Transient
    private String credentialsNonExpiredText;

    @Transient
    private String enabledText;


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

    public SysUserInfoEntity getSysUserInfoEntity() {
        return sysUserInfoEntity;
    }

    public void setSysUserInfoEntity(SysUserInfoEntity sysUserInfoEntity) {
        this.sysUserInfoEntity = sysUserInfoEntity;
    }

    public String getEnabledText() {
        return enabledText;
    }

    public void setEnabledText(String enabledText) {
        this.enabledText = enabledText;
    }
}
