package com.rh.persistence.domain;

import java.util.Date;


public class InviteContactMsisdn {
	

   
    private long id;

    private long ettIdAparty;
    
    private long msisdn;
    
    private boolean status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getEttIdAparty() {
		return ettIdAparty;
	}

	public void setEttIdAparty(long ettIdAparty) {
		this.ettIdAparty = ettIdAparty;
	}

	public long getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(long msisdn) {
		this.msisdn = msisdn;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
    
    
    	 
}

