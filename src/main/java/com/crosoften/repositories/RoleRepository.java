package com.crosoften.repositories;

import com.crosoften.models.auth.Role;
import com.crosoften.models.auth.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Optional<Role> findByName(RoleName roleName);
}
