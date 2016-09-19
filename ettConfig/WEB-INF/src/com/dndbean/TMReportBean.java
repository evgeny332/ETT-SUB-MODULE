package com.dndbean;
import java.io.*;

public class TMReportBean 
{

        private String name;
	private String urn;
	private String dateval;
        private String total;
        private String allowed ;
        private String blocked;
	private String pallowed;
	private String pblocked;
	private String circle;	

	public void setCircle(String circle){
                this.circle = circle;
        }
        public String getCircle(){

                return circle;
        }
	
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

	public void setAllowed(String allowed){
                this.allowed = allowed;
        }
        public String getAllowed(){

                return allowed;
        }
	

	public void setTotal(String total){
                this.total = total;
        }
        public String getTotal(){

                return total;
        }

	public void setBlocked(String blocked){
                this.blocked = blocked;
        }
        public String getBlocked(){

                return blocked;
        }

	public void setPallowed(String pallowed){
                this.pallowed = pallowed;
        }
        public String getPallowed(){

                return pallowed;
        }

	public void setPblocked(String pblocked){
                this.pblocked = pblocked;
        }
        public String getPblocked(){

                return pblocked;
        }
	

}
