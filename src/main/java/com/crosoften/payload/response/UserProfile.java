package com.crosoften.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UserProfile {
	
	private String nickname;
	private String email;
	private Instant joinedAt;
	
	public UserProfile(String nickname, String email, Instant joinedAt) {
		this.nickname = nickname;
		this.email = email;
		this.joinedAt = joinedAt;
	}
}
