package com.sample2oauth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.connect.sample01.models.Oauth_Client_Details;
import com.connect.sample01.models.User;
import com.connect.sample01.repo.UserServiceRepo;

@SpringBootApplication(scanBasePackages = {"com.connect.sample01.config","com.connect.sample01.repo","com.connect.sample01.controller","com.connect.sample01.service"})
public class Sample02OauthApplication implements CommandLineRunner {

	//@Autowired
	private UserServiceRepo userServiceRepo;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(Sample02OauthApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user=new User();
		user.setUsername("surjeet");
		user.setPassword(passwordEncoder.encode("surjeet123"));
		user.setRoles(new String[] {"ADMIN","USER"});
		//userServiceRepo.save(user);
		System.out.println("Saved................");
		//mongoTemplate.save(user);
		mongoTemplate.findAll(User.class, "User").stream().findFirst().get();
		if(user==null)
			System.out.println("User Null============");
		else {
			System.out.println(user.getUsername());
			System.out.println(Arrays.toString(user.getRoles()));
		}
		Oauth_Client_Details client=new Oauth_Client_Details();
		client.setClientId("mohanty");
		client.setClientSecret(passwordEncoder.encode("mohanty123"));
		client.setResourceIds(new String[] {"sample-oauth"});
		client.setAuthorizedGrantTypes(new String[] {"password","client_credentials","refresh_token","authorization_code"});
		client.setRegisteredRedirectUris(new String[] {"http://localhost:8080/orders"});
		client.setScopes(new String[] {"read","write","all"});
		client.setAuthorities(new String[] {"ADMIN","USER"});
		client.setAccessTokenValiditySeconds(20000);
		client.setAdditionalInformation(null);
		client.setRefreshTokenValiditySeconds(7200);
		client.setAutoApproveScopes(new String[] {"true"});
		//mongoTemplate.save(client);
		//Oauth_Client_Details details=mongoTemplate.findAll(Oauth_Client_Details.class, "Oauth_Client_Details").stream().findFirst().get();
		
	}
}
