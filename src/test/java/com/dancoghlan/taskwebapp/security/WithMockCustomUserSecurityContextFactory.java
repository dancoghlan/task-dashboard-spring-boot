package com.dancoghlan.taskwebapp.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;

/**
 * Required to get security authentication working for controller tests.
 *
 * See See https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#jc-authentication-userdetailsservice
 */
public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser user) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication auth = new UsernamePasswordAuthenticationToken(user.username(), user.password(),
                Arrays.asList(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("USER")));
        context.setAuthentication(auth);
        return context;
    }

}
