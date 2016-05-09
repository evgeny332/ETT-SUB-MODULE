package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.entity.UserProfile;



@Repository("userProfileRepository")
public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
	UserProfile findByEttId(Long ettId);
        
    //User findByDeviceId(String deviceId);   
	@Transactional
	@Modifying
	@Query("update UserProfile u set u.ettId= ?1 where u.ettId= ?2")
	void updateEttId(Long ettLong, Long ettLong2);
    
	
	List<UserProfile> findByEmailOrderByUpdateDateDesc(String email);
}
