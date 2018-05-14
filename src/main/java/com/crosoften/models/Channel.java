package com.crosoften.models;


import com.crosoften.models.audit.UserAuditModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	
	@Size(min = 4, max = 90)
	private String name;
	
	@Column(columnDefinition = "text")
	private String description;
	
}
