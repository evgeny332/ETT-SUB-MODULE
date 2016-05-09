package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.UserDeviceIdChange;

@Repository("userDeviceIdChangeRepository")
public interface UserDeviceIdChangeRepository extends JpaRepository<UserDeviceIdChange, Long>{
	
	List<UserDeviceIdChange> findByOldDeviceId(String oldDeviceId);
}
