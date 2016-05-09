package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.InstalledAppsNotReg;




@Repository("installedAppsNotRegRepository")
public interface InstalledAppsNotRegRepository extends JpaRepository<InstalledAppsNotReg,String> {
   List<InstalledAppsNotReg> findByIdIn(List<String> id);
   
   List<InstalledAppsNotReg> findByEttId(Long ettId);
}