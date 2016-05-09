package com.etxWeb.repository.active;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import com.etxWeb.entity.active.SmsCliCapture;

public interface SmsCliCaptureRepository extends JpaRepository<SmsCliCapture, Long> {

}
