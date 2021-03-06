package com.crosoften.repositories;

import com.crosoften.models.Channel;
import com.crosoften.models.FavoriteChannel;
import com.crosoften.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteChannelRepository extends JpaRepository<FavoriteChannel, Long> {
	
	List<FavoriteChannel> findAllByProfileId(Long profileId);
	
	boolean existsByProfileAndChannel(Profile profile, Channel channel);
}
