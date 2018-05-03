package com.crosoften.services.auth;

import com.crosoften.models.auth.Role;
import com.crosoften.models.auth.User;
import com.crosoften.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = this.userRepository.findByEmail( email );
		
		Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
		
		for (Role role : user.getRoles()) {
			grantedAuthoritySet.add( new SimpleGrantedAuthority( role.getName() ) );
		}
		
		return new org.springframework.security.core.userdetails.User( user.getEmail(), user.getPassword(), grantedAuthoritySet );
	}
}
