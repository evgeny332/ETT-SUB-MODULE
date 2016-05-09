package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.entity.InstalledApps;
import com.domain.entity.UserDublicateCallBackPrevent;




@Repository("userDublicateCallBackPreventRepository")
public interface UserDublicateCallBackPreventRepository extends JpaRepository<UserDublicateCallBackPrevent,String> {
  
   List<InstalledApps> findById(String id);
   
   @Modifying
   @Transactional
   @Query("delete from UserDublicateCallBackPrevent i where i.ettId = ?1")
   void deleteUserDublicateCallBackByEttId(Long ettId);
}