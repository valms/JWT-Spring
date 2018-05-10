package com.crosoften.repositories;

import com.crosoften.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
	
	Optional<Profile> findByUserId(Long userId);
	
	Optional<Profile> findByNickname(String nickName);
	
	Boolean existsByNickname(String nickname);
	
	
}
