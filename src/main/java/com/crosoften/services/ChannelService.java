package com.crosoften.services;

import com.crosoften.exception.BadRequestException;
import com.crosoften.exception.ResourceNotFoundException;
import com.crosoften.models.Channel;
import com.crosoften.models.Profile;
import com.crosoften.payload.response.ChannelResponse;
import com.crosoften.payload.response.PagedResponse;
import com.crosoften.repositories.ChannelRepository;
import com.crosoften.repositories.ProfileRepository;
import com.crosoften.repositories.UserRepository;
import com.crosoften.security.UserPrincipal;
import com.crosoften.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class ChannelService {
	
	
	private final ChannelRepository channelRepository;
	private final UserRepository userRepository;
	private final ProfileRepository profileRepository;
	
	
	@Autowired
	public ChannelService(ChannelRepository channelRepository, UserRepository userRepository, ProfileRepository profileRepository) {
		this.channelRepository = channelRepository;
		this.userRepository = userRepository;
		this.profileRepository = profileRepository;
	}
	
	
	public PagedResponse<ChannelResponse> getChannelsCreatedBy(String nickname, UserPrincipal currentUser, int page, int size) {
		this.validatePageNumberAndSize( page, size );
		
		Optional<Profile> profile = Optional.of( this.profileRepository.findByNickname( nickname ).orElseThrow( () -> new ResourceNotFoundException( "Perfil", "nickname", nickname ) ) );
		
		Pageable pageable = new PageRequest( page, size, Sort.Direction.ASC, "createdAt" );
		Page<Channel> channels = this.channelRepository.findByCreatedBy( profile.get().getId(), pageable );
		
		if (channels.getNumberOfElements() == 0) {
			return new PagedResponse<>( Collections.emptyList(), channels.getNumber(), channels.getSize(), channels.getTotalElements(), channels.getTotalPages(), channels.isLast() );
		}
		
		//TODO: Parei aqui
		return null;
	}
	
	private void validatePageNumberAndSize(int page, int size) {
		if (page < 0) {
			throw new BadRequestException( "Números de Páginas precisam ser maio que zero" );
		}
		
		if (size > AppConstants.MAX_PAGE_SIZE) {
			throw new BadRequestException( "Tamanho da pagina precisa ser menor que " + AppConstants.MAX_PAGE_SIZE );
		}
	}
}
