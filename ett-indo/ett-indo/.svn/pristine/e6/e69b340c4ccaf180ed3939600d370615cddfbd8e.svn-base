package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.entity.InstalledApps;




@Repository("installedAppsRepository")
public interface InstalledAppsRepository extends JpaRepository<InstalledApps,String> {
   List<InstalledApps> findByIdIn(List<String> id);
   
   List<InstalledApps> findByEttId(Long ettId);
   
   @Query("select i.appKey from InstalledApps i where i.ettId = ?1")
   List<String> findAppkeyByEttId(Long ettId);
   
   @Modifying
   @Transactional
   @Query("delete from InstalledApps i where i.ettId = ?1")
   void deleteInstalledAppsByEttId(Long ettId);
}