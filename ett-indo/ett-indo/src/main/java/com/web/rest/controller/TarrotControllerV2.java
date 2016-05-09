package com.web.rest.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.apache.tools.tar.TarOutputStream;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.entity.DataUsagePendingCredits;
import com.domain.entity.Tarrot;
import com.domain.entity.TarrotConfig;
import com.domain.entity.User;
import com.domain.entity.UserAccountSummary;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.repository.jpa.DataUsagePendingCreditsRepository;
import com.repository.jpa.DeviceTokenRepository;
import com.repository.jpa.TarrotConfigRepository;
import com.repository.jpa.TarrotRepository;
import com.repository.jpa.UserAccountRepository;
import com.repository.jpa.UserAccountSummaryRepository;
import com.repository.jpa.UserRepository;
import com.service.CustomDatabaseService;
import com.service.DataUsageService;
import com.service.EttApis;
import com.service.OffersService;
import com.service.RechargeService;
import com.web.rest.dto.CompressDto;
import com.web.rest.dto.TarrotConfigData;
import com.web.rest.dto.TarrotConfigDto;
import com.web.rest.dto.TarrotDto;

/*
 * @author ankit
 */

@Controller
@RequestMapping(value = "/v2")
public class TarrotControllerV2 {
	
	private static Logger LOGGER = LoggerFactory.getLogger(TarrotControllerV2.class);

	@Autowired
	TarrotConfigRepository tarrotConfigRepository;
	
	@Autowired
	TarrotRepository tarrotRepository;
	
	@Autowired
	EttApis ettApis;
	
	@Autowired
	UserRepository userRepository;

	@Resource
	CustomDatabaseService customDatabaseService;
	
	@Resource
	RechargeService rechargeService;
	
	@Resource
	OffersService offersService;
	
	
	@RequestMapping(value = "user/TarrotConfig/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> tarrotConfig(@RequestParam("ettId") long ettId,
			@RequestParam("otp") int otp,
			@RequestParam(value="isCompressed", required=false, defaultValue="true") boolean isCompressed)
			throws IOException, ExecutionException, InterruptedException {
		
		LOGGER.info("API user/TarrotConfig ettId={},otp={},isCompressed",ettId,otp,isCompressed);
		
		if(ettApis.getBlackListStatus(ettId)) {
			LOGGER.info("ettId BlackListed {}",ettId);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		User user = userRepository.findByEttId(ettId);
		if(!user.isVerified() || user.getOtp() != otp){
			LOGGER.warn("ettId="+ ettId+ "is not verified but still trying to send TarrotConfig");
			ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		List<TarrotConfig> tarrotConfig = tarrotConfigRepository.findAll();
		TarrotConfigDto configDto = new TarrotConfigDto();
		List<TarrotConfigData> configDatas = new ArrayList<TarrotConfigData>(tarrotConfig.size());
		for(TarrotConfig tarrotConfig2:tarrotConfig) {
			TarrotConfigData configData = new TarrotConfigData();
			configData.setId(tarrotConfig2.getId());
			configData.setImgUrl(tarrotConfig2.getImgUrl());
			configData.setInfo(tarrotConfig2.getInfo());
			configData.setName(tarrotConfig2.getName());
			configData.setUrlValue(tarrotConfig2.getUrlValue());
			configDatas.add(configData);
		}
		configDto.setTarrotConfigData(configDatas);
		configDto.setText(rechargeService.getLocaleTextTemplate().get("tarrotText_"+user.getLocale()));
		
		try {
	    	if(rechargeService.getAppConfig().get("IsOfferDataCompress").equals("true") && isCompressed == true) {
		    	
		    	Gson gson = new GsonBuilder().serializeNulls().create();
		    	String json = gson.toJson(configDto);
		    	byte [] compressed = offersService.compress(json);
		    	CompressDto compressDto = new CompressDto();
		    	compressDto.setIsCompress(true);
		    	compressDto.setCompressedData(compressed);
		    	return new ResponseEntity<>(compressDto, HttpStatus.OK);
	    	}
	    	else{
	    		return new ResponseEntity<>(configDto, HttpStatus.OK);
	    	}
    	}catch(Exception ex){
    		return new ResponseEntity<>(configDto, HttpStatus.OK);
    	}
	}



	
	@RequestMapping(value = "user/Tarrot/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> TarrotConfig(@RequestParam("ettId") long ettId,
			@RequestParam("otp") int otp,
			@RequestParam(value="isCompressed", required=false, defaultValue="true") boolean isCompressed,
			@RequestParam(value="update_id", required=false) List<Long> update_id,
			@RequestParam("tarrotName") String tarrotName)
			throws IOException, ExecutionException, InterruptedException {
		
		LOGGER.info("API user/Tarrot ettId={},otp={},tarrotName{},isCompressed{}",ettId,otp,tarrotName,isCompressed);
		
		if(ettApis.getBlackListStatus(ettId)) {
			LOGGER.info("ettId BlackListed {}",ettId);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		User user = userRepository.findByEttId(ettId);
		if(!user.isVerified() || user.getOtp() != otp){
			LOGGER.warn("ettId="+ ettId+ "is not verified but still trying to send Tarrot");
			ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		List<TarrotDto> tarrotDto = customDatabaseService.getTarrotDto(tarrotName,update_id);
		/*List<TarrotDto> configDto = new ArrayList<TarrotDto>(tarrots.size());
		for(Tarrot tarrot:tarrots){
			TarrotDto dto = new TarrotDto();
			dto.setId(tarrot.getId());
			dto.setDetail(tarrot.getDetail());
			dto.setTitle(tarrot.getCardName());
			dto.setValue(tarrot.getAskValue());
			configDto.add(dto);
		}*/
		
		
		try {
	    	if(rechargeService.getAppConfig().get("IsOfferDataCompress").equals("true") && isCompressed == true) {
		    	Gson gson = new Gson();
		    	String json = gson.toJson(tarrotDto);
		    	byte [] compressed = offersService.compress(json);
		    	CompressDto compressDto = new CompressDto();
		    	compressDto.setIsCompress(true);
		    	compressDto.setCompressedData(compressed);
		    	return new ResponseEntity<>(compressDto, HttpStatus.OK);
	    	}
	    	else{
	    		return new ResponseEntity<>(tarrotDto, HttpStatus.OK);
	    	}
    	}catch(Exception ex){
    		return new ResponseEntity<>(tarrotDto, HttpStatus.OK);
    	}
	}

}

