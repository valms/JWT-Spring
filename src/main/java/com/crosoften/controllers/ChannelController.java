package com.crosoften.controllers;


import com.crosoften.payload.request.ChannelRequest;
import com.crosoften.repositories.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ChannelController {
	
	
	private final ChannelRepository channelRepository;
	
	@Autowired
	public ChannelController(ChannelRepository channelRepository) {
		this.channelRepository = channelRepository;
	}
	
	@PostMapping("/create-channel")
	public ResponseEntity<?> createChannel(@Valid @RequestBody ChannelRequest loginRequest) {
		return null;
	}
//
	
}
