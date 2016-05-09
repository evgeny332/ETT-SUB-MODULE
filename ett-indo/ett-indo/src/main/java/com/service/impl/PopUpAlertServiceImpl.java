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
import com.domain.entity.PopUpAlert;
import com.domain.entity.RedeemThreshold;
import com.domain.entity.TempOtpRegId;
import com.domain.entity.User;
import com.domain.entity.UserAccount;
import com.domain.entity.UserAccountSummary;
import com.domain.entity.UserBlackList;
import com.repository.jpa.BreakingAlertRepository;
import com.repository.jpa.DeviceTokenRepository;
import com.repository.jpa.PopUpAlertRepository;
import com.repository.jpa.RedeemThresholdRepository;
import com.repository.jpa.TempOtpRegIdRepository;
import com.repository.jpa.UserAccountRepository;
import com.repository.jpa.UserAccountSummaryRepository;
import com.repository.jpa.UserBlackListRepository;
import com.service.BreakingAlertService;
import com.service.EttApis;
import com.service.PopUpAlertService;
import com.service.RechargeService;
import com.web.rest.dto.BreakingAlertDto;
import com.web.rest.dto.PopUpAlertDto;



@Service
public class PopUpAlertServiceImpl implements PopUpAlertService {

	private static Logger LOGGER = LoggerFactory.getLogger(PopUpAlertServiceImpl.class);
	@Resource
	private PopUpAlertRepository popUpAlertRepository;
	@Override
	public PopUpAlertDto getPopUpAlertDto(PopUpAlert popUpAlert, User user) {
		PopUpAlertDto popUpAlertDto = new PopUpAlertDto();
		if(popUpAlert != null) {
			popUpAlertDto.setHeading(popUpAlert.getHeading());
			popUpAlertDto.setId(popUpAlert.getId());
			popUpAlertDto.setText(popUpAlert.getText());
			popUpAlertDto.setNoOfButton(popUpAlert.getNoOfButton());
			if(popUpAlert.getButtonsText() != null){
				popUpAlertDto.setButtonsText(popUpAlert.getButtonsText().split(";"));
			}
			if(popUpAlert.getActinoUrl() != null) {
				popUpAlertDto.setActinoUrl(popUpAlert.getActinoUrl().split(";"));
			}
			popUpAlertDto.setUpdate_id(popUpAlert.getId()+"_"+popUpAlert.getUpdate_triger_id());
		}
		return popUpAlertDto;
	}
	
		
}
