package com.crosoften.payload.response;

import com.crosoften.models.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSummary {
	
	private Long id;
	private String nickname;
	private String email;
	private String city;
	private Gender gender;
	
	
}
