package com.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.CallBackConfirmationDeviceChange;

@Repository("callBackConfirmationDeviceChangeRepository")
public interface CallBackConfirmationDeviceChangeRepository extends JpaRepository<CallBackConfirmationDeviceChange, Long> {

	
}
