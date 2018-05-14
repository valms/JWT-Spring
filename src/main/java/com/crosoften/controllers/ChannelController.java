package com.crosoften.controllers;


import com.crosoften.models.Channel;
import com.crosoften.models.Profile;
import com.crosoften.models.ProfileChannel;
import com.crosoften.payload.request.ChannelRequest;
import com.crosoften.payload.response.ApiResponse;
import com.crosoften.repositories.ChannelRepository;
import com.crosoften.repositories.ProfileChannelRepository;
import com.crosoften.repositories.ProfileRepository;
import com.crosoften.security.CurrentLoggedUser;
import com.crosoften.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/channel/")
public class ChannelController {
	
	
	private final ChannelRepository channelRepository;
	private final ProfileRepository profileRepository;
	private final ProfileChannelRepository profileChannelRepository;
	
	@Autowired
	public ChannelController(ChannelRepository channelRepository, ProfileRepository profileRepository, ProfileChannelRepository profileChannelRepository) {
		this.channelRepository = channelRepository;
		this.profileRepository = profileRepository;
		this.profileChannelRepository = profileChannelRepository;
	}
	
	@PostMapping("/create-channel")
	public ResponseEntity<?> createChannel(@CurrentLoggedUser UserPrincipal currentUser, @Valid @RequestBody ChannelRequest channelRequest) {
		try {
			Optional<Profile> profile = this.profileRepository.findByUserId( currentUser.getId() );
			
			if (profile.isPresent()) {
				Channel channel = new Channel();
				
				channel.setOpen( channelRequest.isChannelOpen() );
				channel.setName( channelRequest.getName() );
				channel.setDescription( channelRequest.getDescription() );
				
				Channel result = this.channelRepository.save( channel );
				
				ProfileChannel profileChannel = new ProfileChannel();
				
				profileChannel.setChannel( result );
				profileChannel.setProfile( profile.get() );
				profileChannel.setModerator( true );
				
				this.profileChannelRepository.save( profileChannel );
				
				return new ResponseEntity<>( new ApiResponse( true, "Canal criado com sucesso!" ), HttpStatus.CREATED );
			}
			return new ResponseEntity<>( new ApiResponse( false, "Erro ao criar o canal: Usuário inválido!" ), HttpStatus.BAD_REQUEST );
		} catch (Exception e) {
			return new ResponseEntity<>( new ApiResponse( false, "Erro ao criar o canal: " + e.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR );
		}
		
	}
//
	
}
