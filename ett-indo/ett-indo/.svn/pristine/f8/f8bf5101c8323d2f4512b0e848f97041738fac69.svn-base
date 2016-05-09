package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.AppKeyMap;
import com.domain.entity.UserUrl;


@Repository("userUrlRepository")
public interface UserUrlRepository extends JpaRepository<UserUrl,String> {
   
	@Query("select u from UserUrl u where u.msisdn=?1 and u.status=true")
	UserUrl findByettIdStatus(Long msisdn);
}