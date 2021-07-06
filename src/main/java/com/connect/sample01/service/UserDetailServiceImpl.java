package com.connect.sample01.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.connect.sample01.models.User;
@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService{
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	Map<String, User> users;
	public UserDetailServiceImpl() {
		/*
		 * users=new HashMap<>(); User user=new User("surjeet", "surjeet123", new
		 * String[] {"USER"}); users.put(user.getUsername(), user);
		 */
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Loading username from db===========");
		/*
		 * if(!users.containsKey(username)) throw new
		 * UsernameNotFoundException("Username not found!"); User
		 * user=users.get(username); UserBuilder userBuilder=null; userBuilder=
		 * org.springframework.security.core.userdetails.User.withUsername(user.
		 * getUsername());
		 * userBuilder.password(passwordEncoder.encode(user.getPassword()));
		 * userBuilder.roles(user.getRoles());
		 */
		Query query=new Query();
		query.addCriteria(Criteria.where("username").is(username));
		User user=mongoTemplate.find(query, User.class, "User").stream().findFirst().get();
		if(user==null)
			throw new UsernameNotFoundException("Username not found");
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapToAuthorities(user.getRoles()));
		
	}

	private Collection<? extends GrantedAuthority> mapToAuthorities(String[] roles) {
		List<SimpleGrantedAuthority> list= Stream.of(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		
		return list;
	}

	
}
