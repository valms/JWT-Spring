package com.crosoften.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Profile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nickname;
	
	private String city;
	
	private String gender;
	
	private boolean enableNotification;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	
	private User user;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "settings_id", unique = true)
	private Settings settings;
	
}
