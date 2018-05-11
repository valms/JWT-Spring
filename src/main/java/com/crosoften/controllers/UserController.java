package com.crosoften.controllers;


import com.crosoften.exception.ResourceNotFoundException;
import com.crosoften.models.Gender;
import com.crosoften.models.Profile;
import com.crosoften.payload.response.*;
import com.crosoften.repositories.ProfileRepository;
import com.crosoften.repositories.UserRepository;
import com.crosoften.security.CurrentLoggedUser;
import com.crosoften.security.UserPrincipal;
import com.crosoften.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
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
	public UserSummary getCurrentUser(@CurrentLoggedUser UserPrincipal currentUser) {
		Optional<Profile> profile = this.profileRepository.findByUserId( currentUser.getId() );
		
		if (profile.isPresent()) {
			UserSummary userSummary = new UserSummary();
			userSummary.setId( currentUser.getId() );
			userSummary.setEmail( currentUser.getEmail() );
			userSummary.setNickname( profile.get().getNickname() );
			userSummary.setCity( profile.get().getCity() );
			userSummary.setGender( Gender.valueOf( profile.get().getGender().name() ) );
			return userSummary;
		}
		
		//TODO: Tratamento
		return null;
		
	}
	
	@GetMapping("/user/checkNickname")
	public UserIdentityAvailability checkNicknameAvailability(@RequestParam(value = "nickname") String nickname) {
		return new UserIdentityAvailability( !this.profileRepository.existsByNickname( nickname ) );
	}
	
	@GetMapping("/user/checkEmail")
	public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
		return new UserIdentityAvailability( !this.userRepository.existsByEmail( email ) );
	}
	
	@GetMapping("/users/{nickname}")
	public UserProfile getUserProfile(@PathVariable(value = "nickname") String nickname) {
		Optional<Profile> profile = Optional.of( this.profileRepository.findByNickname( nickname ).orElseThrow( () -> new ResourceNotFoundException( "Perfil", "nickname", nickname ) ) );
		
		return new UserProfile( profile.get().getNickname(), profile.get().getUser().getEmail(), profile.get().getUser().getCreatedAt() );
	}
	
	@GetMapping("/users/{nickname}/channels")
	public PagedResponse<ChannelResponse> getChannelsCreatedBy(
		@PathVariable(value = "nickname") String nickname, @CurrentLoggedUser UserPrincipal currentUser, @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page, @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
		
		
		return null;
	}
	
}
