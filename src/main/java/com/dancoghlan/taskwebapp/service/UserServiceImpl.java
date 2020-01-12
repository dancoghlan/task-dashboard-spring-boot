package com.dancoghlan.taskwebapp.service;

import com.dancoghlan.taskwebapp.entity.Role;
import com.dancoghlan.taskwebapp.entity.User;
import com.dancoghlan.taskwebapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl extends AbstractPersistenceService<User> implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.warn("No user found for username [{}]", username);
            return null;
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    @Override
    public User add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User addedUser = userRepository.save(user);
        logger.info("Saved user [{}]", addedUser);
        return addedUser;
    }

    @Override
    public User getByUsername(String username) {
        logger.info("Retrieving user with username [{}]", username);
        return userRepository.findByUsername(username);
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

}
