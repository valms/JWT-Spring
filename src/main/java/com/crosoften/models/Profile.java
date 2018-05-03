package com.crosoften.models;


import com.crosoften.models.auth.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Profile extends AuditModel implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(max = 90)
	private String nickname;
	
	@Size(max = 100)
	private String city;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private Gender gender;
	
	private boolean enableNotification;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "settings_id", nullable = false)
	private Settings settings;
	
}
