package com.connect.sample01.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.connect.sample01.models.User;
@Repository("userServiceRepo")
public interface UserServiceRepo extends MongoRepository<User, String>{
	
}
