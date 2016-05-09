package com.rh.ett.service;

public interface InviteContactProcess {
	public void otpRequstProcess(long msisdn,long ettId);
	public void contactFilter(String msisdnData,Long ettId);
}
