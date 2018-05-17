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
	
	public UserSummary(Long id, String nickname, String email, String city, Gender gender) {
		this.id = id;
		this.nickname = nickname;
		this.email = email;
		this.city = city;
		this.gender = gender;
	}
	
	public UserSummary(Long id, String nickname) {
		this.id = id;
		this.nickname = nickname;
	}
}
