package com.dndbean;
import java.io.*;

public class BlockReport_downloadBean 
{

        private String datetime;
	private String filename;

	public void setFilename(String filename){
                this.filename = filename;
        }
        public String getFilename(){

                return filename;
        }
	
	public void setDatetime(String datetime){
                this.datetime = datetime;
        }
        public String getDatetime(){

                return datetime;
        }

}
