package com.web.rest.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.entity.DeviceToken;
import com.domain.entity.RedeemAmountConfig;
import com.domain.entity.RedeemThreshold;
import com.domain.entity.User;
import com.domain.entity.UserAccount;
import com.domain.entity.UserAccountSummary;
import com.domain.entity.UserRedeem;
import com.domain.entity.UserRedeem.RedeemType;
import com.google.gson.Gson;
import com.repository.jpa.DeviceTokenRepository;
import com.repository.jpa.EttGiftPendingRepository;
import com.repository.jpa.RedeemAmountConfigRepository;
import com.repository.jpa.RedeemThresholdRepository;
import com.repository.jpa.UserAccountRepository;
import com.repository.jpa.UserAccountSummaryRepository;
import com.repository.jpa.UserBlackListRepository;
import com.repository.jpa.UserRedeemRepository;
import com.repository.jpa.UserRepository;
import com.service.EttApis;
import com.service.OffersService;
import com.service.RechargeService;
import com.web.rest.dto.DTHDetails;
import com.web.rest.dto.GiftMsgResp;
import com.web.rest.dto.OffersDtoV4Compress;
import com.web.rest.dto.RedeemDataDto;
import com.web.rest.dto.RedeemDataWithConvenienceFeeDto;

/**
 * @author ankur
 */
@Controller
@RequestMapping(value = "/v2")
public class RedeemControllerV2 {

	private static Logger LOGGER = LoggerFactory
			.getLogger(RedeemControllerV2.class);

	@Inject 
    OffersService offersService;

	@Resource
	private JmsTemplate jmsTemplate;

	@Resource
	UserRepository userRepository;

	@Resource
	UserAccountRepository userAccountRepository;

	@Resource
	EttGiftPendingRepository ettGiftPendingRepository;

	@Resource
	UserRedeemRepository userRedeemRepository;

	@Resource
	RedeemThresholdRepository redeemThresholdRepository;

	@Resource
	RechargeService rechargeService;

	@Resource
	UserAccountSummaryRepository userAccountSummaryRepository;

	@Autowired
	private DeviceTokenRepository deviceTokenRepository;

	@Resource
	UserBlackListRepository userBlackListRepository;

	@Resource
	RedeemAmountConfigRepository redeemAmountConfigRepository;

	@Resource
	EttApis ettApis;

	@RequestMapping(value = "user/redeem/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> redeem(
			@RequestParam("ettId") long ettId,
			@RequestParam("msisdn") long msisdn,
			@RequestParam("circle") String circle,
			@RequestParam("operator") String operator,
			@RequestParam("amount") float amount,
			@RequestParam("type") String type,
			@RequestParam("deviceId") String deviceId,
			@RequestParam("otp") int otp,
			@RequestParam(value = "fee", required = false, defaultValue = "0.0") float fee

	) throws IOException, ExecutionException, InterruptedException {

		LOGGER.info(
				"user/redeem/v2 ettId={}, msisdn={}, circle={}, operator={}, amount={}, type={}, deviceId={}, otp {}, fee{}",
				ettId, msisdn, circle, operator, amount, type, deviceId, otp,
				fee);

		if (ettApis.getBlackListStatus(ettId)) {
			LOGGER.info("ettId BlackListed {}", ettId);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = userRepository.findByEttId(ettId);
		if (user == null || !user.isVerified() || user.getOtp() != otp) {
			LOGGER.error("user not authorized in db ettId=" + ettId);
			ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		float threshold = ettApis.getThresholdAmont(user);

		UserAccount userAccount = userAccountRepository.findByEttId(ettId);
		if (userAccount == null) {
			LOGGER.error("user not found in user account table ettId=" + ettId);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (userAccount.getCurrentBalance() < threshold) {
			LOGGER.warn("current balance is less than threshold amount -->amount="
					+ userAccount.getCurrentBalance()
					+ "&threshold="
					+ threshold + "&ettId=" + ettId);
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		} else if (userAccount.getCurrentBalance() < amount) {
			LOGGER.warn("current balance is less than requested amount -->balance="
					+ userAccount.getCurrentBalance()
					+ "&requestedAmount="
					+ threshold + "&ettId=" + ettId);
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		userAccount.setCurrentBalance((userAccount.getCurrentBalance() - (amount + fee)));
		userAccountRepository.save(userAccount);

		UserRedeem userRedeem = new UserRedeem();
		userRedeem.setEttId(ettId);
		userRedeem.setMsisdn(msisdn);
		userRedeem.setOperator(operator);
		userRedeem.setCircle(circle);
		userRedeem.setAmount(amount);
		userRedeem.setType(type);
		userRedeem.setRedeemType(RedeemType.RECHARGE);
		userRedeem.setLoanAmount(0f);
		userRedeem.setStatus("PENDING");
		userRedeem.setPostBalance(userAccount.getCurrentBalance());
		userRedeem.setFee(fee);
		userRedeem = userRedeemRepository.save(userRedeem);
		user.setRedeemCount(user.getRedeemCount()+1);
		userRepository.save(user);
		LOGGER.debug("save in UserRedeem table status=PROCESSING ettId="
				+ ettId);
		enqueue(userRedeem.getId());
		Map<String, Object> resp = new HashMap<String, Object>();
		resp.put("id", userRedeem.getId() + "");
		resp.put(
				"text",
				rechargeService.getLocaleTextTemplate().get(
						"REDEEM_ACCEPTED_RESPONSE_"+user.getLocale()));
		resp.put("redeemAmounts", rechargeService.getRedeemAmountsList());
		resp.put("operators", rechargeService.getRedeemOperatorsList());
		LOGGER.info("request accepted for|redeem ettId=" + ettId + "&redeemId="
				+ userRedeem.getId());
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "user/redeemGift/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> redeemGift(
			@RequestParam("ettId") long ettId,
			@RequestParam("msisdn") long msisdn,
			@RequestParam("amount") float amount,
			@RequestParam("otp") int otp,
			@RequestParam(value = "fee", required = false, defaultValue = "0") int fee,
			@RequestParam(value = "message", required = false, defaultValue = "") String msg

	) throws IOException, ExecutionException, InterruptedException {

		LOGGER.info(
				"user/redeemGift/v2 ettId={}, msisdn={}, amount={}, otp {}, fee{}, msg{}",ettId, msisdn, amount, otp, fee, msg);

		if (ettApis.getBlackListStatus(ettId)) {
			LOGGER.info("ettId BlackListed {}", ettId);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = userRepository.findByEttId(ettId);
		if (user == null || !user.isVerified() || user.getOtp() != otp) {
			LOGGER.error("user not authorized in db ettId=" + ettId);
			ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		int redeemCount = user.getRedeemCount();
			if(redeemCount==0) {
				LOGGER.info("Giffing option not for the user whose redeem count is zero ettId={}",ettId);
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		List<User> userList = userRepository.findByMsisdnVerfied(msisdn + "");
		if(userList == null || userList.size()==0) {
			GiftMsgResp giftMsgResp = new GiftMsgResp();
			giftMsgResp.setMsg(rechargeService.getLocaleTextTemplate().get("GIFT_BPARTY_NOT_ETT_"+user.getLocale()).replace("#MSISDN#", msisdn+""));
			giftMsgResp.setStatus(false);
			return new ResponseEntity<>(giftMsgResp,HttpStatus.OK);
		}
		RedeemAmountConfig dthAmountConfigs = redeemAmountConfigRepository.findGift();
		if (dthAmountConfigs == null) {
			LOGGER.info("Gift Closed found wrong hit ettId={},msisdn{},amount{}",
					ettId, msisdn, amount);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		fee=Integer.parseInt(dthAmountConfigs.getFee());
		int todayGiftAmount = 0, toDaysRedeem = 0;
		java.util.Date date = ettApis.convertDBDateActive();
		List<UserAccountSummary> userAccountSummarylist = userAccountSummaryRepository
				.getTodaysGift(ettId, 8884l, date);
		for (UserAccountSummary userAccountSummary : userAccountSummarylist) {
			todayGiftAmount = todayGiftAmount
					+ (-(int) userAccountSummary.getAmount());
		}
		List<UserRedeem> userRedeemlist = userRedeemRepository.getTodaysRedeem(
				ettId, date);
		for (UserRedeem userRedeem : userRedeemlist) {
			toDaysRedeem = toDaysRedeem + (int) userRedeem.getAmount();
		}
		String amountThreashHold[] = dthAmountConfigs.getAmount().split(",");
		int minTrheashHold = Integer.parseInt(amountThreashHold[0]);
		int maxThreadHold = Integer.parseInt(amountThreashHold[1]);
		int userBalanceAfterGift = Integer.parseInt(amountThreashHold[2]);

		try {
			int giftAmountStaticThreasHold = Integer.parseInt(amountThreashHold[3]);
			if(giftAmountStaticThreasHold<amount+fee) {
				LOGGER.info("Wrong amount request for ettId={},ThreasholdAmount={},transerAmount={}",ettId,giftAmountStaticThreasHold,amount);
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}
		catch(Exception ex) {}
		int userBal = (int) userAccountRepository.findByEttId(ettId).getCurrentBalance();
		userBal = userBal - (userBalanceAfterGift + fee);
		int userMaxThreadHold = (maxThreadHold - (todayGiftAmount + toDaysRedeem));
		if (userBal < userMaxThreadHold) {
			userMaxThreadHold = userBal;
		}
		if (amount < minTrheashHold || amount > userMaxThreadHold) {
			LOGGER.info("Gift Closed found wrong amount ettId={},msisdn{},amount{},minTrheashHold={},userMaxThreadHold={}",ettId, msisdn, amount, minTrheashHold, userMaxThreadHold);
			GiftMsgResp giftMsgResp = new GiftMsgResp();
			giftMsgResp.setMsg(rechargeService.getLocaleTextTemplate().get("maxThreashHoldMsgFailLowBalGIFT_"+user.getLocale()));
			return new ResponseEntity<>(giftMsgResp, HttpStatus.OK);
		}
		UserAccount userAccountAparty = userAccountRepository
				.findByEttId(ettId);
		if (userAccountAparty == null) {
			userAccountAparty = new UserAccount();
			userAccountAparty.setEttId(ettId);
		}
		userAccountAparty.setCurrentBalance(userAccountAparty.getCurrentBalance() - (amount + fee));
		userAccountRepository.save(userAccountAparty);
		UserAccountSummary userAccountSummaryAparty = new UserAccountSummary();
		userAccountSummaryAparty.setAmount(Float.parseFloat("-" + (amount)));
		userAccountSummaryAparty.setCreatedTime(new java.util.Date());
		userAccountSummaryAparty.setEttId(ettId);
		userAccountSummaryAparty.setOfferId(8884l);
		userAccountSummaryAparty.setOfferName("gift to " + msisdn);
		userAccountSummaryAparty.setRemarks("GIFT_SUCCESS");
		userAccountSummaryRepository.save(userAccountSummaryAparty);

		userAccountSummaryAparty = new UserAccountSummary();
		userAccountSummaryAparty.setAmount(Float.parseFloat("-"+fee));
		userAccountSummaryAparty.setCreatedTime(new java.util.Date());
		userAccountSummaryAparty.setEttId(ettId);
		userAccountSummaryAparty.setOfferId(8884l);
		userAccountSummaryAparty.setRemarks("convenience charge");
		userAccountSummaryAparty.setOfferName("convenience fee on gifting");
		userAccountSummaryRepository.save(userAccountSummaryAparty);
		
		User userBparty = userList.get(0);
		UserAccount userAccountBparty = userAccountRepository
				.findByEttId(userBparty.getEttId());
		if (userAccountBparty == null) {
			userAccountBparty = new UserAccount();
			userAccountBparty.setEttId(userBparty.getEttId());
		}
		userAccountBparty.setCurrentBalance(userAccountBparty.getCurrentBalance() + amount);
		userAccountRepository.save(userAccountBparty);
		UserAccountSummary userAccountSummaryBparty = new UserAccountSummary();
		userAccountSummaryBparty.setAmount(amount);
		userAccountSummaryBparty.setCreatedTime(new java.util.Date());
		userAccountSummaryBparty.setEttId(userBparty.getEttId());
		userAccountSummaryBparty.setOfferId(8883l);
		userAccountSummaryBparty.setRemarks("GIFT_" + user.getEttId());
		userAccountSummaryBparty.setOfferName("gift from " + user.getMsisdn());
		userAccountSummaryRepository.save(userAccountSummaryBparty);
		String apartyMsg = rechargeService.getLocaleTextTemplate().get("GIFT_SUCCESS_APARTY_MSG_"+user.getLocale()).replace("#AMOUNT#", ((int) amount) + "").replace("#MSISDN#", msisdn + "");
		String bpartyMsg = rechargeService.getLocaleTextTemplate().get("GIFT_SUCCESS_BPARTY_MSG_"+user.getLocale()).replace("#AMOUNT#", ((int) amount) + "").replace("#MSISDN#", user.getMsisdn() + "");
		if (msg.length() > 0) {
			bpartyMsg = bpartyMsg + "\nHere is the message from your friend:\n"
					+ msg;
		}
		DeviceToken deviceTokenA = deviceTokenRepository.findByEttId(ettId);
		if (deviceTokenA != null) {
			sendPush(apartyMsg, deviceTokenA.getDeviceToken(), "BALANCE", ettId);
		}
		DeviceToken deviceTokenB = deviceTokenRepository.findByEttId(userBparty
				.getEttId());
		if (deviceTokenB != null) {
			sendPush(bpartyMsg, deviceTokenB.getDeviceToken(), "BALANCE",
					userBparty.getEttId());
		}
		GiftMsgResp giftMsgResp = new GiftMsgResp();
		giftMsgResp.setMsg(apartyMsg);
		return new ResponseEntity<>(giftMsgResp, HttpStatus.OK);

	}
	
	 
	
	 
	@RequestMapping(value = "user/redeemData/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> redeemData(@RequestParam("ettId") long ettId,
			@RequestParam("otp") int otp,
			@RequestParam(value="isCompressed", required=false, defaultValue="false") boolean isCompressed) throws IOException,
			ExecutionException, InterruptedException {

		LOGGER.info("user/redeemData/v2 ettId={}, otp {}", ettId, otp);
		List<RedeemDataDto> resp = new ArrayList<RedeemDataDto>(3);
		List<DTHDetails> dthDet = new ArrayList<DTHDetails>(3);
		RedeemDataDto redeemDataDtoGift = null;
		User user = userRepository.findByEttId(ettId);
		int redeemCount = user.getRedeemCount();
		if(Float.parseFloat(user.getAppVersion())<1.6f) {
			redeemCount=redeemCount+1;
		}
		
		List<RedeemAmountConfig> dthAmountConfigs = redeemAmountConfigRepository.findAll();
		int userBal = (int) userAccountRepository.findByEttId(ettId).getCurrentBalance();
		String threshold="0";
		for (RedeemAmountConfig dthAmountConfig1 : dthAmountConfigs) {
			if (dthAmountConfig1.isStatus() == false) {
				continue;
			}
			if (dthAmountConfig1.getType().equals("PREPAID")) {
				RedeemDataDto redeemDataDto = new RedeemDataDto();
				if(redeemCount == 0){
					redeemDataDto.setAmount(dthAmountConfig1.getIstRedeemAmount());
					redeemDataDto.setThreshold(dthAmountConfig1.getIstThreshold());
				}
				else{
					redeemDataDto.setAmount(dthAmountConfig1.getAmount());
					redeemDataDto.setThreshold(dthAmountConfig1.getThreshold());
				}
				redeemDataDto.setThresholdMain(dthAmountConfig1.getThreshold());
				redeemDataDto.setFee(ettApis.getFee(redeemDataDto.getAmount(), dthAmountConfig1.getFee(), userBal));
				redeemDataDto.setType("PREPAID");
				redeemDataDto.setOperatorList(Arrays.asList(rechargeService.getAppConfig().get("OPERATOR").split(",")));
				resp.add(redeemDataDto);
				continue;
			}
			if (dthAmountConfig1.getType().equals("POSTPAID")) {
				RedeemDataDto redeemDataDto = new RedeemDataDto();
				if(redeemCount == 0){
					redeemDataDto.setAmount(dthAmountConfig1.getIstRedeemAmount());
					redeemDataDto.setThreshold(dthAmountConfig1.getIstThreshold());
				}
				else{
					redeemDataDto.setAmount(dthAmountConfig1.getAmount());
					redeemDataDto.setThreshold(dthAmountConfig1.getThreshold());
				}
				redeemDataDto.setThresholdMain(dthAmountConfig1.getThreshold());
				redeemDataDto.setFee(ettApis.getFee(redeemDataDto.getAmount(), dthAmountConfig1.getFee(), userBal));
				redeemDataDto.setType("POSTPAID");
				redeemDataDto.setOperatorList(Arrays.asList(rechargeService.getAppConfig().get("OPERATOR").split(",")));
				resp.add(redeemDataDto);
				continue;
			}
			if (dthAmountConfig1.getType().equals("GIFT")) {
				if(redeemCount==0) {
					LOGGER.info("Giffing option not for the user whose redeem count is zero ettId={}",ettId);
					continue;
				}
				int toDaysRedeem = 0, todayGiftAmount = 0;
				//java.util.Date date = ettApis.convertDBDate();
				java.util.Date date = ettApis.convertDBDateActive();
				List<UserAccountSummary> userAccountSummarylist = userAccountSummaryRepository.getTodaysGift(ettId, 8884l, date);
				for (UserAccountSummary userAccountSummary : userAccountSummarylist) {
					todayGiftAmount = todayGiftAmount
							+ (-(int) userAccountSummary.getAmount());
				}
				List<UserRedeem> userRedeemlist = userRedeemRepository
						.getTodaysRedeem(ettId, date);
				for (UserRedeem userRedeem : userRedeemlist) {
					toDaysRedeem = toDaysRedeem + (int) userRedeem.getAmount();
				}
				LOGGER.info("todayGiftAmount={},toDaysRedeem={}",
						todayGiftAmount, toDaysRedeem);
				String amountThreashHold[] = null;
				if(redeemCount==0) {
					amountThreashHold = dthAmountConfig1.getIstRedeemAmount().split(",");
				}
				else {
					amountThreashHold = dthAmountConfig1.getAmount().split(",");
				}
				int minTrheashHold = Integer.parseInt(amountThreashHold[0]);
				int maxThreadHold = Integer.parseInt(amountThreashHold[1]);
				int userBalanceAfterGift = Integer.parseInt(amountThreashHold[2]);
				userBal = userBal- (userBalanceAfterGift + Integer.parseInt(dthAmountConfig1.getFee()));
				int userMaxThreadHold = (maxThreadHold - (todayGiftAmount + toDaysRedeem));
				if (userBal < userMaxThreadHold) {
					userMaxThreadHold = userBal;
				}
				try {
					int giftAmountStaticThreasHold = Integer.parseInt(amountThreashHold[3]);
					if(giftAmountStaticThreasHold<userMaxThreadHold) {
						userMaxThreadHold =giftAmountStaticThreasHold;
					}
				}
				catch(Exception ex) {}
				redeemDataDtoGift = new RedeemDataDto();
				redeemDataDtoGift.setMaxThreashHoldMsgGift(rechargeService.getLocaleTextTemplate().get("maxThreashHoldMsgGIFT_"+user.getLocale()).replace("#userMaxThreadHold#",userMaxThreadHold + ""));
				redeemDataDtoGift.setMinThreashHoldGift(minTrheashHold + "");
				redeemDataDtoGift.setFee(dthAmountConfig1.getFee());
				redeemDataDtoGift.setThreshold(dthAmountConfig1.getIstThreshold());
				//redeemDataDtoGift.setThreshold(dthAmountConfig1.getIstThreshold());
				redeemDataDtoGift.setType("GIFT");

				if (userMaxThreadHold <= 0) {
					userMaxThreadHold = 0;
					if(userBal <=0 ) {
						redeemDataDtoGift.setMaxThreashHoldMsgGift(rechargeService.getLocaleTextTemplate().get("maxThreashHoldMsgFailLowBalGIFT_"+user.getLocale()).replace("#userMaxThreadHold#",userMaxThreadHold + ""));
					}
					else {
						redeemDataDtoGift.setMaxThreashHoldMsgGift(rechargeService.getLocaleTextTemplate().get("maxThreashHoldMsgFailGIFT_"+user.getLocale()).replace("#userMaxThreadHold#",userMaxThreadHold + ""));
					}
				}
				else if (userMaxThreadHold < minTrheashHold) {
					userMaxThreadHold = 0;
					redeemDataDtoGift.setMaxThreashHoldMsgGift(rechargeService.getLocaleTextTemplate().get("maxThreashHoldMsgFailLowBalGIFT_"+user.getLocale()).replace("#userMaxThreadHold#",userMaxThreadHold + ""));
				}
				else {
					
				}
				redeemDataDtoGift.setMaxThreashHoldGift(userMaxThreadHold + "");
				// resp.add(redeemDataDtoGift);
				continue;
			}
			DTHDetails dthDetails = new DTHDetails();
			dthDetails.setOperator(dthAmountConfig1.getOperator());
			if(redeemCount==0) {
				dthDetails.setAmount(dthAmountConfig1.getIstRedeemAmount());
			}
			else {
				dthDetails.setAmount(dthAmountConfig1.getAmount());
			}
			//dthDetails.setFee(dthAmountConfig1.getFee());
			dthDetails.setFee(ettApis.getFee(dthAmountConfig1.getAmount(), dthAmountConfig1.getFee(), userBal));
			threshold = dthAmountConfig1.getThreshold();
			dthDet.add(dthDetails);
		}
		RedeemDataDto redeemDataDtoDth = new RedeemDataDto();
		redeemDataDtoDth.setType("DTH");
		//redeemDataDtoDth.setThreshold(dthAmountConfig1.getIstThreshold());
		redeemDataDtoDth.setThreshold(threshold);
		redeemDataDtoDth.setDthDetails(dthDet);
		resp.add(redeemDataDtoDth);
		if (redeemDataDtoGift != null) {
			resp.add(redeemDataDtoGift);
		}
		RedeemDataWithConvenienceFeeDto redeemConvenienceFeeDto = new RedeemDataWithConvenienceFeeDto();
		redeemConvenienceFeeDto.setRedeemDataDto(resp);
		redeemConvenienceFeeDto.setConvenienceFee(rechargeService.getLocaleTextTemplate().get("CONVENIENCE_FEE_TEXT_"+user.getLocale()));
		redeemConvenienceFeeDto.setConvenienceFeeNew(rechargeService.getLocaleTextTemplate().get("CONVENIENCE_FEE_TEXT_NEW_"+user.getLocale()));
		//List<RedeemThreshold> redeemThresholdsList = redeemThresholdRepository.findAll();
	/*	if(redeemCount == 0){
			redeemConvenienceFeeDto.setThreshold(redeemThresholdsList.get(1).getThresholdAmount());
			redeemConvenienceFeeDto.setThresholdForIstRedeem(redeemThresholdsList.get(0).getThresholdAmount());
		}
		else {
			redeemConvenienceFeeDto.setThreshold(redeemThresholdsList.get(0).getThresholdAmount());
		}*/
		//redeemConvenienceFeeDto.setPrepaidOperator(Arrays.asList(rechargeService.getProperties().getProperty("OPERATOR").split(",")));
		//redeemConvenienceFeeDto.setPostpaidOperator(Arrays.asList(rechargeService.getProperties().getProperty("OPERATOR").split(",")));
		//redeemConvenienceFeeDto.setDthOperator(Arrays.asList(rechargeService.getProperties().getProperty("OPERATOR_DTH").split(",")));
		
		try {
	    	if(rechargeService.getAppConfig().get("IsOfferDataCompress").equals("true") && isCompressed == true) {
		    	//Object offerDto = getOffersDto(user, offersList,threshold,userAccount,isTodayFirst);
		    	//Gson gson = new Gson();
		    	
		    	Gson gson = new Gson();
		    	String json = gson.toJson(redeemConvenienceFeeDto);
		    	//System.out.println("[json]["+json+"]");
		    	byte [] compressed = offersService.compress(json);
		    	OffersDtoV4Compress offersDtoV4Compress = new OffersDtoV4Compress();
		    	offersDtoV4Compress.setIsCompress(true);
		    	offersDtoV4Compress.setOfferDetails(compressed);
		    	return new ResponseEntity<>(offersDtoV4Compress, HttpStatus.OK);
	    	}
	    	else{
	    		return new ResponseEntity<>(redeemConvenienceFeeDto, HttpStatus.OK);
	    	}
    	}catch(Exception ex){
    		return new ResponseEntity<>(redeemConvenienceFeeDto, HttpStatus.OK);
    	}
		
		

	}


	private void enqueue(final Long id) {
		try {
			jmsTemplate.send("redeem", new MessageCreator() {
				@Override
				public Message createMessage(Session session)
						throws JMSException {

					TextMessage textMessage = session.createTextMessage();
					textMessage.setText(id + "");
					return textMessage;
				}
			});
		} catch (Exception e) {
			LOGGER.error("error in redeem push " + e);
			e.printStackTrace();
		}

	}

	private void sendPush(final String pushText, final String dToken,
			final String type, final Long ettId) {
		try {
			jmsTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session)
						throws JMSException {

					MapMessage mapMessage = session.createMapMessage();
					mapMessage.setString("ID", dToken);
					mapMessage.setString("DISPLAY_STRING", pushText);
					mapMessage.setInt("BADGE_COUNT", 1);
					mapMessage.setLong("ettId", ettId);
					mapMessage.setString("DEVICE_TYPE", "ANDROID");
					mapMessage.setString("type", type);
					return mapMessage;
				}
			});
		} catch (Exception e) {
			LOGGER.error("error in otp controller push " + e);
			e.printStackTrace();
		}

	}

	public String SendUDP(String strFinal, String IP, String port) {
		System.out
				.println("[ETT][SendUDP] [SendUDP] [OnlineDA Message] FINAL String "
						+ strFinal + ":::" + IP + "::" + port);
		String resp = strFinal;
		try {
			DatagramSocket clientSocket = new DatagramSocket();
			int localport = clientSocket.getLocalPort();
			String portn = localport + "";
			strFinal = strFinal.replace("LPORT", portn);
			InetAddress IPAddress = InetAddress.getByName(IP);
			DatagramPacket sendPacket = new DatagramPacket(strFinal.getBytes(),
					strFinal.getBytes().length, IPAddress,
					Integer.parseInt(port));
			clientSocket.send(sendPacket);
			System.out.println("[ETT] [SendUDP] [OnlineDA Message] SendUDP["
					+ strFinal + "] IP:" + IP + " Port:" + port);
			clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("[ETT][SendUDP] [OnlineDA Message] Exception When send Packet!!!!!!!!!! "
							+ e);
		}
		return resp;
	}

}
