package com.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.DeviceToken;

/**
 * @author ankur
 */
@Repository("deviceTokenRepository")
public interface DeviceTokenRepository extends JpaRepository<DeviceToken,Long> {
  
	 DeviceToken findByDeviceId(String deviceId);
	    DeviceToken findByEttId(Long ettId);
	    
	   // @Query("select u from DeviceToken u where u.email=?1 limit 0,2")
		
	    List<DeviceToken> findByEmail(String email,Pageable pageable);
		
	    List<DeviceToken> findByAdvertisingId(String advertisingId);
		List<DeviceToken> findByAndroidId(String androidId);
		List<DeviceToken> findByMacAddress(String macAddress);
		
		//@Query("select u from DeviceToken u where u.deviceToken=?1 limit 0,2")
		
		List<DeviceToken> findByDeviceToken(String deviceToken,Pageable pageable);
}