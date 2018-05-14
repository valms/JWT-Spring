package com.crosoften.controllers;


import com.crosoften.exception.ResourceNotFoundException;
import com.crosoften.models.Channel;
import com.crosoften.models.Gender;
import com.crosoften.models.Profile;
import com.crosoften.payload.response.*;
import com.crosoften.repositories.ChannelRepository;
import com.crosoften.repositories.ProfileRepository;
import com.crosoften.repositories.UserRepository;
import com.crosoften.security.CurrentLoggedUser;
import com.crosoften.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger( UserController.class );
	
	private final UserRepository userRepository;
	private final ProfileRepository profileRepository;
	private final ChannelRepository channelRepository;
	
	
	public UserController(UserRepository userRepository, ProfileRepository profileRepository, ChannelRepository channelRepository) {
		this.userRepository = userRepository;
		this.profileRepository = profileRepository;
		this.channelRepository = channelRepository;
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
	
	@GetMapping("/users/my_channels")
	public List<Channel> getChannelsCreatedBy(@CurrentLoggedUser UserPrincipal currentUser) {
//		Optional<Profile> profile = Optional.of( this.profileRepository.findByUserId( currentUser.getId() ).orElseThrow( () -> new ResourceNotFoundException( "Usuário", "e-mail", currentUser.getEmail() ) ) );
//		Optional<Channel> channel = Optional.of(  );
		
		//TOFIX: Retorno como lista
		return Collections.singletonList( this.channelRepository.findByCreatedBy( currentUser.getId() ).orElseThrow( () -> new ResourceNotFoundException( "Canal", "usuário", currentUser.getEmail() ) ) );
	}
	
}
