package com.web.rest.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Future;

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
import com.http.client.HttpRequestProcessor;
import com.ning.http.client.Response;
import com.repository.jpa.UserRepository;
import com.service.RechargeService;
import com.sms.process.SmsManager;


/**
 * @author ankur
 */
@Controller
@RequestMapping(value = "/v1")
public class TempOtpController {

    private static Logger LOGGER = LoggerFactory.getLogger(TempOtpController.class);

    @Autowired
    private UserRepository userRepository;
   
    @Resource
    private HttpRequestProcessor httpRequestProcessor;
    
    @Resource
    private RechargeService rechargeService;

    @RequestMapping(value = "user/getCurrentOtp/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> login(@RequestParam("ettId") Long ettId
                                  
    )  {
        LOGGER.info("Get Current OTP request received for ettId"+ettId);
        User user = userRepository.findByEttId(ettId);
        if (user==null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}        
        user.setAppVersion("1.1");
        userRepository.save(user);
        
        Future<Response> circleResp  = null;
        //get 1operator circle url
        Map<String,String> queryParam = new HashMap<>();
        queryParam.put("msisdn", user.getMsisdn());
       //send otp sms
       String otpText = rechargeService.getLocaleTextTemplate().get("OTP_TEXT_"+user.getLocale()).replaceFirst("#OTP_KEY#", "otp="+user.getOtp());
       Map<String,String> queryParamMap = new HashMap<>();
       queryParamMap.put("user", "rationalh");
       queryParamMap.put("password", "rationalh123");
       queryParamMap.put("PhoneNumber", "91"+user.getMsisdn());
       queryParamMap.put("sender", "RHTETT");
       queryParamMap.put("text", otpText);
       try {
		httpRequestProcessor.submitGetRequest("http://203.92.40.186:8443/Sun3/Send_SMS2x?", null, queryParamMap, new SmsManager(queryParamMap));
       } catch (Exception e) {
    	   e.printStackTrace();
       }
       LOGGER.info("otp sent");
      return new ResponseEntity<>(HttpStatus.OK);
    }
    
   
}
