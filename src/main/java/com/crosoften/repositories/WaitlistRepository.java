package com.crosoften.repositories;

import com.crosoften.models.Channel;
import com.crosoften.models.Profile;
import com.crosoften.models.WaitlistChannel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WaitlistRepository extends JpaRepository<WaitlistChannel, Long> {
	
	boolean existsByProfileAndChannel(Profile profile, Channel channel);
	
	Optional<WaitlistChannel> findByChannelAndProfile(Channel channel, Profile profile);
	
	List<WaitlistChannel> findAllByChannel(Channel channel);
}
