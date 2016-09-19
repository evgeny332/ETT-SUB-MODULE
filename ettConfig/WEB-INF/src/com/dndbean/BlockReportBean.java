package com.dndbean;
import java.io.*;

public class BlockReportBean 
{

        private String name;
	private String urn;
	private String dateval;
	private String timeval;
        private String aparty;
        private String bparty;
        private String action;
	private String circle;

	public void setUrn(String urn){
                this.urn = urn;
        }
        public String getUrn(){

                return urn;
        }	


	public void setName(String name){
                this.name = name;
        }
        public String getName(){

                return name;
        }
	
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

	public void setAction(String action){
                this.action = action;
        }
        public String getAction(){

                return action;
        }

	public void setCircle(String circle){
                this.circle = circle;
        }
        public String getCircle(){

                return circle;
        }
	

}
