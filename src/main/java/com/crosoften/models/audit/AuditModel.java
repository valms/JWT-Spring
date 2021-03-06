package com.crosoften.models.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
	value = {"createdAt", "updatedAt"},
	allowGetters = true
)
@NoArgsConstructor
@Getter
@Setter
public class AuditModel implements Serializable {
	
	@Column(nullable = false)
	@CreatedDate
	private Instant createdAt;
	
	@Column(nullable = false)
	@LastModifiedDate
	private Instant updatedAt;
	
	
}
