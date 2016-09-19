package com.dndbean;
import java.io.*;

public class de_CircleWiseReportBean 
{

        private String filename;
	private String sdatetime;
        private String totalcount;
        private String successcount ;
        private String failcount;
	private String edatetime;

        public void setEdatetime(String edatetime){
                this.edatetime = edatetime;
        }
        public String getEdatetime(){

                return edatetime;
        }

	public void setFilename(String filename){
                this.filename = filename;
        }
        public String getFilename(){

                return filename;
        }
	
	public void setSdatetime(String sdatetime){
                this.sdatetime = sdatetime;
        }
        public String getSdatetime(){

                return sdatetime;
        }


	public void setTotalcount(String totalcount){
                this.totalcount = totalcount;
        }
        public String getTotalcount(){

                return totalcount;
        }

	public void setSuccesscount(String successcount){
                this.successcount = successcount;
        }
        public String getSuccesscount(){

                return successcount;
        }

	public void setFailcount(String failcount){
                this.failcount = failcount;
        }
        public String getFailcount(){

                return failcount;
        }
	

}
