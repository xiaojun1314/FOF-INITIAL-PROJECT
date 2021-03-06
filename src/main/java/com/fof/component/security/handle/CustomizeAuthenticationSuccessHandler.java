package com.fof.component.security.handle;

import com.alibaba.fastjson.JSON;

import com.fof.common.bean.JsonResult;
import com.fof.common.bean.SecurityUserInfo;
import com.fof.common.util.ResultTool;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Hutengfei
 * @Description: 登录成功处理逻辑
 * @Date Create in 2019/9/3 15:52
 */
@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //更新用户表上次登录时间、更新人、更新时间等字段
        SecurityUserInfo userDetails = (SecurityUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //此处还可以进行一些处理，比如登录成功之后可能需要返回给前台当前用户有哪些菜单权限，
        //进而前台动态的控制菜单的显示等，具体根据自己的业务需求进行扩展
        //String token = JwtUtils.generateTokenExpireInMinutes(userDetails,prop.getPrivateKey(),15);
        //System.out.println("token"+"------------------>"+token);
        //JwtConfig JwtConfig=new JwtConfig();
        //JwtTokenTool jwtTokenTool=new JwtTokenTool();
        //String tokenCode=jwtTokenTool.createToken(userDetails);

        String token = httpServletRequest.getSession().getId();
        System.out.println(token);

       // httpServletResponse.addHeader("authorization", "Bearer " + tokenCode);
        //返回json数据
        JsonResult result = ResultTool.success();
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(result));
    }
}
