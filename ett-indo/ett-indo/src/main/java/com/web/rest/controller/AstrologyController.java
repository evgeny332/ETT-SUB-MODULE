package com.web.rest.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.joda.time.DateTime;
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

import com.domain.entity.Astrology;
import com.domain.entity.AstrologyClicks;
import com.domain.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.repository.jpa.AstrologyClicksRepository;
import com.repository.jpa.AstrologyRepository;
import com.repository.jpa.UserRepository;
import com.service.EttApis;
import com.service.OffersService;
import com.service.RechargeService;
import com.web.rest.dto.AstroData;
import com.web.rest.dto.AstroDto;
import com.web.rest.dto.CompressDto;

@Controller
@RequestMapping(value = "/v1")
public class AstrologyController {

	private static Logger LOGGER = LoggerFactory.getLogger(AstrologyController.class);
	
	@Autowired
	private AstrologyRepository astrologyRepository;
	
	@Autowired
	private AstrologyClicksRepository astrologyClicksRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Resource
	private EttApis ettApis;
	
	@Inject 
    private OffersService offersService;
	
	@Resource
	private RechargeService rechargeService;
	
	@RequestMapping(value = "user/getAstro/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> dataUsageEdr(@RequestParam("ettId") long ettId,
			@RequestParam("otp") int otp,
			@RequestParam(value="isCompressed", required=false, defaultValue="true") boolean isCompressed)
			throws IOException, ExecutionException, InterruptedException {
		
		LOGGER.info("API user/getAstro ettId={},otp={}",ettId,otp);
		
		User user = userRepository.findByEttId(ettId);
		if(!user.isVerified() || user.getOtp() != otp){
			LOGGER.warn("ettId="+ ettId+ "is not verified but still trying to fetch astro data");
			ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		Date dateToday = ettApis.getTodayDate(user);
		List<Astrology> astrologyList = astrologyRepository.findAllAstro(dateToday);
		
		if(astrologyList == null || astrologyList.size()<=0){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		try {
	    	if(rechargeService.getAppConfig().get("IsOfferDataCompress").equals("true") && isCompressed == true) {
		    	Object offerDto = getAstroDto(user, astrologyList);
		    	
		    	Gson gson = new GsonBuilder().serializeNulls().create();
		    	String json = gson.toJson(offerDto);
		    	byte [] compressed = offersService.compress(json);
		    	CompressDto compressDto = new CompressDto();
		    	compressDto.setIsCompress(true);
		    	compressDto.setCompressedData(compressed);
		    	return new ResponseEntity<>(compressDto, HttpStatus.OK);
	    	}
	    	else{
	    		return new ResponseEntity<>(getAstroDto(user, astrologyList), HttpStatus.OK);
	    	}
    	}catch(Exception ex){
    		return new ResponseEntity<>(getAstroDto(user, astrologyList), HttpStatus.OK);
    	}
	
	}
	
	// inclues Astrology click and Tarrot click also.
	@RequestMapping(value = "user/astroClick/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> dataUsageEdr(@RequestParam("ettId") long ettId,
			@RequestParam("otp") int otp,
			@RequestParam("sunshine") String sunshine,
			@RequestParam(value="type", required=false, defaultValue="1") int type)
			throws IOException, ExecutionException, InterruptedException {
		LOGGER.info("API user/astroClick ettId={},otp={},sunshine={},type={}",ettId,otp,sunshine,type);
		
		if(ettApis.getBlackListStatus(ettId)) {
			LOGGER.info("ettId BlackListed {}",ettId);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		User user = userRepository.findByEttId(ettId);
		if(!user.isVerified() || user.getOtp() != otp){
			LOGGER.warn("ettId="+ ettId+ "is not verified but still trying to send astro click");
			ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		AstrologyClicks astrologyClicks = new AstrologyClicks();
		astrologyClicks.setEttId(user.getEttId());
		astrologyClicks.setSunshine(sunshine);
		astrologyClicks.setType(type);
		if(sunshine.indexOf("utm_source=ETT")>-1) {
			astrologyClicks.setClickType(0);
		}
		else{
			astrologyClicks.setClickType(1);
		}
		
		astrologyClicksRepository.save(astrologyClicks);
		LOGGER.info("AstroClick saved of ettId={}",user.getEttId());
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private Object getAstroDto(User user, List<Astrology> astrologyList) {
		AstroDto astroDto = new AstroDto();
		List<AstroData> astroList = new ArrayList<>(astrologyList.size());
		for(Astrology astrology : astrologyList){
			AstroData astroData = new AstroData();
			astroData.setSunshine(astrology.getSunshine());
			astroData.setDescription(astrology.getDescription());
			astroData.setShareImageUrl(astrology.getShareImageUrl());
			astroList.add(astroData);
		}
		astroDto.setAstroData(astroList);
		astroDto.setText(rechargeService.getAppConfig().get("astroText"));
		return astroDto;
	}
	
}
