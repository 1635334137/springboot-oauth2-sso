package org.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 测试
 *
 * @author Mr.Lan
 * @create: 2022-01-18 20:40
 */
//@Configuration
public class Test01 extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //基于角色控制，只有ROLE_xx才可以访问/abc资源，ROLE_是必须的
                .antMatchers("/abc").hasRole("ROLE_xx")
                //基于权限控制，只有有read权限的用户才可以访问/bbb资源
                .antMatchers("/bbb").hasAuthority("read")
                .antMatchers("xx").permitAll()
                .anyRequest().authenticated();
        http.exceptionHandling().accessDeniedHandler(new MyAccessDeniedHandler());
    }
}
