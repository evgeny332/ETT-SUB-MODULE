package com.web.rest.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.entity.UserFbInfo;
import com.repository.jpa.UserFbInfoRepository;

/**
 * @author ankur
 */
@Controller
@RequestMapping(value = "/v1")
public class    FBController {

    private static Logger LOGGER = LoggerFactory.getLogger(FBController.class);

   @Resource
   UserFbInfoRepository userFbInfoRepository;
    
    @RequestMapping(value = "/user/fb/login/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> saveUserProfile(
    										@RequestParam(value="ettId") Long ettId,
    										@RequestParam(value="fbToken") String fbToken
    										)
	{
		LOGGER.info("/user/fb/login/ ettId {} | fbToken {}",ettId,fbToken);
		UserFbInfo userFbInfo = new UserFbInfo();
		userFbInfo.setEttId(ettId);
		userFbInfo.setAccessToken(fbToken);
		userFbInfoRepository.save(userFbInfo);
		return new ResponseEntity<>(HttpStatus.OK);
  }
}