package com.rh.ett.service;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rh.ett.dao.InviteContactDAO;
import com.rh.ett.dao.InviteContactDAOImpl;

public class InviteContactProcessImpl implements InviteContactProcess {
	
	private static Log log = LogFactory.getLog(InviteContactProcessImpl.class);
	
	InviteContactDAO inviteContactDAO;
	public InviteContactProcessImpl(InviteContactDAO inviteContactDAO){
		this.inviteContactDAO = inviteContactDAO;
	}
	
	@Override
	public void otpRequstProcess(long msisdn, long ettId) {
		// TODO Auto-generated method stub
		int size = inviteContactDAO.getInviteAllreadyContact(ettId);
		if(size>0){
			long time1=System.currentTimeMillis();
			inviteContactDAO.deleteInviteContact(ettId);
			log.info("processing time in deleteInviteContact ettId:"+ettId+", time:"+(System.currentTimeMillis()-time1));
		}
		long time1=System.currentTimeMillis();
		inviteContactDAO.otpRequstInsert(msisdn, ettId);
		log.info("processing time in otpRequstInsert ettId:"+ettId+", msisdn:"+msisdn+", time:"+(System.currentTimeMillis()-time1));
	}

	@Override
	public void contactFilter(String msisdnData, Long ettId) {
		// TODO Auto-generated method stub
		int size = inviteContactDAO.getInviteAllreadyContact(ettId);
		if(size>0){
			long time1=System.currentTimeMillis();
			inviteContactDAO.deleteInviteContact(ettId);
			log.info("processing time in deleteInviteContact ettId:"+ettId+", time:"+(System.currentTimeMillis()-time1));
		}
		List<String> allreadyMsisdnList = new ArrayList<String>();
		List<String> msisdnList = Arrays.asList((msisdnData.replaceAll(" ", "").split(",")));
		String msisdnList1[] = (msisdnData.replaceAll(" ", "").split(","));
		long time1 = System.currentTimeMillis();
		allreadyMsisdnList= inviteContactDAO.listMsisdn(msisdnData);
		log.info("processing time in listMsisdn ettId:"+ettId+", allreadyMsisdnList:"+allreadyMsisdnList+", time:"+(System.currentTimeMillis()-time1));
		time1 = System.currentTimeMillis();
		inviteContactDAO.insertInviteContactMsisdn(ettId, msisdnList, allreadyMsisdnList);
		log.info("processing time in insertInviteContactMsisdn ettId:"+ettId+", msisdnList:"+msisdnList+", time:"+(System.currentTimeMillis()-time1));
	}

}
