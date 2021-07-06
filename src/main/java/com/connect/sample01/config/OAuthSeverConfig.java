package com.connect.sample01.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import com.connect.sample01.service.MongoClientDetailsService;
@Configuration
@EnableAuthorizationServer
public class OAuthSeverConfig extends AuthorizationServerConfigurerAdapter {

	private String clientId="mohanty";
	private String secret="mohanty123";
	private String[] authorizedGrantTypes = { "password", "client_credentials" };
	private String[] scopes = { "read", "write" };
	private String resourceIds = "core-resource";
	@Autowired
	private TokenStore tokenStore;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private PasswordEncoder passwordEncoder;
	public OAuthSeverConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
	@Autowired
	private MongoClientDetailsService mongoClientDetailsService;
	@Bean
	public TokenStore tokenStore() {
	    return new InMemoryTokenStore();
	}
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager).approvalStoreDisabled().tokenStore(tokenStore);
	}


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		/*
		 * clients.inMemory() .withClient("mohanty")
		 * .secret(passwordEncoder.encode("mohanty123"))
		 * .authorizedGrantTypes("client_credentials", "password") .scopes("read",
		 * "write") .accessTokenValiditySeconds(3600)
		 * .refreshTokenValiditySeconds(50000) .resourceIds("sample-oauth") .and()
		 * .withClient("ajay") .secret(passwordEncoder.encode("ajay123"))
		 * .authorizedGrantTypes("client_credentials", "password") .scopes("read")
		 * .accessTokenValiditySeconds(3600) .resourceIds("sample-oauth");
		 */
    	System.out.println("Configure Client Service configure===============");
    	clients.withClientDetails(mongoClientDetailsService);
    }
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("isAuthenticated()").allowFormAuthenticationForClients();
	}

}
