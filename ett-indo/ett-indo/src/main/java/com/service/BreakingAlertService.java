package com.service;

import java.util.Date;

import com.domain.entity.BreakingAlert;
import com.domain.entity.DownloadBoosterEligibleUser;
import com.domain.entity.User;
import com.web.rest.dto.BreakingAlertDto;

public interface BreakingAlertService {

		BreakingAlertDto getBreakingAlertDto(BreakingAlert breakingAlert,User user);
}
