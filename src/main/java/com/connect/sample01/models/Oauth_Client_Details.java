package com.connect.sample01.models;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@Document(collection = "Oauth_Client_Details")
@NoArgsConstructor
@ToString
public class Oauth_Client_Details {

	@Id
	private String id;
	private String clientId;
	private String clientSecret;
	private String[] scopes;
	private String[] resourceIds;
	private String[] authorizedGrantTypes;
	private String[] registeredRedirectUris;
	private String[] autoApproveScopes;

	private String[] authorities;
	private Integer accessTokenValiditySeconds;
	private Integer refreshTokenValiditySeconds;
	private Map<String, Object> additionalInformation;

}
