package com.crosoften.payload;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
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
	@Size(min = 3, max = 15)
	private String email;
	
	@NotBlank
	@Size(min = 6, max = 20)
	private String password;
	
	@NotBlank
	private Boolean enableNotification;
	
}
