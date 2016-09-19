package com.dndbean;
import java.io.*;

public class DndSearchBean
{

        private String datetime;
        private String circle;
	private String aparty;
	private String bparty;
	private String mscnumber;
	private String callaction;
	private String scpid;
	private String timeval;

        public void setDatetime(String datetime){
                this.datetime = datetime;
        }
        public String getDatetime(){

                return datetime;
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

	public void setMscnumber(String mscnumber){
                this.mscnumber = mscnumber;
        }
        public String getMscnumber(){

                return mscnumber;
        }
	
	public void setCallaction(String callaction){
                this.callaction = callaction;
        }
        public String getCallaction(){

                return callaction;
        }
		
	public void setScpid(String scpid){
                this.scpid = scpid;
        }
        public String getScpid(){

                return scpid;
        }

}
