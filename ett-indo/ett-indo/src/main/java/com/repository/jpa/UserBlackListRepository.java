package com.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.UserBlackList;

@Repository("userBlackListRepository")
public interface UserBlackListRepository extends JpaRepository<UserBlackList,Long>{
	
	UserBlackList findByEttId(Long ettId);
	
}
