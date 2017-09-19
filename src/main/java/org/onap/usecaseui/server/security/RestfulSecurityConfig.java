/*
 * 文 件 名:  RestfulSecurityConfig.java
 * 版    权:  Raisecom Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  donghu
 * 修改时间:  2017年8月21日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package org.onap.usecaseui.server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * @author  donghu
 * @version  [版本号, 2017年8月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
//@Configuration
//@EnableWebSecurity
public class RestfulSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http)
        throws Exception
    {
        // TODO Auto-generated method stub
        super.configure(http);
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
        throws Exception
    {
        // TODO Auto-generated method stub
        super.configure(auth);
    }
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
