package com.rh.activityTracking.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rh.activityTracking.persistence.DBPersister;



public class FileProcessId {

	private static final Log LOGGER = LogFactory.getLog(FileProcessId.class);
	DBPersister dbPersister = null;
		public FileProcessId(DBPersister dbPersister){
			this.dbPersister =  dbPersister;
		}
		public long getLastIdProcessed(){
			try {
				BufferedReader brTest = new BufferedReader(new FileReader("UserAccountSummaryLastProcessed.txt"));
				long id = Long.parseLong(brTest.readLine());
				brTest.close();
				if(id<=0) {
					id = getLastMinIdFromDB();
					
				}
				return id;
			}
			catch(Exception ex) {
				ex.printStackTrace();
				LOGGER.error("Error in File Reading for the max ID from UserAccountSummary file... now going for last min ID");
				long id = getLastMinIdFromDB();
				return id;
				
			}
		}
		
		public void updateIdInFile(long id){
			try {
				File file = new File("UserAccountSummaryLastProcessed.txt");
				FileWriter  fileWriter  = new FileWriter (file,false);
				fileWriter.write(id+"\n");
				fileWriter.close();
			}catch(Exception ex){
				LOGGER.info("Error in updateIdInFile:"+ex);
				ex.printStackTrace();
				System.exit(0);
			}
		}
		public long getLastMinIdFromDB(){
			try {
				long id = dbPersister.getLastMinId();
				return id;
			}
			catch(Exception ex){
				ex.printStackTrace();
				LOGGER.error("Error while getting the last min ID from DB so shutting process down...");
				System.exit(0);
			}
			return -3;
		}
}
