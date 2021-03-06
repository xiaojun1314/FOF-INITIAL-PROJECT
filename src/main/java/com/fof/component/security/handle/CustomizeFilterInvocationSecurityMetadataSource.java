package com.fof.component.security.handle;

import com.fof.common.bean.SecurityUserInfo;
import com.fof.init.dao.ModuleOperationDao;
import com.fof.init.dao.RoleInfoDao;
import com.fof.init.entity.SysRoleInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Hutengfei
 * @Description:
 * @Date Create in 2019/9/3 21:06
 */
@Component
public class CustomizeFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    private FilterInvocationSecurityMetadataSource  superMetadataSource;
    //@Autowired
    //SysPermissionService sysPermissionService;

    @Autowired
    public RoleInfoDao roleInfoDao;

    @Autowired
    public ModuleOperationDao moduleOperationDao;




    private final Map<String,String> urlRoleMap = new HashMap<String,String>(){{
        put("/getUser","ROLE_USER");
    }};
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        SecurityUserInfo SecurityUserInfo=  (SecurityUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("44444"+SecurityUserInfo.getUsername());
        //获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();

        Map<String, Object> searchParams=new HashMap<String, Object>();
        searchParams.put("url",requestUrl);
        int count = moduleOperationDao.getCount(searchParams);
        if(count==0){
          return null;
        }else{
            List<SysRoleInfoEntity> sysRoleInfoList= roleInfoDao.getByOperationUrl(requestUrl);
            String[] attributes= sysRoleInfoList.stream().map(item -> "ROLE_"+item.getCode()).collect(Collectors.toList()).stream().toArray(String[]::new);
            return SecurityConfig.createList(attributes.length>0?attributes:(new String[]{"ROLE_DROP"}));
        }
        // System.out.println("44444"+requestUrl);
        /**
        //查询具体某个接口的权限
        List<SysPermission> permissionList =  new ArrayList<SysPermission>();
        SysPermission sysPermission1=new SysPermission();
        sysPermission1.setId(1);
        sysPermission1.setPermissionCode("create_user");
        sysPermission1.setPermissionName("创建用户");
        permissionList.add(sysPermission1);

        SysPermission sysPermission2=new SysPermission();
        sysPermission2.setId(2);
        sysPermission2.setPermissionCode("query_user");
        sysPermission2.setPermissionName("查看用户");
        permissionList.add(sysPermission2);

        if(permissionList == null || permissionList.size() == 0){
            //请求路径没有配置权限，表明该请求接口可以任意访问
            return null;
        }
        String[] attributes = new String[permissionList.size()];
        for(int i = 0;i<permissionList.size();i++){
            attributes[i] = permissionList.get(i).getPermissionCode();
        }
        return SecurityConfig.createList( );
         */
/*
        for(Map.Entry<String,String> entry:urlRoleMap.entrySet()){
            if(antPathMatcher.match(entry.getKey(),requestUrl)){
                System.out.println("测试:"+entry.getValue());
                return SecurityConfig.createList(entry.getValue());
            }
        }

        List<String> list=new ArrayList<String>();
        String[] attributes = new String[1];
        if(requestUrl.equals("/userManage/removeUserInfo")){
            attributes[0]="ROLE_USER";
        }else{
            attributes[0]="null";
        }
        if(attributes.length>0){

        }
*/

       // attributes[1]="query_user";

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
