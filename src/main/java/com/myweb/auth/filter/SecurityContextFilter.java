package com.myweb.auth.filter;

import com.myweb.auth.dao.UserDAO;
import com.myweb.auth.dao.UserPermissionDAO;
import com.myweb.auth.entity.User;
import com.myweb.auth.entity.UserPermission;
import com.myweb.auth.security.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sun.security.util.SecurityConstants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SecurityContextFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private UserPermissionDAO userPermissionDAO;

    @Autowired
    private UserDAO userDAO;

    private final static String USERNAME = new String("username");

    private Authentication authentication;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String username = request.getHeader(USERNAME);

        UserContext userContext = new UserContext();
        userContext.setAuthorities(getAuthoritiesForCurrentUser(username));
        userContext.setUsername(username);

        if (SecurityContextHolder.getContext() != null) {
            SecurityContextHolder.clearContext();
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userContext,
                null, userContext.getAuthorities());
        userContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(userContext);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private List<SimpleGrantedAuthority> getAuthoritiesForCurrentUser(String username) {
        User user = userDAO.selectByUsername(username);
        UUID userId = user.getUserId();
        List<UserPermission> userPermissionList = userPermissionDAO.selectByUserId(userId);
        List<SimpleGrantedAuthority> grantedAuthorityList = new ArrayList<>();
        userPermissionList.stream().
                map(item -> new SimpleGrantedAuthority(item.getPermission())).forEachOrdered(grantedAuthorityList::add);
        return grantedAuthorityList;
    }

}
