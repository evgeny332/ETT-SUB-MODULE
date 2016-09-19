package com.dndbean;
import java.io.*;

public class SMS_TMReportBean 
{

	private String urn;
	private String dateval;
        private String total;
        private String allowed ;
        private String blocked;
	private String pallowed;
	private String pblocked;
	private String circle;	
	private String blockpf;
        private String blockff;
        private String pblockpf;
        private String pblockff;
	private String type;

	public void setType(String type){
                this.type= type;
        }
        public String getType(){

                return type;
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
