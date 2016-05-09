package com.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.DownloadBoosterEligibleUser;

@Repository("downloadBoosterEligibleUserRepository")
public interface DownloadBoosterEligibleUserRepository extends JpaRepository<DownloadBoosterEligibleUser, String>{

	DownloadBoosterEligibleUser findByEttId(Long ettId);
}
