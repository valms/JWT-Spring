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
import com.crosoften.payload.response.UploadFileResponse;
import com.crosoften.repositories.ProfileRepository;
import com.crosoften.repositories.RoleRepository;
import com.crosoften.repositories.UserRepository;
import com.crosoften.security.JwtTokenProvider;
import com.crosoften.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final ProfileRepository profileRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final FileStorageService fileStorageService;
	
	
	@Autowired
	public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, ProfileRepository profileRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, FileStorageService fileStorageService) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.profileRepository = profileRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.fileStorageService = fileStorageService;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (this.userRepository.existsByEmail( signUpRequest.getEmail() )) {
			return new ResponseEntity<>( new ApiResponse( false, "E-mail já cadastrado" ), HttpStatus.BAD_REQUEST );
		}
		
		if (this.profileRepository.existsByNickname( signUpRequest.getNickname() )) {
			return new ResponseEntity<>( new ApiResponse( false, "Nickname já cadastrado" ), HttpStatus.BAD_REQUEST );
		}
		
		// Criando Usuário
		
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
		
		//TODO: Arrumar Files e implementar Chat
//		Arrays.stream( files ).map( file -> uploadFile( file, profile ) ).collect( Collectors.toList() );
		
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
	
	private UploadFileResponse uploadFile(MultipartFile file, Profile profile) {
		String fileName = this.fileStorageService.storeFile( file );
		String uriDownload = ServletUriComponentsBuilder.fromCurrentContextPath().path( "/downloadFile/" ).path( fileName ).toUriString();
		
		return new UploadFileResponse( fileName, uriDownload, file.getContentType(), file.getSize() );
	}
	
	
}
