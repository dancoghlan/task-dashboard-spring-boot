package com.dancoghlan.taskwebapp.service;

import com.dancoghlan.taskwebapp.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends PersitenceService<User>, UserDetailsService {

    User getByUsername(String username);
}
