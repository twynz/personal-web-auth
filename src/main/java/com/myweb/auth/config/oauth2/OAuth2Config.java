//package com.myweb.auth.config.oauth2;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.sql.DataSource;
//
//import com.myweb.auth.security.CustomAuthenticationProvider;
//import com.myweb.auth.security.CustomAuthorizationTokenServices;
//import com.myweb.auth.security.CustomUserDetails;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
//import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
//import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
//import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//
//
//@Configuration
//@EnableAuthorizationServer
//public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
//
//    private static final String TOKEN_SEG_TENANT_ID = "X-BWTS-TenantId";
//    private static final String TOKEN_SEG_USER_ID = "X-BWTS-UserId";
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public JdbcClientDetailsService clientDetailsService(DataSource dataSource) {
//        return new JdbcClientDetailsService(dataSource);
//    }
//
//    @Bean
//    public JdbcTokenStore tokenStore(DataSource dataSource) {
//        return new JdbcTokenStore(dataSource);
//    }
//
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.withClientDetails(clientDetailsService(dataSource));
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.authenticationManager(authenticationManager)
//                .tokenStore(tokenStore(dataSource))
//                .tokenServices(authorizationServerTokenServices())
//                .accessTokenConverter(accessTokenConverter());
//    }
//
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new CustomTokenEnhancer();
//        converter.setSigningKey("secret");
//        return converter;
//    }
//
//    protected static class CustomTokenEnhancer extends JwtAccessTokenConverter {
//        @Override
//        public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
//                                         OAuth2Authentication authentication) {
//            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//
//            Map<String, Object> info = new HashMap<String, Object>();
//            info.put(TOKEN_SEG_TENANT_ID, userDetails.getTenantId());
//            info.put(TOKEN_SEG_USER_ID, userDetails.getUserId());
//
//            DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
//            customAccessToken.setAdditionalInformation(info);
//
//            DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken)super
//                    .enhance(customAccessToken, authentication);
//            Map<String, Object> additionalInfo = defaultOAuth2AccessToken.getAdditionalInformation();
//            additionalInfo.remove(TOKEN_SEG_TENANT_ID);
//            additionalInfo.remove(TOKEN_SEG_USER_ID);
//            return defaultOAuth2AccessToken;
//        }
//    }
//
//    @Configuration
//    public static class AuthenticationManagerConfig extends GlobalAuthenticationConfigurerAdapter {
//
//        @Autowired
//        CustomAuthenticationProvider customAuthenticationProvider;
//
//        @Override
//        public void configure(AuthenticationManagerBuilder auth) throws Exception {
//            auth.authenticationProvider(customAuthenticationProvider);
//        }
//
//    }
//
//    @Bean
//    public AuthorizationServerTokenServices authorizationServerTokenServices() {
//        CustomAuthorizationTokenServices customTokenServices = new CustomAuthorizationTokenServices();
//        customTokenServices.setTokenStore(tokenStore(dataSource));
//        customTokenServices.setSupportRefreshToken(true);
//        customTokenServices.setReuseRefreshToken(true);
//        customTokenServices.setClientDetailsService(clientDetailsService(dataSource));
//        customTokenServices.setTokenEnhancer(accessTokenConverter());
//        return customTokenServices;
//    }
//
//}
