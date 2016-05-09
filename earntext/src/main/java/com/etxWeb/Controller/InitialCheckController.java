
package com.etxWeb.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.etxWeb.config.Config;
import com.etxWeb.config.ReSetVar;
import com.etxWeb.dto.DNDRespDncIndia;
import com.etxWeb.dto.OnScreenCheck;
import com.etxWeb.dto.SmsSubResp;
import com.etxWeb.service.RuleCheckService;
import com.extWeb.service.impl.RuleCheckServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.etxWeb.dto.CompressDto;
import com.etxWeb.dto.EarnTxtCheckDto;
import com.etxWeb.entity.passive.EtxMsgSubmitDetails;



@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/etx/v1")
public class InitialCheckController {

	private static Logger LOGGER = LoggerFactory.getLogger(InitialCheckController.class);
   
    @Autowired
    private Config config;
   
    private ReSetVar reSetVar=new ReSetVar(); 
    
    @Autowired
    private EtxMsgSubmitDetails etxMsgSubmitDetailsRepository;
    
    //private AtomicInteger count = new AtomicInteger();
    /*@Autowired
    private RuleCheckServiceImpl checkService;
    */
    @RequestMapping("/")
	String home(){
    	//System.out.println(config.getDayLimitApi());
    	return "Hello World";
	}
	
	
	/*@RequestMapping(value = "user/EtxOnScreenCheck/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> etxOnScreenCheck(
			@RequestHeader(value="User-Agent", required=false, defaultValue="UNKNOWN") String userAgent,
			@RequestHeader(value="X-FORWARDED-FOR", required=false, defaultValue="UNKNOWN") String CIP,
			@RequestHeader(value="X-Forwarded-Proto", required=false, defaultValue="UNKNOWN") String Proto,
			@RequestParam(value="isRH", required=false, defaultValue="0") int isRH,
			@RequestParam("ettId") long ettId,
			@RequestParam("otp") int otp,
			@RequestParam(value="isCompressed", required=false, defaultValue="true") boolean isCompressed,
			@RequestParam(value = "tempParam", required = false, defaultValue = "") final String tempParam) 
			throws IOException, ExecutionException, InterruptedException {
		
		LOGGER.info("API user/EtxOnScreenCheck/ userAgent={}, CIP={}, Proto={}, ettId={}, otp={},isRH={},isCompressed={}",userAgent,CIP,Proto,ettId,otp,isRH,isCompressed);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;
		try {
		//response = restTemplate.getForEntity("http://api.earntalktime.com/ett/api/v1/user/EtxOnScreenCheck/?ettId="+ettId+"&otp="+otp+"&tempParam=test1&isRH=1&isCompressed="+isCompressed,String.class);
		RuleCheckService ruleCheckService = new RuleCheckServiceImpl();
		String api = config.getOnScreenRuleCheckApi().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#ISCOMPRESSED#", isCompressed+"");
		response = ruleCheckService.urlCall(api,config.getOnScreenRuleCheckApiRto(),config.getOnScreenRuleCheckApiCto());
		//response = ruleCheckService.urlCall("http://api.earntalktime.com/ett/api/v1/user/EtxOnScreenCheck/?ettId="+ettId+"&otp="+otp+"&tempParam=test1&isRH=1&isCompressed="+isCompressed);
		if(response.getStatusCode()==HttpStatus.OK) {
			return new ResponseEntity<>(response.getBody(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		}catch(HttpClientErrorException hcee) {
			return new ResponseEntity<>(hcee.getStatusCode());
		}
		catch(Exception e) {
			
			throw e;
		}
		
	}
*/

	@RequestMapping(value = "user/EtxOnScreenCheck/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> etxOnScreenCheck(
			@RequestHeader(value="User-Agent", required=false, defaultValue="UNKNOWN") String userAgent,
			@RequestHeader(value="X-FORWARDED-FOR", required=false, defaultValue="UNKNOWN") String CIP,
			@RequestHeader(value="X-Forwarded-Proto", required=false, defaultValue="UNKNOWN") String Proto,
			@RequestParam(value="isRH", required=false, defaultValue="0") int isRH,
			@RequestParam("ettId") long ettId,
			@RequestParam("otp") int otp,
			@RequestParam(value="isCompressed", required=false, defaultValue="true") boolean isCompressed,
			@RequestParam(value = "tempParam", required = false, defaultValue = "") final String tempParam) 
			throws IOException, ExecutionException, InterruptedException {
		
		LOGGER.info("API user/EtxOnScreen/ userAgent={}, CIP={}, Proto={}, ettId={}, otp={},isRH={},isCompressed={}",userAgent,CIP,Proto,ettId,otp,isRH,isCompressed);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;
		try {
		//response = restTemplate.getForEntity("http://api.earntalktime.com/ett/api/v1/user/EtxOnScreenCheck/?ettId="+ettId+"&otp="+otp+"&tempParam=test1&isRH=1&isCompressed="+isCompressed,String.class);
		RuleCheckServiceImpl ruleCheckService = new RuleCheckServiceImpl();
		String api = config.getOnScreenRuleCheckApi().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#ISCOMPRESSED#", isCompressed+"");
		response = ruleCheckService.urlCall(api,config.getOnScreenRuleCheckApiRto(),config.getOnScreenRuleCheckApiCto());
		//System.out.println(response);
		//response = ruleCheckService.urlCall("http://api.earntalktime.com/ett/api/v1/user/EtxOnScreenCheck/?ettId="+ettId+"&otp="+otp+"&tempParam=test1&isRH=1&isCompressed="+isCompressed);
		
		if(response.getStatusCode()==HttpStatus.OK) {
			Gson gson = new Gson();
			OnScreenCheck onScreenCheck = gson.fromJson(response.getBody(),OnScreenCheck.class);
			List <EarnTxtCheckDto> earnTxtCheckDtos = new ArrayList<EarnTxtCheckDto>();
			if(ruleCheckService.checkRedeemCount(onScreenCheck.getRedeemCount(),config.getRedeemCountLimit())) {
				EarnTxtCheckDto earnTxtCheckDto = new EarnTxtCheckDto();
				earnTxtCheckDto.setCheckName("Redeem Count");
				earnTxtCheckDto.setMsg("");
				earnTxtCheckDto.setStatus(1);
				earnTxtCheckDtos.add(earnTxtCheckDto);
			}
			else {
				EarnTxtCheckDto earnTxtCheckDto = new EarnTxtCheckDto();
				earnTxtCheckDto.setCheckName("Redeem Count");
				earnTxtCheckDto.setMsg(config.getRedeemCountLimitText());
				earnTxtCheckDto.setStatus(0);
				earnTxtCheckDtos.add(earnTxtCheckDto);
			}
			if(ruleCheckService.checkValidateDate(onScreenCheck.getLastValidationDate(),config.getOtpExpiryLimit())){
				EarnTxtCheckDto earnTxtCheckDto = new EarnTxtCheckDto();
				earnTxtCheckDto.setCheckName("OTP Check");
				earnTxtCheckDto.setMsg("");
				earnTxtCheckDto.setStatus(1);
				earnTxtCheckDtos.add(earnTxtCheckDto);
				
			}
			else {
				EarnTxtCheckDto earnTxtCheckDto = new EarnTxtCheckDto();
				earnTxtCheckDto.setCheckName("OTP Check");
				earnTxtCheckDto.setMsg(config.getOtpExpiryLimitText());
				earnTxtCheckDto.setStatus(0);
				earnTxtCheckDtos.add(earnTxtCheckDto);
				}
			gson = new Gson();
			String json = gson.toJson(earnTxtCheckDtos);
			if(isCompressed) {
				try {
					byte [] compressed = ruleCheckService.compress(json);
					CompressDto compressDto = new CompressDto();
					compressDto.setIsCompress(true);
					compressDto.setCompressedData(compressed);
					LOGGER.info("Response ettId={},resp={}",ettId,json);
					return new ResponseEntity<>(compressDto, HttpStatus.OK);
				}
				catch(Exception exception){
					LOGGER.info("Response ettId={},resp={}",ettId,json);
					return new ResponseEntity<>(json,HttpStatus.OK);
				}
			}
			else {
					LOGGER.info("Response ettId={},resp={}",ettId,json);
					return new ResponseEntity<>(json,HttpStatus.OK);
			}
		}
		else {
			LOGGER.info("Response ettId={},resp={}",ettId,HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		}catch(HttpClientErrorException hcee) {
			LOGGER.info("Response ettId={},resp={}",ettId,hcee);
			return new ResponseEntity<>(hcee.getStatusCode());
		}
		catch(Exception e) {
			LOGGER.info("Response ettId={},resp={}",ettId,e);
			throw e;
		}
		
	}

	@RequestMapping(value = "user/OptEtx/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getOptEtx(
			@RequestHeader(value="User-Agent", required=false, defaultValue="UNKNOWN") String userAgent,
			@RequestHeader(value="X-FORWARDED-FOR", required=false, defaultValue="UNKNOWN") String CIP,
			@RequestHeader(value="X-Forwarded-Proto", required=false, defaultValue="UNKNOWN") String Proto,
			@RequestParam(value="isRH", required=false, defaultValue="0") int isRH,
			@RequestParam("ettId") long ettId,
			@RequestParam("msisdn") long msisdn,
			@RequestParam("otp") int otp,
			@RequestParam(value="isCompressed", required=false, defaultValue="true") boolean isCompressed,
			@RequestParam(value = "tempParam", required = false, defaultValue = "") final String tempParam) 
			throws IOException, ExecutionException, InterruptedException {
	
		LOGGER.info("API user/OptEtx/ userAgent={}, CIP={}, Proto={}, ettId={}, otp={},msisdn={},isRH={},isCompressed={}",userAgent,CIP,Proto,ettId,otp,msisdn,isRH,isCompressed);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;
		try {
			RuleCheckServiceImpl ruleCheckService = new RuleCheckServiceImpl();
			String api = config.getOtpRequesstApi().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#MSISDN#", msisdn+"");
			response = ruleCheckService.urlCall(api,config.getOtpRequesstApiRto(),config.getOtpRequesstApiCto());
			//response = ruleCheckService.urlCall("http://api.earntalktime.com/ett/api/v1/user/OptEtx/?ettId="+ettId+"&otp="+otp+"&tempParam=test1&isRH=1&isCompressed=false&msisdn="+msisdn);
			LOGGER.info(response.getStatusCode()+","+response.getBody());
			if(response.getStatusCode()==HttpStatus.OK) {
					api = config.getOtpRequesstApiSendSMS().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#MSISDN#", msisdn+"").replace("#OTP_KEY#", response.getBody().replaceAll("\"", ""));
					String response1 = ruleCheckService.sendRequest(api,config.getSmsPushUrlRto(),config.getSmsPushUrlCto());
					LOGGER.info("Response ettId={},resp={}",ettId,response1);
					return new ResponseEntity<>(HttpStatus.OK);	
				}
			else {
				LOGGER.info("Response ettId={},resp={}",ettId,HttpStatus.UNAUTHORIZED);
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}catch(HttpClientErrorException hcee) {
			LOGGER.info("Response ettId={},resp={}",ettId,hcee);
			return new ResponseEntity<>(hcee.getStatusCode());
		}
	}
	
	@RequestMapping(value = "user/OptEtx/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> setOptEtx(
			@RequestHeader(value="User-Agent", required=false, defaultValue="UNKNOWN") String userAgent,
			@RequestHeader(value="X-FORWARDED-FOR", required=false, defaultValue="UNKNOWN") String CIP,
			@RequestHeader(value="X-Forwarded-Proto", required=false, defaultValue="UNKNOWN") String Proto,
			@RequestParam(value="isRH", required=false, defaultValue="0") int isRH,
			@RequestParam("ettId") long ettId,
			@RequestParam("otpEtx") String otpEtx,
			@RequestParam("otp") int otp,
			@RequestParam(value="isCompressed", required=false, defaultValue="true") boolean isCompressed,
			@RequestParam(value = "tempParam", required = false, defaultValue = "") final String tempParam) 
			throws IOException, ExecutionException, InterruptedException {
			
			LOGGER.info("API user/OptEtx/ userAgent={}, CIP={}, Proto={}, ettId={}, otp={},otpEtx={},isRH={},isCompressed={}",userAgent,CIP,Proto,ettId,otp,otpEtx,isRH,isCompressed);
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = null;
			try {
				//response = restTemplate.getForEntity("http://api.earntalktime.com/ett/api/v1/user/OptEtx/?ettId="+ettId+"&otp="+otp+"&tempParam=test1&isRH=1&isCompressed=false&otpEtx="+otpEtx,String.class);
				RuleCheckServiceImpl ruleCheckService = new RuleCheckServiceImpl();
				//response = ruleCheckService.urlCallPost("http://api.earntalktime.com/ett/api/v1/user/OptEtx/?ettId="+ettId+"&otp="+otp+"&tempParam=test1&isRH=1&isCompressed=false&otpEtx="+otpEtx);
				String api[] = config.getOtpRequstCheckApi().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#OTPETX#", otpEtx+"").split("\\$");
				int responseCode = ruleCheckService.sendPOST(api[0],api[1],config.getOtpRequstCheckApiRto(),config.getOtpRequstCheckApiCto());
				//ruleCheckService.sendPOST("http://api.earntalktime.com/ett/api/v1/user/OptEtx/?","ettId="+ettId+"&otp="+otp+"&tempParam=test1&isRH=1&isCompressed=false&otpEtx="+otpEtx,10000,10000);
				/*if(response.getStatusCode()==HttpStatus.OK) {
					return new ResponseEntity<>(response.getBody(),HttpStatus.OK);
				}
				else {
				
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}*/
				if(responseCode==200) {
					LOGGER.info("Response ettId={},resp={}",ettId,HttpStatus.OK);
					return new ResponseEntity<>(HttpStatus.OK);
				}
				else {
					LOGGER.info("Response ettId={},resp={}",ettId,HttpStatus.UNAUTHORIZED);
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}
			}catch(HttpClientErrorException hcee) {
				LOGGER.info("Response ettId={},resp={}",ettId,hcee);
				return new ResponseEntity<>(hcee.getStatusCode());
			}
	}
	
	@RequestMapping(value = "user/SmsSubmit/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> smsSubmit(
			@RequestHeader(value="User-Agent", required=false, defaultValue="UNKNOWN") String userAgent,
			@RequestHeader(value="X-FORWARDED-FOR", required=false, defaultValue="UNKNOWN") String CIP,
			@RequestHeader(value="X-Forwarded-Proto", required=false, defaultValue="UNKNOWN") String Proto,
			@RequestParam(value="isRH", required=false, defaultValue="0") int isRH,
			@RequestParam("ettId") long ettId,
			@RequestParam("otpEtx") String otpEtx,
			@RequestParam("otp") int otp,
			@RequestParam("sms") String sms,
			@RequestParam("msisdn") String msisdn,
			@RequestParam("source") String source,
			@RequestParam(value="isCompressed", required=false, defaultValue="true") boolean isCompressed,
			@RequestParam(value = "tempParam", required = false, defaultValue = "") final String tempParam) 
			throws IOException, ExecutionException, InterruptedException {
			
			LOGGER.info("API user/SmsSubmit/ userAgent={}, CIP={}, Proto={}, ettId={}, otp={},sms={},msisdn={},isRH={},isCompressed={}",userAgent,CIP,Proto,ettId,otp,sms,msisdn,isRH,isCompressed);
			RuleCheckServiceImpl ruleCheckService = new RuleCheckServiceImpl();
			try {
				//Same number
				if(source.equals(msisdn)){
					SmsSubResp smsSubResp = new SmsSubResp();
					smsSubResp.setMsg(config.getSmsSubmitError());
					smsSubResp.setStatus("0");
					Gson gson = new Gson();
					String json = gson.toJson(smsSubResp);
					if(isCompressed) {
						try {
							byte [] compressed = ruleCheckService.compress(json);
							CompressDto compressDto = new CompressDto();
							compressDto.setIsCompress(true);
							compressDto.setCompressedData(compressed);
							String api2 [] = config.getSmsDetailsReport().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#OTPETX#", otpEtx).replace("#MSISDN#", msisdn+"").replace("#DATA#", sms).replace("#RESP#", "0").replace("#STATUS#", "0").split("\\$");
			                ruleCheckService.sendPOST(api2[0],api2[1],config.getSmsDetailsReportRto(),config.getSmsDetailsReportCto());
							LOGGER.info("Response ettId={},resp={}",ettId,json);
							return new ResponseEntity<>(compressDto, HttpStatus.OK);
						}
						catch(Exception exception){
							String api2 [] = config.getSmsDetailsReport().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#OTPETX#", otpEtx).replace("#MSISDN#", msisdn+"").replace("#DATA#", sms).replace("#RESP#", "0").replace("#STATUS#", "0").split("\\$");
			                ruleCheckService.sendPOST(api2[0],api2[1],config.getSmsDetailsReportRto(),config.getSmsDetailsReportCto());
							LOGGER.info("Response ettId={},resp={}",ettId,json);
							return new ResponseEntity<>(json,HttpStatus.OK);
						}
					}
				}
				
				//DND Hour check
				if(ruleCheckService.chekDNDHour(config.getDndInitialHour(),config.getDndOverHour())) {
				//if(false) {
					//System.out.println("DND hour");
					SmsSubResp smsSubResp = new SmsSubResp();
					smsSubResp.setMsg(config.getDndHourCheckFailText());
					smsSubResp.setStatus("4");
					Gson gson = new Gson();
					String json = gson.toJson(smsSubResp);
					if(isCompressed) {
						try {
							byte [] compressed = ruleCheckService.compress(json);
							CompressDto compressDto = new CompressDto();
							compressDto.setIsCompress(true);
							compressDto.setCompressedData(compressed);
							String api2 [] = config.getSmsDetailsReport().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#OTPETX#", otpEtx).replace("#MSISDN#", msisdn+"").replace("#DATA#", sms).replace("#RESP#", "0").replace("#STATUS#", "4").split("\\$");
							ruleCheckService.sendPOST(api2[0],api2[1],config.getSmsDetailsReportRto(),config.getSmsDetailsReportCto());
							LOGGER.info("Response ettId={},resp={}",ettId,json);
							return new ResponseEntity<>(compressDto, HttpStatus.OK);
						}
						catch(Exception exception){
							String api2 [] = config.getSmsDetailsReport().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#OTPETX#", otpEtx).replace("#MSISDN#", msisdn+"").replace("#DATA#", sms).replace("#RESP#", "0").replace("#STATUS#", "4").split("\\$");
							ruleCheckService.sendPOST(api2[0],api2[1],config.getSmsDetailsReportRto(),config.getSmsDetailsReportCto());
							LOGGER.info("Response ettId={},resp={}",ettId,json);
							return new ResponseEntity<>(json,HttpStatus.OK);
						}
					}
					else {
							String api2 [] = config.getSmsDetailsReport().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#OTPETX#", otpEtx).replace("#MSISDN#", msisdn+"").replace("#DATA#", sms).replace("#RESP#", "0").replace("#STATUS#", "4").split("\\$");
							ruleCheckService.sendPOST(api2[0],api2[1],config.getSmsDetailsReportRto(),config.getSmsDetailsReportCto());
							LOGGER.info("Response ettId={},resp={}",ettId,json);
							return new ResponseEntity<>(json,HttpStatus.OK);
					}
					//return new ResponseEntity<>(smsSubResp,HttpStatus.OK);
				}
				//####Tempory Closed
				if(reSetVar.getErrorCheckCounter().get()>=10){
					SmsSubResp smsSubResp = new SmsSubResp();
					smsSubResp.setMsg(config.getSmsDownText());
					smsSubResp.setStatus("6");
					Gson gson = new Gson();
					String json = gson.toJson(smsSubResp);
					if(isCompressed) {
						try {
							byte [] compressed = ruleCheckService.compress(json);
							CompressDto compressDto = new CompressDto();
							compressDto.setIsCompress(true);
							compressDto.setCompressedData(compressed);
							String api2 [] = config.getSmsDetailsReport().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#OTPETX#", otpEtx).replace("#MSISDN#", msisdn+"").replace("#DATA#", sms).replace("#RESP#", "0").replace("#STATUS#", "6").split("\\$");
							ruleCheckService.sendPOST(api2[0],api2[1],config.getSmsDetailsReportRto(),config.getSmsDetailsReportCto());
							LOGGER.info("Response ettId={},resp={}",ettId,json);
							return new ResponseEntity<>(compressDto, HttpStatus.OK);
						}
						catch(Exception exception){
							String api2 [] = config.getSmsDetailsReport().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#OTPETX#", otpEtx).replace("#MSISDN#", msisdn+"").replace("#DATA#", sms).replace("#RESP#", "0").replace("#STATUS#", "6").split("\\$");
							ruleCheckService.sendPOST(api2[0],api2[1],config.getSmsDetailsReportRto(),config.getSmsDetailsReportCto());
							LOGGER.info("Response ettId={},resp={}",ettId,json);
							return new ResponseEntity<>(json,HttpStatus.OK);
						}
					}
					else {
						String api2 [] = config.getSmsDetailsReport().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#OTPETX#", otpEtx).replace("#MSISDN#", msisdn+"").replace("#DATA#", sms).replace("#RESP#", "0").replace("#STATUS#", "6").split("\\$");
						ruleCheckService.sendPOST(api2[0],api2[1],config.getSmsDetailsReportRto(),config.getSmsDetailsReportCto());
						LOGGER.info("Response ettId={},resp={}",ettId,json);
						return new ResponseEntity<>(json,HttpStatus.OK);
					}
				}
				
				//####Tempory Closed
				ResponseEntity<String> response = null;
				//Day Count
				String api = config.getDayLimitApi().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#OTPETX#", otpEtx+"");
				response = ruleCheckService.urlCall(api,config.getDayLimitApiRto(),config.getDayLimitApiCto());
				//response = ruleCheckService.urlCall("http://api.earntalktime.com/ett/api/v1/user/dayCountSmsSendSucc/?ettId="+ettId+"&otp="+otp+"&tempParam=test1&isRH=1&isCompressed=true&otpEtx="+otpEtx);
				
				if(response.getStatusCode()==HttpStatus.OK) {
					int count = Integer.parseInt(response.getBody().replaceAll("\"", ""));
					if(count>config.getDayLimitCount()) {
						//System.out.println("Day Count Exceed..."+count);
						SmsSubResp smsSubResp = new SmsSubResp();
						smsSubResp.setMsg(config.getDayLimitCheckText());
						smsSubResp.setStatus("3");
						Gson gson = new Gson();
						String json = gson.toJson(smsSubResp);
						if(isCompressed) {
							try {
								byte [] compressed = ruleCheckService.compress(json);
								CompressDto compressDto = new CompressDto();
								compressDto.setIsCompress(true);
								compressDto.setCompressedData(compressed);
								String api2 [] = config.getSmsDetailsReport().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#OTPETX#", otpEtx).replace("#MSISDN#", msisdn+"").replace("#DATA#", sms).replace("#RESP#", "0").replace("#STATUS#", "3").split("\\$");
								ruleCheckService.sendPOST(api2[0],api2[1],config.getSmsDetailsReportRto(),config.getSmsDetailsReportCto());
								LOGGER.info("Response ettId={},resp={}",ettId,json);
								return new ResponseEntity<>(compressDto, HttpStatus.OK);
							}
							catch(Exception exception){
								String api2 [] = config.getSmsDetailsReport().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#OTPETX#", otpEtx).replace("#MSISDN#", msisdn+"").replace("#DATA#", sms).replace("#RESP#", "0").replace("#STATUS#", "3").split("\\$");
								ruleCheckService.sendPOST(api2[0],api2[1],config.getSmsDetailsReportRto(),config.getSmsDetailsReportCto());
								LOGGER.info("Response ettId={},resp={}",ettId,json);
								return new ResponseEntity<>(json,HttpStatus.OK);
							}
						}
						else {
								String api2 [] = config.getSmsDetailsReport().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#OTPETX#", otpEtx).replace("#MSISDN#", msisdn+"").replace("#DATA#", sms).replace("#RESP#", "0").replace("#STATUS#", "3").split("\\$");
								ruleCheckService.sendPOST(api2[0],api2[1],config.getSmsDetailsReportRto(),config.getSmsDetailsReportCto());
								LOGGER.info("Response ettId={},resp={}",ettId,json);
								return new ResponseEntity<>(json,HttpStatus.OK);
						}
					}
				}
				else {
					String api2 [] = config.getSmsDetailsReport().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#OTPETX#", otpEtx).replace("#MSISDN#", msisdn+"").replace("#DATA#", sms).replace("#RESP#", "0").replace("#STATUS#", "0").split("\\$");
					ruleCheckService.sendPOST(api2[0],api2[1],config.getSmsDetailsReportRto(),config.getSmsDetailsReportCto());
					LOGGER.info("Response ettId={},resp={}",ettId,HttpStatus.UNAUTHORIZED);
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}
				
				//DND Check
				api = config.getDndCheckUrl().replace("#MSISDN#", msisdn+"");
				//String DNDCheck = "http://dncindia.com/dacx/jsonCommand?command=singleScrubApi&data={\"number\":\""+msisdn+"\",\"apiKey\":\"3ula6n5w44u13lq9\",\"categories\":[]}";
				String resp = ruleCheckService.sendRequest(api,config.getDndCheckUrlRto(),config.getDndCheckUrlCto());
				//String resp = ruleCheckService.sendRequest(DNDCheck,10000,10000);
				//System.out.println("response"+resp);
				DNDRespDncIndia dNDRespDncIndia = new DNDRespDncIndia();
                Gson gson = new Gson();
                dNDRespDncIndia = gson.fromJson(resp,DNDRespDncIndia.class);
                if(!dNDRespDncIndia.getCall().equals("OK")) {
                	SmsSubResp smsSubResp = new SmsSubResp();
					smsSubResp.setMsg(config.getDndListContainFailText());
					smsSubResp.setStatus("2");
					gson = new Gson();
					String json = gson.toJson(smsSubResp);
					if(isCompressed) {
						try {
							byte [] compressed = ruleCheckService.compress(json);
							CompressDto compressDto = new CompressDto();
							compressDto.setIsCompress(true);
							compressDto.setCompressedData(compressed);
							String api2 [] = config.getSmsDetailsReport().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#OTPETX#", otpEtx).replace("#MSISDN#", msisdn+"").replace("#DATA#", sms).replace("#RESP#", "0").replace("#STATUS#", "2").split("\\$");
							ruleCheckService.sendPOST(api2[0],api2[1],config.getSmsDetailsReportRto(),config.getSmsDetailsReportCto());
							LOGGER.info("Response ettId={},resp={}",ettId,json);
							return new ResponseEntity<>(compressDto, HttpStatus.OK);
						}
						catch(Exception exception){
							String api2 [] = config.getSmsDetailsReport().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#OTPETX#", otpEtx).replace("#MSISDN#", msisdn+"").replace("#DATA#", sms).replace("#RESP#", "0").replace("#STATUS#", "2").split("\\$");
							ruleCheckService.sendPOST(api2[0],api2[1],config.getSmsDetailsReportRto(),config.getSmsDetailsReportCto());
							LOGGER.info("Response ettId={},resp={}",ettId,json);
							return new ResponseEntity<>(json,HttpStatus.OK);
						}
					}
					else {
							String api2 [] = config.getSmsDetailsReport().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#OTPETX#", otpEtx).replace("#MSISDN#", msisdn+"").replace("#DATA#", sms).replace("#RESP#", "0").replace("#STATUS#", "2").split("\\$");
							ruleCheckService.sendPOST(api2[0],api2[1],config.getSmsDetailsReportRto(),config.getSmsDetailsReportCto());
							LOGGER.info("Response ettId={},resp={}",ettId,json);
							return new ResponseEntity<>(json,HttpStatus.OK);
					}
					//return new ResponseEntity<>(smsSubResp,HttpStatus.OK);
				}
                
                //SMS Submit
                String data = URLEncoder.encode(config.getSmsFormat().replace("#SOURCE#", source+"").replace("#SMS#", sms));
                //String data = URLEncoder.encode("From +91"+msisdn+"\n"+sms+"\n\nfree SMS from http://fsms.earntalktime.com");
                api = config.getSmsPushUrl().replace("#MSISDN#", msisdn+"").replace("#DATA#", data);
                //String smsUrl = "http://push3.maccesssmspush.com/servlet/com.aclwireless.pushconnectivity.listeners.TextListener?userId=rhsalt&pass=rhsalt&appid=rhsalt&subappid=rhsalt&contenttype=1&to=91"+msisdn+"&from=RHTETT&text="+data+"&selfid=true&alert=1&dlrreq=true";
                resp = ruleCheckService.sendRequest(api,config.getSmsPushUrlRto(),config.getSmsPushUrlCto());
				
                //SMS Succ Submit
                //String smsSub = "http://api.earntalktime.com/ett/api/v1/user/smsSendSucc/?";
                //String postParam = "ettId="+ettId+"&otp="+otp+"&tempParam=test1&isRH=1&isCompressed=true&msisdn="+msisdn+"&otpEtx="+otpEtx+"&vendor=ACL&msg="+data+"&respCode="+resp+"&status=1";
                String api2 [] = config.getSmsDetailsReport().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#OTPETX#", otpEtx).replace("#MSISDN#", msisdn+"").replace("#DATA#", data).replace("#RESP#", resp).replace("#STATUS#", "1").split("\\$");
                ruleCheckService.sendPOST(api2[0],api2[1],config.getSmsDetailsReportRto(),config.getSmsDetailsReportCto());
                if(reSetVar.getErrorCheckCounter().get()>0) {
                	reSetVar.getErrorCheckCounter().decrementAndGet();
                }
				
			}catch(Exception ex){
				LOGGER.error("ettId={},error={}",ettId,ex.getMessage());
				if(reSetVar.getErrorCheckCounter().get()<10) {
                	reSetVar.getErrorCheckCounter().addAndGet(1);
                }
				ex.printStackTrace();
				SmsSubResp smsSubResp = new SmsSubResp();
				smsSubResp.setMsg(config.getSmsSubmitError());
				smsSubResp.setStatus("0");
				Gson gson = new Gson();
				String json = gson.toJson(smsSubResp);
				if(isCompressed) {
					try {
						byte [] compressed = ruleCheckService.compress(json);
						CompressDto compressDto = new CompressDto();
						compressDto.setIsCompress(true);
						compressDto.setCompressedData(compressed);
						String api2 [] = config.getSmsDetailsReport().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#OTPETX#", otpEtx).replace("#MSISDN#", msisdn+"").replace("#DATA#", sms).replace("#RESP#", "0").replace("#STATUS#", "0").split("\\$");
		                ruleCheckService.sendPOST(api2[0],api2[1],config.getSmsDetailsReportRto(),config.getSmsDetailsReportCto());
						LOGGER.info("Response ettId={},resp={}",ettId,json);
						return new ResponseEntity<>(compressDto, HttpStatus.OK);
					}
					catch(Exception exception){
						String api2 [] = config.getSmsDetailsReport().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#OTPETX#", otpEtx).replace("#MSISDN#", msisdn+"").replace("#DATA#", sms).replace("#RESP#", "0").replace("#STATUS#", "0").split("\\$");
		                ruleCheckService.sendPOST(api2[0],api2[1],config.getSmsDetailsReportRto(),config.getSmsDetailsReportCto());
						LOGGER.info("Response ettId={},resp={}",ettId,json);
						return new ResponseEntity<>(json,HttpStatus.OK);
					}
				}
				else {
					String api2 [] = config.getSmsDetailsReport().replace("#ETTID#", ettId+"").replace("#OTP#", otp+"").replace("#OTPETX#", otpEtx).replace("#MSISDN#", msisdn+"").replace("#DATA#", sms).replace("#RESP#", "0").replace("#STATUS#", "0").split("\\$");
	                ruleCheckService.sendPOST(api2[0],api2[1],config.getSmsDetailsReportRto(),config.getSmsDetailsReportCto());
					LOGGER.info("Response ettId={},resp={}",ettId,json);
					return new ResponseEntity<>(json,HttpStatus.OK);
				}
				//return new ResponseEntity<>(smsSubResp,HttpStatus.OK);
			}
			SmsSubResp smsSubResp = new SmsSubResp();
			smsSubResp.setMsg(config.getSmsSubmitSuccessText());
			smsSubResp.setStatus("1");
			Gson gson = new Gson();
			String json = gson.toJson(smsSubResp);
			if(isCompressed) {
				try {
					byte [] compressed = ruleCheckService.compress(json);
					CompressDto compressDto = new CompressDto();
					compressDto.setIsCompress(true);
					compressDto.setCompressedData(compressed);
					LOGGER.info("Response ettId={},resp={}",ettId,json);
					return new ResponseEntity<>(compressDto, HttpStatus.OK);
				}
				catch(Exception exception){
					LOGGER.info("Response ettId={},resp={}",ettId,json);
					return new ResponseEntity<>(json,HttpStatus.OK);
				}
			}
			else {
				LOGGER.info("Response ettId={},resp={}",ettId,json);
				return new ResponseEntity<>(json,HttpStatus.OK);
			}
			//return new ResponseEntity<>(smsSubResp,HttpStatus.OK);
			
	}
	
}
