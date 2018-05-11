package com.crosoften.payload.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelResponse {
	
	private String channelName;
	private String channelDescription;

	
	public ChannelResponse(String channelName, String channelDescription) {
		this.channelName = channelName;
		this.channelDescription = channelDescription;
	}
	
}
