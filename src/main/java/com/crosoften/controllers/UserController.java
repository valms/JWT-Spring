package com.crosoften.controllers;


import com.crosoften.models.auth.User;
import com.crosoften.repositories.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
	
	
	private final UserRepository userRepository;
	
	
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@PostMapping("/user")
	public User createUser(@Valid @RequestBody User user) {
		return this.userRepository.save( user );
	}
	
}
