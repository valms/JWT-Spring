package com.crosoften.repositories;

import com.crosoften.models.ProfileChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProfileChannelRepository extends JpaRepository<ProfileChannel, Long> {
}
