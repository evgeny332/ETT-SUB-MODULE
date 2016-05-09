
package com.web.rest.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

import com.domain.entity.User;
import com.repository.jpa.UserBlackListRepository;
import com.repository.jpa.UserRepository;
import com.rh.remote.SendGetRequest;
import com.service.EttApis;
import com.service.RechargeService;


/**
 * @author ankur
 */
@Controller
@RequestMapping(value = "/v1")
public class RedeemAmountController {

    private static Logger LOGGER = LoggerFactory.getLogger(RedeemAmountController.class);

   @Resource
    UserRepository userRepository;
    
    @Resource
    RechargeService rechargeService;
    
    @Resource
    UserBlackListRepository userBlackListRepository;

    @Resource
    EttApis ettApis;
    
    

    //@Resource
    //SendGetRequest sendGetRequest;
    
    @RequestMapping(value = "user/redeemAmount/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> redeemAmount(@RequestParam("ettId") long ettId,
    								@RequestParam("msisdn") long msisdn,
    								@RequestParam(value="operator", required=false, defaultValue="0") String operator,
    								@RequestParam("otp") int otp,
    								@RequestParam(value="tempOtp", required=false, defaultValue="0") int tempOtp
    ) throws IOException, ExecutionException, InterruptedException {
    	
    	LOGGER.info("API user/redeemAmount/v1 ettId={}, msisdn={}, operator={}, otp={}, tempOtp={}", ettId, msisdn, operator, otp, tempOtp);
    	
    	if(ettApis.getBlackListStatus(ettId)) {
			LOGGER.info("ettId BlackListed {}",ettId);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}


    	User user = userRepository.findByEttId(ettId);
    	if(user == null){
    		LOGGER.error("user not available in db ettId = "+ettId);
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      	}    	
    	if(user.isVerified() && user.getOtp()!=otp) {
    		LOGGER.error("user not authorized in db ettId = "+ettId);
    		ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
    	if(user.getOtp()!=otp) {
    			if(user.getTempOtp()!=tempOtp){
    				LOGGER.error("user not authorized in db ettId = "+ettId);
    				ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
    				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    			}
    		}
    	
    	String resp = rechargeService.getAppConfig().get("Redeem_Validate_Default_List");
    	if(rechargeService.getAppConfig().get("Redeem_Validate_Url_Check").equals("true")) {
		    	String url = rechargeService.getAppConfig().get("Redeem_Validate_Url_Local");
		    	url = url.replaceFirst("<msisdn>", msisdn+"");
		    	url = url.replaceFirst("<ettId>", ettId+"");
		    	long time1 = Calendar.getInstance().getTimeInMillis();
		    	SendGetRequest sendGetRequest = new SendGetRequest();
		    	resp = sendGetRequest.sendRequest(url,10000,10000);
		    	LOGGER.info("ettId={},msisdn={},RedeemValidateResp={},time={}",ettId,msisdn,resp,(Calendar.getInstance().getTimeInMillis()-time1));
    	}
    	
    	
    	List<String> list = Arrays.asList(resp.split(","));
    	return new ResponseEntity<>(list,HttpStatus.OK);
    }    
}