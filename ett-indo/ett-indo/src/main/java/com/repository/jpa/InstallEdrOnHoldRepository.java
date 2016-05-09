package com.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.InstallEdrOnHold;

@Repository("installEdrOnHoldRepository")
public interface InstallEdrOnHoldRepository extends JpaRepository<InstallEdrOnHold, String> {

}
