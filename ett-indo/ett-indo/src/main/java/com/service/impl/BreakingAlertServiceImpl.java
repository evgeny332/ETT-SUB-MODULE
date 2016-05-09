package com.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.domain.entity.BreakingAlert;
import com.domain.entity.DeviceToken;
import com.domain.entity.DownloadBoosterEligibleUser;
import com.domain.entity.RedeemThreshold;
import com.domain.entity.TempOtpRegId;
import com.domain.entity.User;
import com.domain.entity.UserAccount;
import com.domain.entity.UserAccountSummary;
import com.domain.entity.UserBlackList;
import com.repository.jpa.BreakingAlertRepository;
import com.repository.jpa.DeviceTokenRepository;
import com.repository.jpa.RedeemThresholdRepository;
import com.repository.jpa.TempOtpRegIdRepository;
import com.repository.jpa.UserAccountRepository;
import com.repository.jpa.UserAccountSummaryRepository;
import com.repository.jpa.UserBlackListRepository;
import com.service.BreakingAlertService;
import com.service.EttApis;
import com.service.RechargeService;
import com.web.rest.dto.BreakingAlertDto;



@Service
public class BreakingAlertServiceImpl implements BreakingAlertService {

	private static Logger LOGGER = LoggerFactory.getLogger(BreakingAlertServiceImpl.class);
	@Resource
	private BreakingAlertRepository breakingAlertRepository;
	
	 	public BreakingAlertDto getBreakingAlertDto(BreakingAlert breakingAlert,User user) {
		BreakingAlertDto breakingAlertDto = new BreakingAlertDto();
		breakingAlertDto.setId(breakingAlert.getId());
		breakingAlertDto.setText(breakingAlert.getText());
		breakingAlertDto.setImageUrl(breakingAlert.getImageUrl());
		breakingAlertDto.setValidity(breakingAlert.getValidity());
		breakingAlertDto.setClickable(breakingAlert.getClickable()+"");
		breakingAlertDto.setOnClickType(breakingAlert.getOnClickType()+"");
		breakingAlertDto.setOfferId(breakingAlert.getOfferId());
		breakingAlertDto.setActionUrl(breakingAlert.getActionUrl().replace("#ETTID#", user.getEttId()+""));
		breakingAlertDto.setMenuName(breakingAlert.getMenuName());
		breakingAlertDto.setPopUpHeading(breakingAlert.getPopUpHeading());
		breakingAlertDto.setPopUpText(breakingAlert.getPopUpText());
		if(breakingAlert.getPopUpButtonText() != null)
			breakingAlertDto.setPopUpButtonText(breakingAlert.getPopUpButtonText().split(";"));
		if(breakingAlert.getPopUpActionUrl() != null){
			breakingAlertDto.setPopUpActionUrl(breakingAlert.getPopUpActionUrl().split(";"));
		}
		breakingAlertDto.setVisualCode(breakingAlert.getVisualCode());	
		breakingAlertDto.setUpdate_id(breakingAlert.getId()+"_"+breakingAlert.getUpdate_triger_id());
	
		return breakingAlertDto;
	}

	
}
