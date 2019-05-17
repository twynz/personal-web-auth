package com.myweb.auth.security;

import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

//    @Autowired
//    private UserClient userClient;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        //authentication logic here, will check username from db.
        Map map = null;
        String tenantId = (String) map.get("tenantId");
        String userId = (String) map.get("userId");

        if (StringUtils.isEmpty(tenantId) || StringUtils.isEmpty(userId)) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(username, password, tenantId, userId);

        return new CustomAuthenticationToken(customUserDetails);
    }

    private Map<String, String> getUserServicePostObject(String username, String password) {
        Map<String, String> requestParam = new HashMap<String, String>();
        requestParam.put("userName", username);
        requestParam.put("password", password);
        return requestParam;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
