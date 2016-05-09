package com.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.UserSource;


@Repository("userSourceRepository")
public interface UserSourceRepository extends JpaRepository<UserSource,Long> {
	
	UserSource findByUtmSource(String utmSource);
    
	UserSource findByEttId(Long ettId);
}
