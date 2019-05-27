package com.myweb.auth.security;

import com.myweb.auth.dao.UserDAO;
import com.myweb.auth.dao.UserPermissionDAO;
import com.myweb.auth.entity.User;
import com.myweb.auth.entity.UserPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserPermissionDAO userPermissionDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.selectByUsername(username);
        List<SimpleGrantedAuthority> list = getAuthoritiesList(user);
        UserDetails userDetails = new CustomUserDetails(user.getUsername(),user.getPassword(),user.getUserId().toString(),list);
        return userDetails;
    }

    private List<SimpleGrantedAuthority> getAuthoritiesList(User user) {
        List<UserPermission> userPermissionList = userPermissionDAO.selectByUserId(user.getUserId());
        List<SimpleGrantedAuthority> grantedAuthorityList = new ArrayList<>();
        userPermissionList.stream().
                map(item -> new SimpleGrantedAuthority(item.getPermission())).forEachOrdered(grantedAuthorityList::add);
        return grantedAuthorityList;
    }
}
