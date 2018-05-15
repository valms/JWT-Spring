package com.crosoften.repositories;

import com.crosoften.models.FavoriteChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteChannelRepository extends JpaRepository<FavoriteChannel, Long> {
	
	List<FavoriteChannel> findAllByProfileId(Long profileId);
}
