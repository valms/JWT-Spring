package com.crosoften.repositories;

import com.crosoften.models.Channel;
import com.crosoften.models.Profile;
import com.crosoften.models.WaitlistChannel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WaitlistRepository extends JpaRepository<WaitlistChannel, Long> {
	
	boolean existsByProfileAndChannel(Profile profile, Channel channel);
	
	List<WaitlistChannel> findAllByChannelId(Long channelId);
}
