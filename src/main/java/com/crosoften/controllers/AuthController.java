package com.crosoften.controllers;


import com.crosoften.exception.AppException;
import com.crosoften.models.Gender;
import com.crosoften.models.Profile;
import com.crosoften.models.auth.Role;
import com.crosoften.models.auth.RoleName;
import com.crosoften.models.auth.User;
import com.crosoften.payload.response.ApiResponse;
import com.crosoften.payload.response.JwtAuthenticationResponse;
import com.crosoften.payload.request.LoginRequest;
import com.crosoften.payload.request.SignUpRequest;
import com.crosoften.repositories.RoleRepository;
import com.crosoften.repositories.UserRepository;
import com.crosoften.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	private final JwtTokenProvider jwtTokenProvider;
	
	
	@Autowired
	public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (this.userRepository.existsByEmail( signUpRequest.getEmail() )) {
			return new ResponseEntity<>( new ApiResponse( false, "E-mail já cadastrado" ), HttpStatus.BAD_REQUEST );
		}
		
		// Criando Usuáruo
		
		User user = new User();
		user.setEmail( signUpRequest.getEmail() );
		user.setPassword( this.passwordEncoder.encode( signUpRequest.getPassword() ) );
		
		Role userRole = this.roleRepository.findByName( RoleName.ROLE_USER ).orElseThrow( () -> new AppException( "Role não definida" ) );
		
		user.setRoles( Collections.singleton( userRole ) );
		
		Profile profile = new Profile();
		profile.setCity( signUpRequest.getCity() );
		
		Gender userGender = Gender.valueOf( signUpRequest.getGender() );
		
		profile.setGender( userGender );
		profile.setCity( signUpRequest.getCity() );
		profile.setNickname( signUpRequest.getNickname() );
		profile.setEnableNotification( signUpRequest.isEnableNotification() );
		
		user.setProfile( profile );
		profile.setUser( user );
		
		User result = this.userRepository.save( user );
		
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path( "/users/{nickname}" )
			               .buildAndExpand( result.getProfile().getNickname() ).toUri();
		
		
		return ResponseEntity.created( location ).body( new ApiResponse( true, "Usuário Criado com sucesso" ) );
		
	}
	
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		
		Authentication authentication = this.authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( loginRequest.getEmail(), loginRequest.getPassword() ) );
		
		SecurityContextHolder.getContext().setAuthentication( authentication );
		
		String jwtToken = this.jwtTokenProvider.generateToken( authentication );
		
		return ResponseEntity.ok( new JwtAuthenticationResponse( jwtToken ) );
		
	}
	
	
}
