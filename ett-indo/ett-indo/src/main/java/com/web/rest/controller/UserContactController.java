package com.web.rest.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import javax.annotation.Resource;

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

import com.domain.entity.User;
import com.domain.entity.UserContactDetails;
import com.repository.jpa.UserContactDetailsRepository;
import com.repository.jpa.UserRepository;
import com.service.EttApis;

/**
 * 
 * @author ankit
 *
 */

@Controller
@RequestMapping(value = "/v1")
public class UserContactController {

	private static Logger LOGGER = LoggerFactory.getLogger(UserContactController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Resource
	private EttApis ettApis;
	
	@Autowired
	private UserContactDetailsRepository userContactDetailsRepository;
	
	
	@RequestMapping(value = "user/contactInfo/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> userContact(@RequestParam("ettId") long ettId,
			@RequestParam("otp") int otp,
			@RequestParam("msisdnList") List<String> msisdnList,
			@RequestParam("operationType") String operationType)
			throws IOException, ExecutionException, InterruptedException {
		
		LOGGER.info("API user/contactInfo ettId={},otp={},msisdnList={},operationType={}",ettId,otp,msisdnList,operationType);
		
		if(msisdnList.size() <= 0){
			LOGGER.info("Alert ! No msisdn for insert or remove operation in UserContactDetails");
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		if(ettApis.getBlackListStatus(ettId)) {
			LOGGER.info("ettId BlackListed {}",ettId);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		User user = userRepository.findByEttId(ettId);
		if(!user.isVerified() || user.getOtp() != otp){
			LOGGER.warn("ettId={}, is not verified but still sending contactInfo",ettId);
			ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		List<UserContactDetails> userContactDetails = userContactDetailsRepository.findByEttId(user.getEttId());
		
		if(operationType.equalsIgnoreCase("insert")){
			insertContact(user,userContactDetails,msisdnList);
		} else if(operationType.equalsIgnoreCase("remove")){
			changeContactStatus(user,userContactDetails,msisdnList);
		} else {
			LOGGER.error("Error user/cantactInfo unIdentified operation type of ettId={},operationtype={}",ettId,operationType);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}


	private void changeContactStatus(User user, List<UserContactDetails> userContactDetails, List<String> msisdnList) {
		if(userContactDetails == null || userContactDetails.size()<=0){
			LOGGER.info("No contact details of ettId={}, to remove",user.getEttId());
			return;
		}
		List<UserContactDetails> tempUserContact = new ArrayList<>(msisdnList.size());
		for(String msisdn : msisdnList){
			UserContactDetails userCont = new UserContactDetails();
			userCont.setMsisdn(msisdn);
			tempUserContact.add(userCont);
		}
		userContactDetails.retainAll(tempUserContact);
		if(userContactDetails.size() > 0){
			for(UserContactDetails contactToRemove : userContactDetails){
				contactToRemove.setStatus(false);
			}
			userContactDetailsRepository.save(userContactDetails);
			LOGGER.info("status of the contact of ettId={} have been changed",user.getEttId());
		}
	}


	private void insertContact(User user, List<UserContactDetails> userContactDetails, List<String> msisdnList) {
		List<UserContactDetails> tempUserContact = new ArrayList<>(msisdnList.size());
		for(String msisdn : msisdnList){
			if(!(msisdn.matches("\\d+")))
				continue;
			UserContactDetails userCont = new UserContactDetails();
			userCont.setMsisdn(msisdn);
			userCont.setEttId(user.getEttId());
			userCont.setStatus(true);
			tempUserContact.add(userCont);
		}
		if(userContactDetails != null && userContactDetails.size() > 0){
			tempUserContact.removeAll(userContactDetails);
		}
		userContactDetailsRepository.save(tempUserContact);
		LOGGER.info("New entry saved in UserContactDetails of ettId={}",user.getEttId());
	}
}
