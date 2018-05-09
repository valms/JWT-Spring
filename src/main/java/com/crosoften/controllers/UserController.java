package com.crosoften.controllers;


import com.crosoften.models.Gender;
import com.crosoften.models.Profile;
import com.crosoften.payload.response.UserSummary;
import com.crosoften.repositories.ProfileRepository;
import com.crosoften.repositories.UserRepository;
import com.crosoften.security.CurrentLoggedUser;
import com.crosoften.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger( UserController.class );
	
	private final UserRepository userRepository;
	private final ProfileRepository profileRepository;
	
	
	public UserController(UserRepository userRepository, ProfileRepository profileRepository) {
		this.userRepository = userRepository;
		this.profileRepository = profileRepository;
	}
	
	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	public UserSummary getCurrenłtUser(@CurrentLoggedUser UserPrincipal currentUser) {
		Optional<Profile> profile = this.profileRepository.findByUserId( currentUser.getId() );
		UserSummary userSummary = new UserSummary();
		userSummary.setId( currentUser.getId() );
		userSummary.setEmail( currentUser.getEmail() );
		userSummary.setNickname( profile.get().getNickname() );
		userSummary.setCity( profile.get().getCity() );
		userSummary.setGender( Gender.valueOf( profile.get().getGender().name() ) );
		
		return userSummary;
	}
}
