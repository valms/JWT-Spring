package com.crosoften.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class SignUpRequest {
	
	@NotBlank
	@Size(min = 4, max = 40)
	private String nickname;
	
	@NotBlank
	private String city;
	
	@NotBlank
	private String gender;
	
	@NotBlank
	@Size(min = 3, max = 40)
	private String email;
	
	@NotBlank
	@Size(min = 6, max = 20)
	private String password;
	
	@NotNull
	private boolean enableNotification;
	
}