package com.fof.component.security.config;

import com.fof.component.security.handle.*;

import com.fof.init.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * Security配置文件，项目启动时就加载了
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //登录成功处理逻辑
    @Autowired
    CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;

    //登录失败处理逻辑
    @Autowired
    CustomizeAuthenticationFailureHandler authenticationFailureHandler;

    //登出成功处理逻辑
    @Autowired
    CustomizeLogoutSuccessHandler logoutSuccessHandler;

    //权限拒绝处理逻辑
    @Autowired
    CustomizeAccessDeniedHandler accessDeniedHandler;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //匿名用户访问无权限资源时的异常
    @Autowired
    CustomizeAuthenticationEntryPoint authenticationEntryPoint;

    //会话失效(账号被挤下线)处理逻辑
    @Autowired
    CustomizeSessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private CustomizeAbstractSecurityInterceptor securityInterceptor;

    //访问决策管理器
    @Autowired
    CustomizeAccessDecisionManager accessDecisionManager;

    //实现权限拦截
    @Autowired
    CustomizeFilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    private CustomizeInvalidSessionStrategyHandler invalidSessionStrategy;

    @Autowired
    private UserDetailsService userDetailsService;




    protected void configure(HttpSecurity http) throws Exception {
        //允许跨域访问
        http.cors().and().csrf().disable();
        http.
                authorizeRequests().
                withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                o.setAccessDecisionManager(accessDecisionManager);//决策管理器
                o.setSecurityMetadataSource(securityMetadataSource);//安全元数据源
                return o;
            }
        }).
        and().
        authenticationProvider(authenticationProvider()).
        formLogin().usernameParameter("userName").passwordParameter("passWord").
        loginPage("http://localhost:8000/user/login").loginProcessingUrl("/login").//loginPage:登录页面;loginProcessingUrl:登录地址重命名
        permitAll().
        successHandler(authenticationSuccessHandler).//登录成功处理逻辑
        failureHandler(authenticationFailureHandler).//登录失败处理逻辑
        and().
        logout().  //登出
        logoutUrl("/logout").
        permitAll().//允许所有用户
        logoutSuccessHandler(logoutSuccessHandler).//登出成功处理逻辑
        deleteCookies("JSESSIONID").// 登出之后删除cookie
        and().exceptionHandling().//异常处理(权限拒绝、登录失效等)
        accessDeniedHandler(accessDeniedHandler).//权限拒绝处理逻辑
        authenticationEntryPoint(authenticationEntryPoint).//匿名用户访问无权限资源时的异常处理
        and().sessionManagement().// 会话管理
        invalidSessionStrategy(invalidSessionStrategy).
        maximumSessions(1).//同一账号同时登录最大用户数
        expiredSessionStrategy(sessionInformationExpiredStrategy);//会话失效(账号被挤下线)处理逻辑
        http.addFilterBefore(securityInterceptor, FilterSecurityInterceptor.class);
        // 基于Token不需要session
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 禁用缓存
        http.headers().cacheControl();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        //对默认的UserDetailsService进行覆盖
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
