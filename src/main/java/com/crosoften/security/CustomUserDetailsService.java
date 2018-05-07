package com.crosoften.security;

import com.crosoften.models.auth.User;
import com.crosoften.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	@Autowired
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = this.userRepository.findByEmail( email ).orElseThrow( () -> new UsernameNotFoundException( "Usuário não encontrado" ) );
		
		return UserPrincipal.create( user );
	}
	
	public UserDetails loadUserById(Long id) {
		User user = this.userRepository.findById( id ).orElseThrow( () -> new UsernameNotFoundException( "Usuário com o id " + id + " não encontrado" ) );
		
		return UserPrincipal.create( user );
	}
}
