package com.repository.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.UserRedeem;


@Repository("userRedeemRepository")
public interface UserRedeemRepository extends JpaRepository<UserRedeem,Long> {
	UserRedeem findById(Long Id);  
    
	@Query("select u from UserRedeem u where u.ettId=?1 and u.createdTime>=?2 and u.status='SUCCESS'")
	List<UserRedeem> getTodaysRedeem(Long ettId,Date date);
	
}
