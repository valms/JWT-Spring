package com.crosoften.models;


import com.crosoften.models.audit.UserAuditModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Channel extends UserAuditModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private boolean isOpen;
	
	@Lob
	private String description;

}
