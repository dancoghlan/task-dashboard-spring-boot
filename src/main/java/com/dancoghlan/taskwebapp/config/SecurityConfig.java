package com.dancoghlan.taskwebapp.config;

import com.dancoghlan.taskwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/app/user/**").hasAuthority("ADMIN")
                .antMatchers("/app/task/**", "/app/home", "/app/logout").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/app/denied", "/app/login").permitAll()
                .and()
                .formLogin()
                .loginPage("/app/login")
                .defaultSuccessUrl("/app/task/list")
                .failureUrl("/app/login")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/app/logout")
                .logoutSuccessUrl("/app/login")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies()
                .and()
                .exceptionHandling().accessDeniedPage("/app/denied");
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/webjars/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

}
