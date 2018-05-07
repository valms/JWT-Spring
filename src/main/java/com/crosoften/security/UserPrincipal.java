package com.crosoften.security;

import com.crosoften.models.auth.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class UserPrincipal implements UserDetails {
	
	@Getter
	private Long id;
	
	@Getter
	private String email;
	
	private String password;
	
	private Collection<? extends GrantedAuthority> grantedAuthorities;
	
	
	public UserPrincipal(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.grantedAuthorities = authorities;
	}
	
	
	public static UserPrincipal create(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream().map(
			role -> new SimpleGrantedAuthority( role.getName().name() )
		).collect( Collectors.toList() );
		
		return new UserPrincipal( user.getId(), user.getEmail(), user.getPassword(), authorities );
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.grantedAuthorities;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}
	
	@Override
	public String getUsername() {
		return null;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		UserPrincipal userPrincipal = (UserPrincipal) obj;
		return Objects.equals( id, userPrincipal.id );
	}
	
	@Override
	public int hashCode() {
		return Objects.hash( id );
	}
}
