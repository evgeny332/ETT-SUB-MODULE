package com.dndbean;
import java.io.*;

public class Conformance_ReportBean 
{

        private String datetime;
        private String total;
        private String connected ;
        private String disconnected;
   	private String circle ;
	private String pconnected ;
        private String pdisconnected ;
	//private String hubid ;
	
	/*public void setHubid(String hubid){
                this.hubid = hubid;
        }
        public String getHubid(){

                return hubid;
        }*/

	public void setPconnected(String pconnected){
                this.pconnected = pconnected;
        }
        public String getPconnected(){

                return pconnected;
        }


        public void setPdisconnected(String pdisconnected){
                this.pdisconnected = pdisconnected;
        }
        public String getPdisconnected(){

                return pdisconnected;
        }

	public void setDatetime(String datetime){
                this.datetime = datetime;
        }
        public String getDatetime(){

                return datetime;
        }

	public void setTotal(String total){
                this.total = total;
        }
        public String getTotal(){

                return total;
        }

	public void setConnected(String connected){
                this.connected = connected;
        }
        public String getConnected(){

                return connected;
        }

	public void setDisconnected(String disconnected){
                this.disconnected = disconnected;
        }
        public String getDisconnected(){

                return disconnected;
        }
	
	public void setCircle(String circle){
                this.circle = circle;
        }
        public String getCircle(){

                return circle;
        }
	

}
