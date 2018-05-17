package com.crosoften.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;


@Getter
public class PedentUser {
	
	@NotNull
	private Long userId;
	
	@NotNull
	private boolean isApproved;
	
}
