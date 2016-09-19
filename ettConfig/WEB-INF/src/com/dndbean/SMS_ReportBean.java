package com.dndbean;
import java.io.*;

public class SMS_ReportBean 
{

        private String datetime;
        private String total;
        private String allowed;
        private String blocked;
   	private String circle;
	private String pallowed;
        private String pblocked;
	private String blockpf;
	private String blockff;
	private String pblockpf;
	private String pblockff;

	public void setPallowed(String pallowed){
                this.pallowed= pallowed;
        }
        public String getPallowed(){

                return pallowed;
        }

	public void setBlockpf(String blockpf){
                this.blockpf= blockpf;
        }
        public String getBlockpf(){

                return blockpf;
        }

	public void setBlockff(String blockff){
                this.blockff= blockff;
        }
        public String getBlockff(){

                return blockff;
        }

	public void setPblockpf(String pblockpf){
                this.pblockpf= pblockpf;
        }
        public String getPblockpf(){

                return pblockpf;
        }

	public void setPblockff(String pblockff){
                this.pblockff= pblockff;
        }
        public String getPblockff(){

                return pblockff;
        }
        public void setPblocked(String pblocked){
                this.pblocked = pblocked;
        }
        public String getPblocked(){

                return pblocked;
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

	public void setAllowed(String allowed){
                this.allowed = allowed;
        }
        public String getAllowed(){

                return allowed;
        }

	public void setBlocked(String blocked){
                this.blocked = blocked;
        }
        public String getBlocked(){

                return blocked;
        }
	
	public void setCircle(String circle){
                this.circle = circle;
        }
        public String getCircle(){

                return circle;
        }
	

}
