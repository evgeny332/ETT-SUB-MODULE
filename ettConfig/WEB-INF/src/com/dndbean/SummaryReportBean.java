package com.dndbean;
import java.io.*;

public class SummaryReportBean 
{

        private String file;
	private String dateval;
	private String timeval;
        private String total;
        private String success ;
        private String fail;

	public void setFile(String file){
                this.file = file;
        }
        public String getFile(){

                return file;
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
	

	public void setTotal(String total){
                this.total = total;
        }
        public String getTotal(){

                return total;
        }

	public void setSuccess(String success){
                this.success = success;
        }
        public String getSuccess(){

                return success;
        }

	public void setFail(String fail){
                this.fail = fail;
        }
        public String getFail(){

                return fail;
        }
	

}
