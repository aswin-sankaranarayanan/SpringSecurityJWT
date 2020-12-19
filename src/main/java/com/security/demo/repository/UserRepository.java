package com.security.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.security.demo.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long>{
	User findByEmailId(String emailId);
}