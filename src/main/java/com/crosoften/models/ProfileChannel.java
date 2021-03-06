package com.crosoften.models;

import com.crosoften.models.audit.AuditModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
public class ProfileChannel extends AuditModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private boolean isModerator;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id", nullable = false)
	@JsonIgnore
	private Profile profile;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channel_id", nullable = false)
	@JsonIgnore
	private Channel channel;
	
	public ProfileChannel(boolean isModerator, Profile profile, Channel channel) {
		this.isModerator = isModerator;
		this.profile = profile;
		this.channel = channel;
	}
	
	public ProfileChannel() {
	
	}
}
