package com.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.GameConfig;


@Repository("gameConfigRepository")
public interface GameConfigRepository extends JpaRepository<GameConfig,Long> {
	GameConfig findById(Long Id);  
    
}
