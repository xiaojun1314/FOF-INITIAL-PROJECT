package com.fof.common.bean;

import com.fof.init.entity.SysUserInfoEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @className: SecurityUserInfo
 * @author: jun
 * @date: 2021-01-04 14:20
 * @Depiction:
 **/
public class SecurityUserInfo implements Serializable, UserDetails {

    private String id;

    private String username;

    private String password;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    private List<GrantedAuthority> authorities;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    //返回的值不能为null,否则返回的永远是null,就会一直没有权限，由此定义了一个authorities 属性并提供get方法，因为自
    // 定义了UserDetails，就没有在UserService中，使用到框架提供的User对象
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //不能返回null
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public boolean isAccountNonExpired() {
        return accountNonExpired ? true : false;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked ? true : false;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired ? true : false;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled ? true : false;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}


