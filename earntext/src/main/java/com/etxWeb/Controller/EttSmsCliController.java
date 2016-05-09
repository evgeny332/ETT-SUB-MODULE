package com.etxWeb.Controller;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.etxWeb.config.Config;
import com.etxWeb.dto.CompressDto;
import com.etxWeb.dto.SmsCliDto;
import com.etxWeb.entity.active.SmsCliCapture;
import com.etxWeb.repository.active.SmsCliCaptureRepository;
import com.extWeb.service.impl.RuleCheckServiceImpl;
import com.google.gson.Gson;

@RestController
@EnableAutoConfiguration
@RequestMapping(value="/smsCli/v1/")
public class EttSmsCliController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(EttSmsCliController.class);

	@Autowired
	Config config;
	
	@Autowired
	SmsCliCaptureRepository smsCliCaptureRepository;
	
	@RequestMapping(value = "user/SmsCliCapture/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> getSmsCliCapture(
			@RequestParam(value="cli", required=false, defaultValue="") final String cli,
			@RequestParam(value="count", required=false, defaultValue="1" ) final int count,
			@RequestParam("ettId") long ettId,
			@RequestParam("otp") int otp,
			@RequestParam(value="isCompressed", required=false, defaultValue="true") boolean isCompressed
			
			)throws IOException, ExecutionException, InterruptedException {
				
				LOGGER.info("SmsCliCapture ettId={},otp={},cli={},count={},isCompressed={}",ettId,otp,cli,count,isCompressed);
				SmsCliCapture smsCliCapture = new SmsCliCapture();
				smsCliCapture.setCli(cli);
				smsCliCapture.setCount(count);
				smsCliCapture.setCreatedTime(new Date());
				smsCliCapture.setEttId(ettId);
				smsCliCaptureRepository.save(smsCliCapture);
				
				SmsCliDto smsCliDto = new SmsCliDto();
				smsCliDto.setNextStatus(config.getNextStatus());
				smsCliDto.setCliList(config.getCli());
				Gson gson = new Gson();
				String json = gson.toJson(smsCliDto);
				if(isCompressed) {
					try {
						RuleCheckServiceImpl ruleCheckService = new RuleCheckServiceImpl();
						byte [] compressed = ruleCheckService.compress(json);
						CompressDto compressDto = new CompressDto();
						compressDto.setIsCompress(true);
						compressDto.setCompressedData(compressed);
						return new ResponseEntity<>(compressDto,HttpStatus.OK);
					}catch(Exception ex){
						return new ResponseEntity<>(smsCliDto,HttpStatus.OK);
					}
				}
				else {
					return new ResponseEntity<>(smsCliDto,HttpStatus.OK);
				}
			}
			
}
