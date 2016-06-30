package com.rh.main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.rh.persistence.DBPersister;
import com.rh.persistence.entity.PayoutHoldData;

public class APIImpl {

	/**
	 * @author Ankit Singh
	 */
	private DBPersister dbPersister;
	static Properties configFile = new Properties();
	public int sleep=0;
	private static Log log = LogFactory.getLog(APIImpl.class);
	private BlockingQueue<PayoutHoldData> blockingQueue = new LinkedBlockingQueue<PayoutHoldData>(1024);
	private int threadCount;
	public APIImpl(DBPersister dp) {
		this.dbPersister = dp;
		try {
			configFile.load(APIImpl.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sleep=Integer.parseInt(configFile.getProperty("SLEEP_MILLIS"));
		threadCount = Integer.parseInt(configFile.getProperty("THREAD_COUNT"));
		//log.info("sleep millis is: "+sleep);
		PayOutHoldThreads payOutHoldThreads = new PayOutHoldThreads(blockingQueue, dbPersister);
		for(int i=0;i<threadCount;i++) {
			Thread t = new Thread(payOutHoldThreads);
			t.start();
		}
		
	}


	public void holdPayOut() {
		try {
			while(true) {
				Date dNow = new Date( );
			    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			    String dateNow = ft.format(dNow);
			    
			    List<PayoutHoldData> pushList = new ArrayList<PayoutHoldData>();
			    dbPersister.getList(pushList,dateNow);
			    log.info("Date Time "+dateNow+" pushLis size : "+pushList.size() +" Thread Count : "+threadCount);
			    Iterator<PayoutHoldData> itr = pushList.iterator();
			    while(itr.hasNext()) {
			    	int size = blockingQueue.size();
			    	if(size>500) {
			    		log.info("Queue Size High Process on hold size:"+size);
			    		break;
			    	}
			    	blockingQueue.add(itr.next());
			    }
			    Thread.sleep(sleep);
			}
		}catch(Exception ex) {
		log.error("error in holdPayout error:"+ex);
		ex.printStackTrace();
		}
	}
}
	

