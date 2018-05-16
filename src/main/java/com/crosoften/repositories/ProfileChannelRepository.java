package com.crosoften.repositories;

import com.crosoften.models.Channel;
import com.crosoften.models.Profile;
import com.crosoften.models.ProfileChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProfileChannelRepository extends JpaRepository<ProfileChannel, Long> {
	
	boolean existsByProfileAndChannel(Profile profile, Channel channel);
	
	Optional<ProfileChannel> findByChannelId(Long channelId);
	
//	Boolean existsBy

	
}
