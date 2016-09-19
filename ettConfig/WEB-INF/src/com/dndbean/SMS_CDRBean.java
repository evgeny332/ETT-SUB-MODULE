package com.dndbean;
import java.io.*;

public class SMS_CDRBean 
{

        private String dateval;
        private String circle;
	private String aparty;
	private String bparty;
	private String interfaceused;
	private String errorcode;
	private String senderid;
	private String timeval;

        public void setDateval(String dateval){
                this.dateval = dateval;
        }
        public String getDateval(){

                return dateval;
        }
	
	public void setTimeval(String timeval){
                this.timeval = timeval;
        }
        public String getTimeval(){

                return timeval;
        }

        public void setCircle(String circle){
                this.circle = circle;
        }
        public String getCircle(){

                return circle;
        }

        public void setAparty(String aparty){
                this.aparty = aparty;
        }
        public String getAparty(){

                return aparty;
        }

        public void setBparty(String bparty){
                this.bparty = bparty;
        }
        public String getBparty(){

                return bparty;
        }

	public void setInterfaceused(String interfaceused){
                this.interfaceused = interfaceused;
        }
        public String getInterfaceused(){

                return interfaceused;
        }
	
	public void setErrorcode(String errorcode){
                this.errorcode = errorcode;
        }
        public String getErrorcode(){

                return errorcode;
        }
		
	public void setSenderid(String senderid){
                this.senderid = senderid;
        }
        public String getSenderid(){

                return senderid;
        }

}
