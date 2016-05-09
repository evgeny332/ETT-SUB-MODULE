package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.BreakingAlert;
import com.domain.entity.DayAppDownloadCounter;
import com.domain.entity.Tarrot;
import com.domain.entity.TarrotConfig;



@Repository("tarrotRepository")
public interface TarrotRepository extends JpaRepository<Tarrot,Long> {
	
	
	
}
