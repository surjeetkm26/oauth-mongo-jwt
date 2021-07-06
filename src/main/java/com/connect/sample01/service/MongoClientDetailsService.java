package com.connect.sample01.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import com.connect.sample01.models.Oauth_Client_Details;
@Service("mongoClientDetailsService")
public class MongoClientDetailsService implements ClientDetailsService{

	@Autowired
	private MongoTemplate mongoTemplate;
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		System.out.println("Loading Client By Id======================"+ clientId);
		Query query=new Query();
		query.addCriteria(Criteria.where("clientId").is(clientId));
		Oauth_Client_Details clientDetails= mongoTemplate.find(query, Oauth_Client_Details.class,"Oauth_Client_Details").stream().findFirst().get();
		if(clientDetails==null)
			throw new RuntimeException("ClientDetails is Null:");
		else {
			System.out.println("Not Null..."+ Arrays.toString(clientDetails.getAuthorities()));
		}
		BaseClientDetails base=new BaseClientDetails();
		base.setClientId(clientDetails.getClientId());
		base.setClientSecret(clientDetails.getClientSecret());
		System.out.println("Setting details................"+ base.getClientSecret());
		base.setResourceIds(Arrays.asList(clientDetails.getResourceIds()));
		base.setAuthorizedGrantTypes(Arrays.asList(clientDetails.getAuthorizedGrantTypes()));
		base.setRegisteredRedirectUri(new HashSet(Arrays.asList(clientDetails.getRegisteredRedirectUris())));
		base.setScope(Arrays.asList(clientDetails.getScopes()));
		Collection<GrantedAuthority> auth=Arrays.asList(clientDetails.getScopes()).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		base.setAuthorities(auth);
		base.setAccessTokenValiditySeconds(20000);
		base.setAdditionalInformation(new HashMap<>());
		base.setRefreshTokenValiditySeconds(7200);
		base.setAutoApproveScopes(Arrays.asList(clientDetails.getAutoApproveScopes()));
		return base;
	}

	
}
