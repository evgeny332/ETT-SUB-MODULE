package com.web.rest.controller;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.entity.Feedback;
import com.repository.jpa.FeedbackRepository;
import com.repository.jpa.RateRepository;
import com.repository.jpa.UserBlackListRepository;
import com.service.EttApis;
import com.service.RechargeService;

/**
 * @author ankit jain
 */
@Controller
@RequestMapping(value = "/v1")
public class FeedbackController {

    private static Logger LOGGER = LoggerFactory.getLogger(FeedbackController.class);

    @Inject
    FeedbackRepository feedbackRepository;

    @Inject
    RateRepository rateRepository;

    @Resource
    UserBlackListRepository userBlackListRepository;

    @Resource
    EttApis ettApis;
    
    @Resource
    RechargeService rechargeService;

    
    @RequestMapping(value = "feedback/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> login(@RequestParam("ettId") long ettId,
    							   @RequestParam("feedbackType") String feedbackType,
                                   @RequestParam(value = "feedback") String feedBackString,
                                   @RequestParam(value= "rating" , required = false, defaultValue="") final String rating,
                                   @RequestParam(value = "name" , required = false, defaultValue="") final String name,
                                   @RequestParam(value = "email" , required = false, defaultValue="") final String email,
                                   @RequestParam(value = "msisdn" , required = false, defaultValue="") final String msisdn
    ){
        LOGGER.info("API Feedback received ettId={},feedbackType={},feedBackString={},rating{},name{},email{},msisdn{}",ettId,feedbackType,feedBackString,rating,name,email,msisdn);
        if(ettApis.getBlackListStatus(ettId)) {
			LOGGER.info("ettId BlackListed {}",ettId);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

        Feedback feedback = new Feedback();
        feedback.setEttId(ettId);
        feedback.setFeedbackType(feedbackType);
        feedback.setFeedback(feedBackString);
        feedback.setRate(rating);
        feedback.setName(name);
        feedback.setEmail(email);
        feedback.setMsisdn(msisdn);
        feedbackRepository.save(feedback);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}