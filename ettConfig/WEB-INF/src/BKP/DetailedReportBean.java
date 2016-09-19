package com.reports;

import java.io.*;
import java.lang.*;
import java.util.*;

public class  DetailedReportBean{

        private String dateTime;
        private String campaignName;
        private String senderId;
        private String total;
        private String success;
        private String failure;
        private String dnr;
	private String endTime;
	private String smscRejected;
	private String fileName;
       
	public String getDateTime() {
                return dateTime;
        }
 	public String getFileName() {
                return fileName;
        }
	public void setFileName(String fileName) {
                this.fileName = fileName;
		System.out.println("filenamefilenamefilenamefilename "+fileName);
        }
        public void setDateTime( String dateTime ) {
                this.dateTime = dateTime;
        }

        public String getCampaignName() {
                return campaignName;
        }

        public void setCampaignName( String campaignName ) {
                this.campaignName = campaignName;
        }

        public String getSenderId() {
                return senderId;
        }

        public void setSenderId( String senderId ) {
                this.senderId = senderId;
        }

        public String getTotal() {
                return total;
        }

	public void setTotal( String total ) {
                this.total = total;
        }

        public String getSuccess() {
                return success;
        }

        public void setSuccess( String success ) {
                this.success = success;
        }

        public String getFailure() {
                return failure;
        }

        public void setFailure( String failure ) {
                this.failure = failure;
        }

        public String getDnr() {
                return dnr;
        }

        public void setDnr( String dnr ) {
                this.dnr = dnr;
        }
	public void setEndTime(String endTime)
	{
		this.endTime = endTime;	
	
	}
	public String getEndTime(){
	
		return endTime;
	}
	public void setSmscRejected(String smscRejected)
	{
		this.smscRejected = smscRejected;
	}
	public String getSmscRejected(){

		return smscRejected;
	}
}
