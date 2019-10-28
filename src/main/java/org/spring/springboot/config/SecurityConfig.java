package org.spring.springboot.config;

import org.spring.springboot.domain.User;
import org.spring.springboot.dto.MyUserDetails;
import org.spring.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers(HttpMethod.GET,
                            "/",
                            "/*.html",
                            "/favicon.ico",
                            "/**/*.html",
                            "/**/*.js",
                            "/swagger-resources/**",
                            "/v2/api-docs/**")// 允许对于网站静态资源的无授权访问
        .permitAll().antMatchers("/api/user/login", "/api/user/register").permitAll()// 对登录注册要允许匿名访问
        .antMatchers(HttpMethod.OPTIONS).permitAll()//跨域请求会先进行一次options请求
                .antMatchers("/**").permitAll()//测试时全部运行访问
        .anyRequest().authenticated();// 除上面外的所有请求全部需要鉴权认证
        // 禁用缓存
        httpSecurity.headers().cacheControl();
    }

    @Bean
    public UserDetailsService userDetailsService () {
        return username -> {
            User user = userService.getUserByUserName(username);
            if (user != null) {
                return  new MyUserDetails(user);
            }
            throw new UsernameNotFoundException("用户名或密码错误");
        };
    }
}
