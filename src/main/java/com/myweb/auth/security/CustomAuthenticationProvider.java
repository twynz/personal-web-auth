package com.myweb.auth.security;

import java.util.*;

import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;

import com.myweb.auth.dao.UserDAO;
import com.myweb.auth.dao.UserPermissionDAO;
import com.myweb.auth.entity.User;
import com.myweb.auth.entity.UserPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserPermissionDAO userPermissionDAO;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        //check username and password.
//        User user = checkUserAndReturn(username, password);

        //for testoauth_access_token
        List<SimpleGrantedAuthority> grantedAuthorityList = getUserAuthorities(null);
        CustomUserDetails customUserDetails = new CustomUserDetails(username, password,
                null, grantedAuthorityList);
        return new CustomAuthenticationToken(customUserDetails);
    }

    private User checkUserAndReturn(String username, String password) {
        User user = userDAO.selectByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            throw new BadCredentialsException("Invalid username or password.");
        }

    }

    private List<SimpleGrantedAuthority> getUserAuthorities(User user) {
//        UUID userId = user.getUserId();
//        List<UserPermission> userPermissionList = userPermissionDAO.selectByUserId(userId);
//        List<SimpleGrantedAuthority> grantedAuthorityList = new ArrayList<>();
//        userPermissionList.stream().
//                map(item -> new SimpleGrantedAuthority(item.getPermission())).forEachOrdered(grantedAuthorityList::add);
        List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();
        simpleGrantedAuthorityList.add(new SimpleGrantedAuthority("client_secret_change"));
        return simpleGrantedAuthorityList;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
