package com.fof.component.security.service.impl;

import com.fof.init.dao.UserInfoDao;
import com.fof.init.entity.SysRoleInfoEntity;
import com.fof.init.entity.SysUserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserInfoDao userInfoDao;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username"+username);
        SysUserInfoEntity sysUserInfoEntity=new SysUserInfoEntity();
        sysUserInfoEntity.setUserName("admin");
        sysUserInfoEntity.setPassWord("$2a$10$IuDMcc9W0YZ3nfjWk4KV3u0.YURgBPbXjhQ7GDFAOgSMwIQLh1ybC");
        sysUserInfoEntity.setEnabled(true);
        sysUserInfoEntity.setAccountNonExpired(true);
        sysUserInfoEntity.setAccountNonLocked(true);
        sysUserInfoEntity.setCredentialsNonExpired(true);

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_admin");
        GrantedAuthority grantedAuthority1 = new SimpleGrantedAuthority("query_user");
        grantedAuthorities.add(grantedAuthority);
        grantedAuthorities.add(grantedAuthority1);
        return new User(sysUserInfoEntity.getUserName(),sysUserInfoEntity.getPassWord(), sysUserInfoEntity.isAccountNonExpired(), true, true, true, grantedAuthorities);
    }
}
