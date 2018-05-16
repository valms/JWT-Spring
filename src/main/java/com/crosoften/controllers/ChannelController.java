package com.crosoften.controllers;


import com.crosoften.exception.BadRequestException;
import com.crosoften.exception.ResourceNotFoundException;
import com.crosoften.models.*;
import com.crosoften.models.auth.User;
import com.crosoften.payload.request.ChannelRequest;
import com.crosoften.payload.request.FavoriteChannelRequest;
import com.crosoften.payload.response.ApiResponse;
import com.crosoften.payload.response.ChannelResponse;
import com.crosoften.payload.response.UserSummary;
import com.crosoften.repositories.*;
import com.crosoften.security.CurrentLoggedUser;
import com.crosoften.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.metamodel.ListAttribute;
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
	private final WaitlistRepository waitlistRepository;
	
	@Autowired
	public ChannelController(ChannelRepository channelRepository, ProfileRepository profileRepository, ProfileChannelRepository profileChannelRepository, FavoriteChannelRepository favoriteChanelRepository, WaitlistRepository waitlistRepository) {
		this.channelRepository = channelRepository;
		this.profileRepository = profileRepository;
		this.profileChannelRepository = profileChannelRepository;
		this.favoriteChannelRepository = favoriteChanelRepository;
		this.waitlistRepository = waitlistRepository;
	}
	
	@PostMapping("/create_channel")
	public ResponseEntity<?> createChannel(@CurrentLoggedUser UserPrincipal currentUser, @Valid @RequestBody ChannelRequest channelRequest) {
		try {
			Optional<Profile> profile = Optional.of( this.profileRepository.findByUserId( currentUser.getId() ).orElseThrow( () -> new ResourceNotFoundException( "Usuário", "email", currentUser.getEmail() ) ) );
			
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
	public ResponseEntity<?> subscribeAtChannel(@CurrentLoggedUser UserPrincipal loggedUser, @Valid @RequestBody FavoriteChannelRequest favoriteChannelRequest) {
		Optional<Profile> profile = Optional.of( this.profileRepository.findByUserId( loggedUser.getId() ).orElseThrow( () -> new ResourceNotFoundException( "Usuário", "email", loggedUser.getEmail() ) ) );
		Optional<Channel> channel = Optional.of( this.channelRepository.findById( favoriteChannelRequest.getChannelId() ).orElseThrow( () -> new ResourceNotFoundException( "Canal", "id", favoriteChannelRequest.getChannelId() ) ) );
		
		if (this.profileChannelRepository.existsByProfileAndChannel( profile.get(), channel.get() ))
			throw new BadRequestException( "Usuário já faz parte deste canal" );
		else if (this.waitlistRepository.existsByProfileAndChannel( profile.get(), channel.get() ))
			throw new BadRequestException( "Usuário já está na lista de espera para este canal" );
		
		
		if (channel.get().isOpen()) {
			ProfileChannel profileChannel = new ProfileChannel( false, profile.get(), channel.get() );
			this.profileChannelRepository.save( profileChannel );
			return new ResponseEntity<>( new ApiResponse( true, "Entrou no Canal com sucesso!" ), HttpStatus.OK );
		}
		
		
		WaitlistChannel waitlistChannel = new WaitlistChannel( profile.get(), channel.get() );
		this.waitlistRepository.save( waitlistChannel );
		
		return new ResponseEntity<>( new ApiResponse( true, "Solicitação enviada com sucesso!" ), HttpStatus.OK );
	}
	
	@PostMapping("/favorite_channel")
	public ResponseEntity<?> favoriteChannel(@CurrentLoggedUser UserPrincipal loggedUser, @Valid @RequestBody FavoriteChannelRequest favoriteChannelRequest) {
		
		Optional<Profile> profile = Optional.of( this.profileRepository.findByUserId( loggedUser.getId() ).orElseThrow( () -> new ResourceNotFoundException( "Usuário", "email", loggedUser.getEmail() ) ) );
		Optional<Channel> channel = Optional.of( this.channelRepository.findById( favoriteChannelRequest.getChannelId() ).orElseThrow( () -> new ResourceNotFoundException( "Canal", "id", favoriteChannelRequest.getChannelId() ) ) );
		
		if (this.favoriteChannelRepository.existsByProfileAndChannel( profile.get(), channel.get() ))
			throw new BadRequestException( "Canal já favoriado por este usuário!" );
		
		FavoriteChannel favoriteChannel = new FavoriteChannel();
		favoriteChannel.setChannel( channel.get() );
		favoriteChannel.setProfile( profile.get() );
		
		this.favoriteChannelRepository.save( favoriteChannel );
		
		return new ResponseEntity<>( new ApiResponse( true, "Canal Favoritado!" ), HttpStatus.OK );
		
	}
	
	@GetMapping("/my_favorite_channels")
	public List<ChannelResponse> listFavoriteChannels(@CurrentLoggedUser UserPrincipal userPrincipal) {
		Optional<Profile> profile = Optional.of( this.profileRepository.findByUserId( userPrincipal.getId() ).orElseThrow( () -> new ResourceNotFoundException( "Usuário", "email", userPrincipal.getEmail() ) ) );
		
		List<FavoriteChannel> favoriteChannels = this.favoriteChannelRepository.findAllByProfileId( profile.get().getId() );
		List<ChannelResponse> channelResponses = new ArrayList<>();
		
		for (FavoriteChannel favoriteChannel : favoriteChannels) {
			Optional<Channel> channel = Optional.of( this.channelRepository.findById( favoriteChannel.getChannel().getId() ).orElseThrow( () -> new ResourceNotFoundException( "Canal", "id", favoriteChannel.getChannel().getId() ) ) );
			ChannelResponse channelResponse = new ChannelResponse( channel.get().getId(), channel.get().getName(), channel.get().getDescription(), channel.get().isOpen() );
			channelResponses.add( channelResponse );
		}
		return channelResponses;
	}
	
	
	@PostMapping("/favorite_channel")
	public ResponseEntity<?> acceptUser(@CurrentLoggedUser UserPrincipal loggedUser) {
		
		return null;
	}
	
	
	@PostMapping("/list_pedents_users/{channelId}")
	public List listPedentUsers(@CurrentLoggedUser UserPrincipal loggedUser, @PathVariable(value = "channelId") Long channelId) {
		Optional<Profile> profile = Optional.of( this.profileRepository.findByUserId( loggedUser.getId() ).orElseThrow( () -> new ResourceNotFoundException( "Usuário", "email", loggedUser.getEmail() ) ) );
		Optional<Channel> channel = Optional.of( this.channelRepository.findById( channelId ).orElseThrow( () -> new ResourceNotFoundException( "Canal", "id", channelId ) ) );
		
		Optional<ProfileChannel> profileChannel = Optional.of( this.profileChannelRepository.findByChannelId( channelId ).orElseThrow( () -> new ResourceNotFoundException( "Canal", "id", channelId ) ) );
		
		if (profile.get().getId().equals( profileChannel.get().getProfile().getId() ) && !profileChannel.get().isModerator())
			
			return null;
	}
	
	@PostMapping("/refuse_user/{channelID}/{userID}")
	public ResponseEntity<?> refuseUser(@CurrentLoggedUser UserPrincipal loggedUser) {
		
		return null;
	}
	
	
}
