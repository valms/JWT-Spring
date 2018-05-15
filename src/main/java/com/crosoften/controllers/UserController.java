package com.crosoften.controllers;


import com.crosoften.exception.BadRequestException;
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

import java.util.*;

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
		Optional<Profile> profile = Optional.of( this.profileRepository.findByUserId( currentUser.getId() ).orElseThrow( () -> new ResourceNotFoundException( "Usuário", "email", currentUser.getEmail() ) ) );
		return new UserSummary( currentUser.getId(), currentUser.getEmail(), profile.get().getNickname(), profile.get().getCity(), Gender.valueOf( profile.get().getGender().name() ) );
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
	public List getChannelsCreatedBy(@CurrentLoggedUser UserPrincipal currentUser) {
		
		List<ChannelResponse> channelResponses = new ArrayList<>();
		List<Channel> channels = this.channelRepository.findAllByCreatedBy( currentUser.getId() );
		
		if (channels.isEmpty()) {
			throw new BadRequestException( "Usuário não tem canais criados" );
		}
		
		for (Channel channel : channels) {
			ChannelResponse channelResponse = new ChannelResponse( channel.getId(), channel.getName(), channel.getDescription(), channel.isOpen() );
			channelResponses.add( channelResponse );
		}
		
		return channelResponses;
		
	}
	
}
