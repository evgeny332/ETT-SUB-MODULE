package com.dndbean;
import java.io.*;

public class SearchBean 
{
        private String filename;
        private String msisdn;
	private String recdateval;
	private String interfaceuse;
	private String updateval;

	public String getUpdateval() {
                return updateval;
        }

        public void setUpdateval( String updateval ) {
                this.updateval = updateval;
        }

	public String getInterfaceuse() {
                return interfaceuse;
        }

        public void setInterfaceuse( String interfaceuse ) {
                this.interfaceuse = interfaceuse;
        }

	public String getRecdateval() {
                return recdateval;
        }

        public void setRecdateval( String recdateval ) {
                this.recdateval = recdateval;
        }

        public String getFilename() {
                return filename;
        }

        public void setFilename( String filename ) {
                this.filename = filename;
        }

        public String getMsisdn() {
                return msisdn;
        }

        public void setMsisdn( String msisdn ) {
                this.msisdn = msisdn;
        }

}
