package com.fof.init.service.impl;

import com.fof.common.bean.SecurityUserInfo;
import com.fof.init.dao.RoleAndUserDao;
import com.fof.init.dao.RoleInfoDao;
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
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private RoleInfoDao roleInfoDao;

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("userName"+userName);
        SecurityUserInfo sysUserInfoEntity = userInfoDao.getByUserName(userName);

        //SecurityUserInfo securityUserInfo=new SecurityUserInfo();
        /**
        System.out.println("username"+username);
        SysUserInfoEntity sysUserInfoEntity=new SysUserInfoEntity();
        sysUserInfoEntity.setUserName("admin");
        sysUserInfoEntity.setPassWord("$2a$10$IuDMcc9W0YZ3nfjWk4KV3u0.YURgBPbXjhQ7GDFAOgSMwIQLh1ybC");
        sysUserInfoEntity.setEnabled(true);
        sysUserInfoEntity.setAccountNonExpired(true);
        sysUserInfoEntity.setAccountNonLocked(true);
        sysUserInfoEntity.setCredentialsNonExpired(true);
*/
        List<SysRoleInfoEntity> sysRoleInfoList= roleInfoDao.getByUserId(sysUserInfoEntity.getId());

        List<String> roleCodeList = sysRoleInfoList.stream().map(item -> "ROLE_"+item.getCode()).collect(Collectors.toList());

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(String roleCode: roleCodeList){
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roleCode);
            grantedAuthorities.add(grantedAuthority);
        }
        sysUserInfoEntity.setAuthorities(grantedAuthorities);

        //GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
        // GrantedAuthority grantedAuthority1 = new SimpleGrantedAuthority("query_user");
        //grantedAuthorities.add(grantedAuthority);
       // grantedAuthorities.add(grantedAuthority1);
       // sysUserInfoEntity.setAuthorities(grantedAuthorities);
       // return new User(sysUserInfoEntity.getUserName(),sysUserInfoEntity.getPassWord(), true, true, true, true, grantedAuthorities);
       return  sysUserInfoEntity;
    }
}
