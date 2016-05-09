

package com.web.rest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
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
import com.domain.entity.User;
import com.repository.jpa.DeviceTokenRepository;
import com.repository.jpa.UserBlackListRepository;
import com.repository.jpa.UserRepository;
import com.service.EttApis;

/**
 * @author ankur
 */
@Controller
@RequestMapping(value = "/v1")
public class    PushController {

    private static Logger LOGGER = LoggerFactory.getLogger(PushController.class);

   @Resource
   JmsTemplate jmsTemplate;
    
   
   @Resource
   UserRepository userRepository;
   
   @Resource
   DeviceTokenRepository deviceTokenRepository;
   
   @Resource
   UserBlackListRepository userBlackListRepository;

   @Resource
   EttApis ettApis;

   
    @RequestMapping(value = "/user/push/", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> push(	@RequestParam(value="ettIds") List<Long> ettIds,
    								@RequestParam(value="text") String text,
    								@RequestParam(value="sound", required=false, defaultValue="") final String sound,
    								@RequestParam(value="url", required=false, defaultValue="") final String url,
    								@RequestParam(value="isOpenApp", required=false, defaultValue="") final String isOpenApp,
    								@RequestParam(value="offerId", required=false, defaultValue="") final String offerId,
    								@RequestParam(value="imageUrl", required=false, defaultValue="") final String imageUrl,
    								@RequestParam(value="type", required=false, defaultValue="") final String type,
    								@RequestParam(value="packageName", required=false, defaultValue="") final String packageName
    							)
	{
    	
    	LOGGER.info("API /user/push/ ettIds={}, text={}, sound={} ,url={}, isOpenApp={} offerID={}, imageUrl={}, type={}, packageName{}", ettIds, text,sound,url,isOpenApp,offerId,imageUrl,type,packageName);
    	
    	final String msg = text;
    	List<User> users = userRepository.findByEttIdIn(ettIds);
    	List<Long> sentIds = new ArrayList<>(ettIds.size());
    	
    	for(User user : users){
    		//final DeviceToken deviceToken = deviceTokenRepository.findByDeviceId(user.getDeviceId());
    		final DeviceToken deviceToken = deviceTokenRepository.findByEttId(user.getEttId());
    		final Long ettId = user.getEttId();
    		if(deviceToken!=null && !"".equals(deviceToken.getDeviceToken())){
		    	jmsTemplate.send(new MessageCreator() {
		            @Override
		            public Message createMessage(Session session) throws JMSException {
		            	
		                MapMessage mapMessage = session.createMapMessage();
		                mapMessage.setString("ID", deviceToken.getDeviceToken());
		                mapMessage.setString("DISPLAY_STRING", msg);
		                mapMessage.setInt("BADGE_COUNT", 1);
		                mapMessage.setLong("ettId", ettId);
		                mapMessage.setString("DEVICE_TYPE", "ANDROID");
		                if(!sound.equals("")){
		                	mapMessage.setString("SOUND", sound);
		                }
		                if(!url.equals("")){
		                	mapMessage.setString("URL", url);
		                }
		                if(!isOpenApp.equals("")){
		                	mapMessage.setString("isOpenApp", isOpenApp);
		                }
		                mapMessage.setString("offerId", offerId);
		                mapMessage.setString("imageUrl", imageUrl);
		                mapMessage.setString("type", type);
		                mapMessage.setString("packageName", packageName);
		                return mapMessage;
		            }
		        });
		    	
		    	sentIds.add(user.getEttId());
    		 }
    	}
    	LOGGER.info("push sent msg {}, users {}",text, sentIds);
    	//basant-APA91bHsX7JCNrTL9czvi__Tkz9MPNTIAxaQM3i9SKXSp_uyRcp3-NL_klYSCXtX7vy9cHIx7Vml5zYFn2oHjnZLBxuLyNWkDrgnoAYoKUq2tSs1RL1XbnQaCIq-PVgUlcQCzbIRVxclv70A3TLpKrANVzUrekLGYg
    	//anurag-APA91bE_PPlzDcBBbsTSBULUoqLjpENX6KdcO3gIcYsRqdl4PcewHx5P9q07PNGTSgIZgnXOAxrv3c3J_BCDa3y6NPsmTpZhGLjeE5OS8yFEVSjLDTZEa0ABZbGoaORVfbdDw8ySBqHhfYXukKwe1RmSJMiPeRmtNw
    	//dhiru- APA91bEQfXBrz4v-HhkSoOH-Xqwo2SIpMlt8aqhqnET-SEnubGebRqXsKuTC0ly0tdcjbbauVn7ip9twfUEpEovV1kP33yrhRQhBrlYTmjcLUYUgPGDvkCRd1Y-z0aB1vn-CyGBQ4p5qPojW3NBr-GHP5zcf6lkPTA
    	/*jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
            	
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("ID", "APA91bEQfXBrz4v-HhkSoOH-Xqwo2SIpMlt8aqhqnET-SEnubGebRqXsKuTC0ly0tdcjbbauVn7ip9twfUEpEovV1kP33yrhRQhBrlYTmjcLUYUgPGDvkCRd1Y-z0aB1vn-CyGBQ4p5qPojW3NBr-GHP5zcf6lkPTA");
                mapMessage.setString("DISPLAY_STRING", msg);
                mapMessage.setInt("BADGE_COUNT", 1);
                mapMessage.setString("DEVICE_TYPE", "ANDROID");
                return mapMessage;
            }
        });*/
		return new ResponseEntity<>(sentIds, HttpStatus.OK);
  }
    
    
    
    @RequestMapping(value = "/user/push/all", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> pushAll(@RequestParam(value="text") String text)
	{    	
    	final String msg = text;   	
		int page = 0;
		while (true) {
			int userProcessed = 0;
			Iterable<User> userList = userRepository.findAll(new PageRequest(page++, 500));
			for (User user : userList) {
				userProcessed++;
				//final DeviceToken deviceToken = deviceTokenRepository.findByDeviceId(user.getDeviceId());
				final DeviceToken deviceToken = deviceTokenRepository.findByEttId(user.getEttId());
				final Long ettId = user.getEttId();
				if (deviceToken != null	&& !"".equals(deviceToken.getDeviceToken())) {
					try{
						jmsTemplate.send(new MessageCreator() {
							@Override
							public Message createMessage(Session session)
									throws JMSException {	
								MapMessage mapMessage = session.createMapMessage();
								mapMessage.setString("ID",	deviceToken.getDeviceToken());
								mapMessage.setString("DISPLAY_STRING", msg);
								mapMessage.setLong("ettId",ettId);
								mapMessage.setInt("BADGE_COUNT", 1);
								mapMessage.setString("DEVICE_TYPE", "ANDROID");
								return mapMessage;
							}
						});
					}catch(Exception e){
						e.printStackTrace();
					}					
				}				
			}
			if (userProcessed < 500) {
				break;
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new ResponseEntity<>(HttpStatus.OK);   
  }
}
