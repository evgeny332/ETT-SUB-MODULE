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
import com.domain.entity.EttVideos;
import com.domain.entity.Tarrot;
import com.domain.entity.TarrotConfig;
import com.domain.entity.User;
import com.domain.entity.UserAccountSummary;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.repository.jpa.DataUsagePendingCreditsRepository;
import com.repository.jpa.DeviceTokenRepository;
import com.repository.jpa.EttVideosRepository;
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
import com.web.rest.dto.EttVideoDto;
import com.web.rest.dto.EttVideoListDto;
import com.web.rest.dto.TarrotConfigData;
import com.web.rest.dto.TarrotConfigDto;
import com.web.rest.dto.TarrotDto;

/*
 * @author ankit
 */

@Controller
@RequestMapping(value = "/v2")
public class VideoController2 {
	
	private static Logger LOGGER = LoggerFactory.getLogger(VideoController2.class);

	@Autowired
	EttVideosRepository ettVideosRepository;
	
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
	
	
	@RequestMapping(value = "user/videoPlayList/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> videoPlayList(@RequestParam("ettId") long ettId,
			@RequestParam("otp") int otp,
			@RequestParam(value="player", required=false, defaultValue="youtube") String player,
			@RequestParam(value="isCompressed", required=false, defaultValue="true") boolean isCompressed)
			throws IOException, ExecutionException, InterruptedException {
		
		LOGGER.info("API user/VideoPlayList ettId={},otp={},player={}isCompressed{}",ettId,otp,player,isCompressed);
		
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
		
		List<String> vidioPlayList = ettVideosRepository.findPlayList();
		
		
		try {
	    	if(rechargeService.getAppConfig().get("IsOfferDataCompress").equals("true") && isCompressed == true) {
		    	
		    	Gson gson = new GsonBuilder().serializeNulls().create();
		    	String json = gson.toJson(vidioPlayList);
		    	byte [] compressed = offersService.compress(json);
		    	CompressDto compressDto = new CompressDto();
		    	compressDto.setIsCompress(true);
		    	compressDto.setCompressedData(compressed);
		    	return new ResponseEntity<>(compressDto, HttpStatus.OK);
	    	}
	    	else{
	    		return new ResponseEntity<>(vidioPlayList, HttpStatus.OK);
	    	}
    	}catch(Exception ex){
    		return new ResponseEntity<>(vidioPlayList, HttpStatus.OK);
    	}
	}



	
	@RequestMapping(value = "user/videolist/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> videoList(@RequestParam("ettId") long ettId,
			@RequestParam("otp") int otp,
			@RequestParam("playlist") String playlist,
			@RequestParam("page") int page,
			@RequestParam(value="update_id", required=false) List<Long> update_id,
			@RequestParam(value="isCompressed", required=false, defaultValue="true") boolean isCompressed)
			throws IOException, ExecutionException, InterruptedException {
		
		LOGGER.info("API user/Tarrot ettId={},otp={},playlist={},page={},isCompressed={},update_id={}",ettId,otp,playlist,page,isCompressed,update_id);
		int defaultPageVidio=25;
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
		
		List<EttVideos> ettVideos = ettVideosRepository.findByPlayList(playlist);
		int startPage = 0;
		int endPage = defaultPageVidio;

		if(page>1) {
			startPage = (defaultPageVidio*(page-1));
			endPage = defaultPageVidio*page;
	
		}
			if(ettVideos.size()<=startPage) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			if(ettVideos.size()<endPage) {
				endPage=ettVideos.size();
			}
		
		List<EttVideoListDto> videoListDtos = new ArrayList<EttVideoListDto>(defaultPageVidio);
		EttVideoDto ettVideoDto = new EttVideoDto();
		if(ettVideos.size()>endPage) {
			ettVideoDto.setNextAvailable(true);
		}
		for(int i=startPage;i<endPage;i++) {
			EttVideoListDto ettVideoListDto = new EttVideoListDto();
			ettVideoListDto.setUpdate_id(ettVideos.get(i).getId());
			if(update_id.contains(ettVideos.get(i).getId())){
				videoListDtos.add(ettVideoListDto);
				continue;
			}
			ettVideoListDto.setImageUrl(ettVideos.get(i).getImgUrl());
			ettVideoListDto.setTitle(ettVideos.get(i).getTitle());
			ettVideoListDto.setVideoId(ettVideos.get(i).getVidioId());
			videoListDtos.add(ettVideoListDto);
		}
		ettVideoDto.setEttVideoListDto(videoListDtos);
			
		try {
	    	if(rechargeService.getAppConfig().get("IsOfferDataCompress").equals("true") && isCompressed == true) {
		    	
		    	Gson gson = new GsonBuilder().serializeNulls().create();
		    	String json = gson.toJson(ettVideoDto);
		    	byte [] compressed = offersService.compress(json);
		    	CompressDto compressDto = new CompressDto();
		    	compressDto.setIsCompress(true);
		    	compressDto.setCompressedData(compressed);
		    	return new ResponseEntity<>(compressDto, HttpStatus.OK);
	    	}
	    	else{
	    		return new ResponseEntity<>(ettVideoDto, HttpStatus.OK);
	    	}
    	}catch(Exception ex){
    		return new ResponseEntity<>(ettVideoDto, HttpStatus.OK);
    	}
	}

}

 