package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.User;


@Repository("userRepository")
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEttId(Long ettId);
    
    List<User> findByEttIdIn(List<Long> ettId);
    
    User findByDeviceId(String deviceId);
    
    //User findByMsisdn(String msisdn);
   //@Query("select u from User u where u.msisdn= ?1 order by isVerified")
    @Query("select u from User u where u.msisdn= ?1 order by u.isVerified desc,u.createdTime desc")
    List<User> findByMsisdn(String msisdn);
    
    @Query("select u from User u where u.deviceId in (?1) order by u.updatedTime desc")
    List<User> findByDeviceId_1(String deviceId);
    
    @Query("select u.ettId from User u where u.deviceId in (?1) order by u.updatedTime desc")
    List<Long> findByDeviceId_2(String deviceId);
    
    @Query("select u.ettId from User u where u.deviceId in (?1) and isVerified=true")
    List<Long> findByDeviceId_3(String deviceId);
    
    @Query("select u from User u where u.msisdn= ?1 and u.isVerified=true order by u.updatedTime desc")
    List<User> findByMsisdnVerfied(String msisdn);
    
    @Query("select u from User u where u.deviceId in (?1) and u.isDownloadedFirstApp>='1' order by isDownloadedFirstApp desc")
    List <User> findByDeviceIdIsDownLoad_1(String deviceId);
    
}
