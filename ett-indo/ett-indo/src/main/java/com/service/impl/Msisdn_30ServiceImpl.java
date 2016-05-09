package com.service.impl;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.domain.entity.DeviceToken;
import com.domain.entity.InviteBonusMsisdn;
import com.domain.entity.Msisdn_30;
import com.domain.entity.User;
import com.domain.entity.UserAccount;
import com.domain.entity.UserAccountSummary;
import com.domain.entity.UserSource;
import com.repository.jpa.DeviceTokenRepository;
import com.repository.jpa.Msisdn_30Repository;
import com.repository.jpa.UserAccountRepository;
import com.repository.jpa.UserAccountSummaryRepository;
import com.repository.jpa.UserSourceRepository;
import com.service.Msisdn_30Service;

@Service
public class Msisdn_30ServiceImpl implements Msisdn_30Service {

	private static Logger LOGGER = LoggerFactory.getLogger(Msisdn_30ServiceImpl.class);
	
	@Resource
	private Msisdn_30Repository msisdn_30Repository;
	
	@Resource
	private UserSourceRepository userSourceRepository;
	@Autowired
    UserAccountRepository userAccountRepository;
    
    @Autowired 
    UserAccountSummaryRepository userAccountSummaryRepository;
    
    @Autowired
    DeviceTokenRepository deviceTokenRepository; 
    
    
    @Resource
	private JmsTemplate jmsTemplate;    
	@Override
	public void updateAmount(User user,Msisdn_30 msisdn_30,int amount,long offerId,String oname) {
		
		if(amount<=0) {
			return;
		}
		UserAccount userAccount = userAccountRepository.findByEttId(user.getEttId());
		userAccount.setCurrentBalance(userAccount.getCurrentBalance()+amount);
		userAccountRepository.save(userAccount);
		
		UserAccountSummary userAccountSummary = new UserAccountSummary();
		userAccountSummary.setEttId(user.getEttId());
		//userAccountSummary.setCreatedTime(createdTime);
		userAccountSummary.setOfferId(offerId);
		userAccountSummary.setOfferName(oname);
		userAccountSummary.setAmount(amount);
		userAccountSummary.setRemarks(msisdn_30.getType()+"");
		
		userAccountSummaryRepository.save(userAccountSummary);
		DeviceToken deviceToken  = deviceTokenRepository.findByEttId(user.getEttId());
		String deToken = deviceToken.getDeviceToken();
		if(deToken != null) {
			sendPush(msisdn_30.getMsg().replaceFirst("#AMOUNT#",amount+""), deToken,user.getEttId());
		}
		
	}
	
	@Override
	public void giveInviteMoney(User user,InviteBonusMsisdn inviteBonusMsisdn) {
		UserSource userSource = userSourceRepository.findByEttId(user.getEttId());
		if(userSource!=null && userSource.getUtmCampaign()!=null && userSource.getUtmCampaign().toUpperCase().indexOf("SMS")>-1) {
		UserAccount userAccount = userAccountRepository.findByEttId(user.getEttId());
		userAccount.setCurrentBalance(userAccount.getCurrentBalance()+inviteBonusMsisdn.getAmount());
		userAccountRepository.save(userAccount);
		
		UserAccountSummary userAccountSummary = new UserAccountSummary();
		userAccountSummary.setEttId(user.getEttId());
		//userAccountSummary.setCreatedTime(createdTime);
		userAccountSummary.setOfferId(inviteBonusMsisdn.getOfferId());
		userAccountSummary.setOfferName(inviteBonusMsisdn.getOfferName());
		userAccountSummary.setAmount(inviteBonusMsisdn.getAmount());
		userAccountSummary.setRemarks(inviteBonusMsisdn.getOfferName());
		userAccountSummaryRepository.save(userAccountSummary);
		
		LOGGER.info("Invite bonus given to ettId={},msisdn={},amount={}",user.getEttId(),user.getMsisdn(),inviteBonusMsisdn.getAmount());
		DeviceToken deviceToken  = deviceTokenRepository.findByEttId(user.getEttId());
		String deToken = deviceToken.getDeviceToken();
			if(deToken != null) {
				sendPush(inviteBonusMsisdn.getMsg().replaceFirst("#AMOUNT#",inviteBonusMsisdn.getAmount()+""), deToken,user.getEttId());
			}
		}
	}
	private void sendPush(final String pushText, final String dToken,final Long ettId) {
		try{
			jmsTemplate.send(new MessageCreator() {
					@Override
					public Message createMessage(Session session) throws JMSException {

					MapMessage mapMessage = session.createMapMessage();
					mapMessage.setString("ID", dToken);
					mapMessage.setString("DISPLAY_STRING", pushText);
					mapMessage.setInt("BADGE_COUNT", 1);
					mapMessage.setLong("ettId", ettId);
					mapMessage.setString("DEVICE_TYPE", "ANDROID");
					mapMessage.setString("type", "BALANCE");
					return mapMessage;
					}
					});
		}catch(Exception e){
			LOGGER.error("error in otp controller push "+e);
			e.printStackTrace();
		}

	}

}
