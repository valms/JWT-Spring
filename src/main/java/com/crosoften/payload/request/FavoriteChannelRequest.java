package com.crosoften.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class FavoriteChannelRequest {
	
	@NotNull
	private Long channelId;
	
}
