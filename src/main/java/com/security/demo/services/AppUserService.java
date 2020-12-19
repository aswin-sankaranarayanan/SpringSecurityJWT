package com.security.demo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.security.demo.config.JWTAuthenticationFilter;
import com.security.demo.entity.User;
import com.security.demo.repository.UserRepository;

@Service
public class AppUserService {

	private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	public User signup(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public User loadByEmailId(String emailId){
		logger.info("Incoming Email Id",emailId);
		return userRepository.findByEmailId(emailId);
	}
	
}
