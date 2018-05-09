package com.crosoften.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChannelRequest {
	
	@NotBlank
	private boolean isOpen;
	
	private String description;
	
}
