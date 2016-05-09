package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.entity.DeviceIdInstalledApps;




@Repository("deviceIdInstalledAppsRepository")
public interface DeviceIdInstalledAppsRepository extends JpaRepository<DeviceIdInstalledApps,String> {
   List<DeviceIdInstalledApps> findByIdIn(List<String> id);
   
   @Query("select d from DeviceIdInstalledApps d where d.deviceId= ?1")
   List<DeviceIdInstalledApps> findByDeviceId(String deviceId);
   
   
   @Transactional
   @Modifying
   @Query("delete from DeviceIdInstalledApps u where u.deviceId in(?1)")
   void deleteDeviceId(String deviceId);
   
   @Query("select d.appKey from DeviceIdInstalledApps d where d.deviceId = ?1")
   List<String> findAppkeyByDeviceId(String deviceId);
   
   
}