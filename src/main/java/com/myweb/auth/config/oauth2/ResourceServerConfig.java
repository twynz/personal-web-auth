package com.myweb.auth.config.oauth2;

import com.myweb.auth.filter.SecurityContextFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true) //should add this annotation, for letting @preAuthorize works.
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .requestMatchers().antMatchers("/**")
                .and().authorizeRequests()
                .antMatchers("/auth/**","/health/**","/health","/info","/api/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(initSecurityContextFilter(), AnonymousAuthenticationFilter.class);
    }

    @Bean
    public SecurityContextFilter initSecurityContextFilter() throws Exception {
        SecurityContextFilter securityContextFilter = new SecurityContextFilter();
        securityContextFilter.setAuthenticationManager(authenticationManager);
        return securityContextFilter;
    }

}
