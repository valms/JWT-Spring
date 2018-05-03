package com.crosoften.controllers;


import com.crosoften.models.Profile;
import com.crosoften.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProfileController {
	
	private final ProfileRepository profileRepository;
	
	@Autowired
	public ProfileController(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}
	
	@GetMapping("/profiles")
	public Page<Profile> getAllProfiles(Pageable pageable) {
		return this.profileRepository.findAll( pageable );
	}
	
	
}
