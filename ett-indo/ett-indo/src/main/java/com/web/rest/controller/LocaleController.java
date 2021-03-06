package com.web.rest.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

import com.domain.entity.LocaleTextTemplate.Locale;
import com.domain.entity.User;
import com.repository.jpa.UserRepository;
import com.service.EttApis;

/*
 * @author ankit
 */

@Controller
@RequestMapping(value = "/v1")
public class LocaleController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(LocaleController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Resource
	private EttApis ettApis;
	
	
	@RequestMapping(value = "user/setLocale/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> setLocale(@RequestParam("ettId") long ettId,
			@RequestParam("otp") int otp,
			@RequestParam(value = "locale", required = false, defaultValue = "EN") String locale
			) throws IOException, ExecutionException, InterruptedException {
		
		LOGGER.info("API user/setLocale ettId={},otp={},locale={}",ettId,otp,locale);
		
		if(ettApis.getBlackListStatus(ettId)) {
			LOGGER.info("ettId BlackListed {}",ettId);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		User user = userRepository.findByEttId(ettId);
		if(!user.isVerified() || user.getOtp() != otp){
			LOGGER.warn("ettId="+ ettId+ "is not verified but still trying to send setLocale");
			ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if(user.getLocale() != Locale.valueOf(locale)){
			user.setLocale(Locale.valueOf(locale));
			userRepository.save(user);
			LOGGER.info("locale={}, for ettId={}, saved",ettId,locale);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
