package com.crosoften.repositories;

import com.crosoften.models.Channel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
	
	Page<Channel> findByCreatedBy(Long userId, Pageable pageable);
	
	List<Channel> findAllByCreatedBy(Long userId);
	
	long countByCreatedBy(Long userId);
}