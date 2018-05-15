package com.crosoften.payload.response;


import lombok.Getter;

@Getter
public class ChannelResponse {
	
	private Long id;
	private String channelName;
	private String channelDescription;
	private boolean isOpen;
	
	public ChannelResponse(Long id, String channelName, String channelDescription, boolean isOpen) {
		this.id = id;
		this.channelName = channelName;
		this.channelDescription = channelDescription;
		this.isOpen = isOpen;
	}
	
}
