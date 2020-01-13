package com.dancoghlan.taskwebapp.config;

import com.dancoghlan.taskwebapp.repository.UserRepository;
import com.dancoghlan.taskwebapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.context.WebApplicationContext;

@Configuration
@Qualifier("userDetailsService")
public class TestConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new UserServiceImpl(userRepository);
//    }

}
