package com.crosoften.models;

import com.crosoften.models.audit.AuditModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

public class WaitlistChannel extends AuditModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id", nullable = false)
	@JsonIgnore
	private Profile profile;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channel_id", nullable = false)
	@JsonIgnore
	private Channel channel;
	
	public WaitlistChannel(Profile profile, Channel channel) {
		this.profile = profile;
		this.channel = channel;
	}
	
	public WaitlistChannel() {
	
	}
	
	
}
