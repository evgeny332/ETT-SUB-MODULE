package com.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.AstrologyClicks;

@Repository("astrologyClicksRepository")
public interface AstrologyClicksRepository extends JpaRepository<AstrologyClicks, Long>{

	
}
