package com.web.rest.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.entity.DataUsagePendingCredits;
import com.domain.entity.User;
import com.domain.entity.UserAccountSummary;
import com.repository.jpa.DataUsagePendingCreditsRepository;
import com.repository.jpa.DeviceTokenRepository;
import com.repository.jpa.UserAccountRepository;
import com.repository.jpa.UserAccountSummaryRepository;
import com.repository.jpa.UserRepository;
import com.service.DataUsageService;
import com.service.EttApis;

/*
 * @author ankit
 */

@Controller
@RequestMapping(value = "/v1")
public class DataUsageController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(DataUsageController.class);

	@Autowired
	UserAccountRepository userAccountRepository;

	@Autowired
	UserAccountSummaryRepository userAccountSummaryRepository;
	
	@Autowired
	private DeviceTokenRepository deviceTokenRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DataUsagePendingCreditsRepository dataUsagePendingCreditsRepository;
	
	@Resource
	private EttApis ettApis;
	
	@Resource
	private DataUsageService dataUsageService;
	
	@RequestMapping(value = "user/dataUsage/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> dataUsageEdr(@RequestParam("ettId") long ettId,
			@RequestParam("otp") int otp,
			@RequestParam("id") List<Long> id,
			@RequestParam("totalUsedData") List<Long> totalUsedData)
			throws IOException, ExecutionException, InterruptedException {
		
		LOGGER.info("API user/dataUsage ettId={},otp={},id={},totalUsedData={}",ettId,otp,id,totalUsedData);
		
		if(ettApis.getBlackListStatus(ettId)) {
			LOGGER.info("ettId BlackListed {}",ettId);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		User user = userRepository.findByEttId(ettId);
		if(!user.isVerified() || user.getOtp() != otp){
			LOGGER.warn("ettId="+ ettId+ "is not verified but still trying to send Data Usage");
			ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		//List<DataUsagePendingCredits> dataUsagePendingCredits = dataUsagePendingCreditsRepository.findByIdAndEttId(id, user.getEttId());
		/*if(dataUsagePendingCredits == null){
			LOGGER.info("ettId={} not eligible for dataUsage bcz of either no entry in table or status is zero",ettId);
			return new ResponseEntity<>(id,HttpStatus.OK);
			//return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}*/
		List<Long> idsToDeactivate = new ArrayList<>();
		//for(DataUsagePendingCredits usagePendingCredits : dataUsagePendingCredits){
		for(int i=0;i<id.size();i++) {
			Long curId = id.get(i);
			Long curUsage = totalUsedData.get(i);
			if(curId == null || curUsage == null){
				LOGGER.info("ettId={} is trying with null id in data usage",ettId);
				continue;
			}
			DataUsagePendingCredits usagePendingCredits = dataUsagePendingCreditsRepository.findById(curId);
			if(usagePendingCredits == null){
				LOGGER.info("ettId={} not eligible for dataUsage bcz of either no entry in table or status is zero",ettId);
				idsToDeactivate.add(curId);
				continue;
				//return new ResponseEntity<>(id,HttpStatus.OK);
				//return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			if(usagePendingCredits.getEttId()!=ettId) {
				LOGGER.info("ettId={} wrong ettId for id={}",ettId,curId);
				//idsToDeactivate.add(usagePendingCredits.getId());
				continue;
			}
			
			if(usagePendingCredits.getEligibleStatus() == 2){
				idsToDeactivate.add(usagePendingCredits.getId());
				continue;
			}
			if(!dataUsageService.checkDateEligiblity(usagePendingCredits)){
				LOGGER.info("ettId={} exausted the eligible date",user.getEttId());
				usagePendingCredits.setEligibleStatus(3);
				dataUsagePendingCreditsRepository.save(usagePendingCredits);
				idsToDeactivate.add(usagePendingCredits.getId());
				continue;
			}
			
			if((curUsage - usagePendingCredits.getTotalUsedData()) < usagePendingCredits.getDataThreshold()){
				LOGGER.info("ettId={} has not crossed the data threshold of dataUsage",user.getEttId());
				continue;
				//return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			float totalEarnedAmount = 0f;
			float todaysEarnedAmount = 0f;
			List<UserAccountSummary> userAccountSummary = userAccountSummaryRepository.findByEttIdAndOfferId(user.getEttId(), usagePendingCredits.getOfferId()); 
			DateTime now = DateTime.now();
			for(UserAccountSummary userAccSummary : userAccountSummary){
				if(userAccSummary.getRemarks().equals("DATA_USAGE")){
					totalEarnedAmount = totalEarnedAmount + userAccSummary.getAmount();
					DateTime createdTime = new DateTime(userAccSummary.getCreatedTime());
					Period period = new Period(createdTime, now, PeriodType.hours());
					int hourDiff = period.getHours();
					if(hourDiff <= 24){
						todaysEarnedAmount = todaysEarnedAmount + userAccSummary.getAmount();
					}
				}
			}
			if(totalEarnedAmount >= usagePendingCredits.getMaxCreditLimit() || todaysEarnedAmount >= usagePendingCredits.getMaxCreditPerDayLimit()){
				if(totalEarnedAmount >= usagePendingCredits.getMaxCreditLimit())
					usagePendingCredits.setEligibleStatus(2);
			
				usagePendingCredits.setTotalUsedData(curUsage);
				dataUsagePendingCreditsRepository.save(usagePendingCredits);
				LOGGER.info("ettId={} has crossed the credit limit of dataUsage",user.getEttId());
				continue;
				//return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			int dataUsageChunk = dataUsageService.getThresholdChunk(usagePendingCredits, curUsage, totalEarnedAmount, todaysEarnedAmount);
			
			
			if(dataUsageChunk >= 1){
				dataUsageService.creditUserForDataUsage(user, usagePendingCredits, dataUsageChunk);
				LOGGER.info("ettId={} has been credited={} for dataUsage",user.getEttId(),dataUsageChunk * usagePendingCredits.getAmountPerDataThreshold());
			
				usagePendingCredits.setTotalUsedData(usagePendingCredits.getTotalUsedData() + (usagePendingCredits.getDataThreshold() * dataUsageChunk));
				dataUsagePendingCreditsRepository.save(usagePendingCredits);
			}
		}
		if(idsToDeactivate.size()==0) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(idsToDeactivate,HttpStatus.OK);
	}
	
	@RequestMapping(value = "user/activateDataUsage/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> dataUsage(@RequestParam("ettId") long ettId,
			@RequestParam("otp") int otp,
			@RequestParam("id") long id,
			@RequestParam("totalUsedData") long totalUsedData)
			throws IOException, ExecutionException, InterruptedException {
		
		LOGGER.info("API user/activateDataUsage ettId={},otp={},id={},totalUsedData={}",ettId,otp,id,totalUsedData);
		
		if(ettApis.getBlackListStatus(ettId)) {
			LOGGER.info("ettId BlackListed {}",ettId);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		User user = userRepository.findByEttId(ettId);
		if(!user.isVerified() || user.getOtp() != otp){
			LOGGER.warn("ettId="+ ettId+ "is not verified but still trying to send Data Usage");
			ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		DataUsagePendingCredits usagePendingCredits = dataUsagePendingCreditsRepository.findById(id);
		if(usagePendingCredits == null || usagePendingCredits.getEttId() != ettId){
			LOGGER.info("ettId={} no entry in dataUsage tabe",ettId);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if(usagePendingCredits.getEligibleStatus() != 1){
			usagePendingCredits.setEligibleStatus(1);
			usagePendingCredits.setTotalUsedData(totalUsedData);
			dataUsagePendingCreditsRepository.save(usagePendingCredits);
			LOGGER.info("ettId={} is now active for dataUsage service",user.getEttId());
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
