package com.rh.edrconsumer.spring.scheduler;

import java.io.IOException;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rh.edrconsumer.interfaces.APIInterface;
import com.rh.edrconsumer.persistence.DBPersister;
import com.rh.persistence.edrconsumer.domain.OfferDetails;

public class ConfigSheduler {
	
	private static Log log = LogFactory.getLog(ConfigSheduler.class);
	
	static Properties configFile = new Properties();

	private Vector<String> offerList = new Vector<>();
	private ConcurrentHashMap<String, OfferDetails> offerDetails = new ConcurrentHashMap<>();
	private DBPersister dbPersister;
	int minOfferAmount = 0;
	private String offerIdsToIgnore = "";
	
	public ConfigSheduler(DBPersister dp){
		this.dbPersister = dp;
		try {
			configFile.load(ConfigSheduler.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		minOfferAmount = Integer.parseInt(configFile.getProperty("MIN_APP_AMOUNT"));
		offerIdsToIgnore = configFile.getProperty("OFFERID_TO_IGNORE").trim();
	}
	
	public void updateOffers(){
		dbPersister.updateOfferToCheck(offerList,offerDetails,minOfferAmount,offerIdsToIgnore);
		log.info("config app list updated : "+offerList);
		//System.out.println("updated offerLis:"+offerList);
	}
	
	public Vector<String> getOfferList(){
		return offerList;
	}
	
	public OfferDetails getOfferDetails(String appKey){
		return offerDetails.get(appKey);
	}
}
