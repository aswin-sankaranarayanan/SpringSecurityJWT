package com.security.demo.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.demo.entity.User;
import com.security.demo.services.AppUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private AppUserService userService;
	
	
	public UserController(AppUserService userService) {
		super();
		this.userService = userService;
	}


	@PostMapping("/signup")
	public User signup(@RequestBody User user) {
		return userService.signup(user);
	}
}
