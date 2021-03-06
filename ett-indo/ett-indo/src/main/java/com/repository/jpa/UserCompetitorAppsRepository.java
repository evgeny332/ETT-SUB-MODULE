package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.UserCompetitorApps;


@Repository("userCompetitorAppsRepository")
public interface UserCompetitorAppsRepository extends JpaRepository<UserCompetitorApps,Long> {

	List<UserCompetitorApps> findByettId(Long ettId);
    
}