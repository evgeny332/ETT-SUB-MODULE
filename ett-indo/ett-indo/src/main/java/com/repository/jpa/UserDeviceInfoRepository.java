package com.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.UserDeviceInfo;


@Repository("userDeviceInfoRepository")
public interface UserDeviceInfoRepository extends JpaRepository<UserDeviceInfo,Long> {
	UserDeviceInfo findByEttId(Long ettId);  
    
}