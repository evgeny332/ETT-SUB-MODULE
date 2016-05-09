package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.BreakingAlert;
import com.domain.entity.UserNoOffersAvailable;



@Repository("userNoOffersAvailableRepository")
public interface UserNoOffersAvailableRepository extends JpaRepository<UserNoOffersAvailable,Long> {
	
	
}
