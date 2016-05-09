package com.rh.ett.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface InviteContactDAO {
	public void setDataSource(DataSource ds);
	@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
	public void otpRequstInsert(long msisdn,long ettId);
	@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
	public void deleteInviteContact(long ettId);
	@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<String> listMsisdn(String msisdnData);
	@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
	public void insertInviteContactMsisdn(long ettId,List<String> msisdnList,List<String> allreadyMsisdnList);
	@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
	int getInviteAllreadyContact(Long ettId);
	@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
	void updateStatusInviteContact(long msisdn);
}
