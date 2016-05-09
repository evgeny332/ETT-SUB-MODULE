package com.web.rest.controller;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
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
import com.ning.http.client.AsyncCompletionHandlerBase;
import com.ning.http.client.Response;
import com.repository.jpa.UserRepository;
import com.service.RechargeService;
import com.web.rest.dto.OtpDto;

/**
 * @author ankur
 */
@Controller
@RequestMapping(value = "/v1")
public class ReregisterController {

	private static Logger LOGGER = LoggerFactory
			.getLogger(ReregisterController.class);

	@Autowired
	private UserRepository userRepository;

	@Resource
	private HttpRequestProcessor httpRequestProcessor;

	@Resource
	private RechargeService rechargeService;
	
	
	@RequestMapping(value = "user/reRegister/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> login(@RequestParam("ettId") Long ettId,
			@RequestParam("msisdn") String msisdn) {
		LOGGER.info("Get reRegister request received for ettId {} , msisdn {}",
				ettId, msisdn);
		Future<Response> circleResp = null;
		// get 1operator circle url
		Map<String, String> queryParam = new HashMap<>();
		queryParam.put("msisdn", msisdn);
		try {
			circleResp = httpRequestProcessor.submitGetRequest(rechargeService.getAppConfig().get("FETCH_OPERATOR_CIRCLE_URL"), null,
					queryParam, new AsyncCompletionHandlerBase());
		} catch (Exception e) {
			LOGGER.error("Error in curl operator circle msisdn=" + msisdn);
			e.printStackTrace();
		}

		User user = userRepository.findByEttId(ettId);
		String operatorCircle = getOperatorCircle(circleResp);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else {
			List<User> userList = userRepository.findByMsisdnVerfied(msisdn);
			User userBymsisdn = null;
			if(userList == null || userList.size()<=0) {
				
			}
			else {
				userBymsisdn = userList.get(0);
			}
			/*if (userBymsisdn!=null && !userBymsisdn.getEttId().equals(ettId) && userBymsisdn.isVerified()) {
				Map<String, String> resp = new HashMap<>();
				resp.put("title", rechargeService.getProperties().getProperty("REREGISTER_FAILED_TITLE", "Number already used"));
				resp.put("text", rechargeService.getProperties().getProperty("REREGISTER_FAILED_TEXT", "This number is already verified with some other user. Please reinstall and verify again if you are using the same number."));
				return new ResponseEntity<>(resp, HttpStatus.NOT_ACCEPTABLE);
			}
*/
			if (!user.getMsisdn().equals(msisdn)) {
				user.setMsisdn(msisdn);
				user = userRepository.save(user);
				LOGGER.info(" user updated {}", ettId);
			}
		}

		// send otp sms
		String otpText = rechargeService.getLocaleTextTemplate().get("OTP_TEXT_"+user.getLocale()).replaceFirst(
				"#OTP_KEY#", "otp=" + user.getOtp());
		// Map<String,String> queryParamMap = new HashMap<>();
		/*
		 * queryParamMap.put("username", "RationalHeads");
		 * queryParamMap.put("password", "heads12");
		 * queryParamMap.put("destination", "91"+msisdn);
		 * queryParamMap.put("source", "RHTOTP"); queryParamMap.put("message",
		 * otpText); try { httpRequestProcessor.submitGetRequest(
		 * "http://sms6.routesms.com:8080/bulksms/bulksms?type=0&dlr=1&", null,
		 * queryParamMap, new SmsManager(queryParamMap)); } catch (IOException
		 * e) { e.printStackTrace(); }
		 */
		/*
		 * queryParamMap.put("user", "rationalh"); queryParamMap.put("password",
		 * "rationalh123"); queryParamMap.put("PhoneNumber", "91"+msisdn);
		 * queryParamMap.put("sender", "RHTETT"); queryParamMap.put("text",
		 * otpText); try { httpRequestProcessor.submitGetRequest(
		 * "http://203.92.40.186:8443/Sun3/Send_SMS2x?", null, queryParamMap,
		 * new SmsManager(queryParamMap)); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */

		//LOGGER.info("otp sent"+ SendUDP(msisdn + "#" + otpText, "127.0.0.1", "7777"));
		LOGGER.info("otp sent"+SendUDP(msisdn+"#"+otpText,rechargeService.getAppConfig().get("OTP_MSG_UDP_IP"),rechargeService.getAppConfig().get("OTP_MSG_UDP_PORT")));
		return new ResponseEntity<>(getOtpDto(user), HttpStatus.OK);
	}

	public String SendUDP(String strFinal, String IP, String port) {
		System.out
				.println("[ETT][SendUDP] [SendUDP] [OnlineDA Message] FINAL String "
						+ strFinal + ":::" + IP + "::" + port);
		String resp = "";
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

	private String getOperatorCircle(Future<Response> circleRespFuture) {
		try {
			String circle = circleRespFuture.get().getResponseBody();
			return circle.trim();
		} catch (Exception e) {
			LOGGER.error("error in fetching circle " + e);
			e.printStackTrace();
			return "UNKNOWN";
		}
	}

	private OtpDto getOtpDto(User user) {
		OtpDto otpDto = new OtpDto();
		otpDto.setEttId(user.getEttId());
		otpDto.setVerified(user.isVerified());
		otpDto.setCountryCode("+91");
		otpDto.setCurrency("\u20B9");
		otpDto.setOperator(user.getOperator());
		otpDto.setOperatorsList(rechargeService.getRedeemOperatorsList());
		otpDto.setRedeemAmountsList(rechargeService.getRedeemAmountsList());
		otpDto.setTimerOn(true);
		otpDto.setTempOtp(user.getTempOtp());
		otpDto.setTimer(40);
		return otpDto;
	}
}
