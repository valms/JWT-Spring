package com.crosoften.controllers;


import com.crosoften.exception.BadRequestException;
import com.crosoften.exception.ResourceNotFoundException;
import com.crosoften.models.Channel;
import com.crosoften.models.FavoriteChannel;
import com.crosoften.models.Profile;
import com.crosoften.models.ProfileChannel;
import com.crosoften.payload.request.ChannelRequest;
import com.crosoften.payload.request.FavoriteChannelRequest;
import com.crosoften.payload.response.ApiResponse;
import com.crosoften.payload.response.ChannelResponse;
import com.crosoften.repositories.ChannelRepository;
import com.crosoften.repositories.FavoriteChannelRepository;
import com.crosoften.repositories.ProfileChannelRepository;
import com.crosoften.repositories.ProfileRepository;
import com.crosoften.security.CurrentLoggedUser;
import com.crosoften.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/channel/")
public class ChannelController {
	
	private final ChannelRepository channelRepository;
	private final ProfileRepository profileRepository;
	private final FavoriteChannelRepository favoriteChannelRepository;
	private final ProfileChannelRepository profileChannelRepository;
	
	@Autowired
	public ChannelController(ChannelRepository channelRepository, ProfileRepository profileRepository, ProfileChannelRepository profileChannelRepository, FavoriteChannelRepository favoriteChanelRepository) {
		this.channelRepository = channelRepository;
		this.profileRepository = profileRepository;
		this.profileChannelRepository = profileChannelRepository;
		this.favoriteChannelRepository = favoriteChanelRepository;
	}
	
	@PostMapping("/create_channel")
	public ResponseEntity<?> createChannel(@CurrentLoggedUser UserPrincipal currentUser, @Valid @RequestBody ChannelRequest channelRequest) {
		try {
			Optional<Profile> profile = getProfile( currentUser );
			
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
		} catch (Exception e) {
			return new ResponseEntity<>( new ApiResponse( false, "Erro ao criar o canal: " + e.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR );
		}
		
	}
	
	@GetMapping("/list_channels")
	public List<ChannelResponse> listAllChannels() {
		
		List<Channel> channels = this.channelRepository.findAll( new Sort( Sort.Direction.DESC, "createdAt" ) );
		List<ChannelResponse> channelResponses = new ArrayList<>();
		
		if (channels.isEmpty())
			throw new BadRequestException( "Não existem canais" );
		
		for (Channel channel : channels) {
			ChannelResponse channelResponse = new ChannelResponse( channel.getId(), channel.getName(), channel.getDescription(), channel.isOpen() );
			channelResponses.add( channelResponse );
		}
		
		return channelResponses;
	}
	
	@PostMapping("subscribe_channel")
	public ResponseEntity<?> subscribeAtChannel() {
		return null;
	}
	
	@PostMapping("/favorite_channel")
	public ResponseEntity<?> favoriteChannel(@CurrentLoggedUser UserPrincipal loggedUser, @Valid @RequestBody FavoriteChannelRequest favoriteChannelRequest) {
		try {
			Optional<Profile> profile = getProfile( loggedUser );
			Optional<Channel> channel = Optional.of( this.channelRepository.findById( favoriteChannelRequest.getChannelId() ).orElseThrow( () -> new ResourceNotFoundException( "Canal", "id", favoriteChannelRequest.getChannelId() ) ) );
			
			FavoriteChannel favoriteChannel = new FavoriteChannel();
			favoriteChannel.setChannel( channel.get() );
			favoriteChannel.setProfile( profile.get() );
			
			this.favoriteChannelRepository.save( favoriteChannel );
			
			return new ResponseEntity<>( new ApiResponse( true, "Canal Favoritado" ), HttpStatus.OK );
		} catch (Exception e) {
			return new ResponseEntity<>( new ApiResponse( false, e.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR );
		}
	}
	
	@GetMapping("/my_favorite_channels")
	public List<ChannelResponse> listFavoriteChannels(@CurrentLoggedUser UserPrincipal userPrincipal) {
		
		List<FavoriteChannel> favoriteChannels = this.favoriteChannelRepository.findAllByProfileId( getProfile( userPrincipal ).get().getId() );
		
		//TODO: throw
		for (FavoriteChannel favoriteChannel : favoriteChannels) {
		
		}
		return null;
	}
	
	
	private Optional<Profile> getProfile(UserPrincipal loggedUser) {
		return Optional.of( this.profileRepository.findByUserId( loggedUser.getId() ).orElseThrow( () -> new ResourceNotFoundException( "Usuário", "e-mail", loggedUser.getId() ) ) );
	}
	
	
}
