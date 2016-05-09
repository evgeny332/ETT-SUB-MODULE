package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.InstalledAppNewDevice;




@Repository("installedAppNewDevice")
public interface InstalledAppNewDeviceRepository extends JpaRepository<InstalledAppNewDevice,String> {
   List<InstalledAppNewDevice> findByIdIn(List<String> id);
   
   List<InstalledAppNewDevice> findByEttId(Long ettId);
}