package com.crosoften.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ChannelRequest {
	
	@NotNull
	private boolean channelOpen;
	
	@NotBlank
	private String name;
	
	private String description;
	
}
