package com.service;

import java.util.Date;

import com.domain.entity.BreakingAlert;
import com.domain.entity.DownloadBoosterEligibleUser;
import com.domain.entity.PopUpAlert;
import com.domain.entity.User;
import com.web.rest.dto.BreakingAlertDto;
import com.web.rest.dto.PopUpAlertDto;

public interface PopUpAlertService {

		PopUpAlertDto getPopUpAlertDto(PopUpAlert popUpAlert,User user);
}
