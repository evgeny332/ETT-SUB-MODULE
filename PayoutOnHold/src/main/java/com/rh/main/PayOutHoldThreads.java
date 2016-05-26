package com.rh.main;

import java.util.concurrent.BlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rh.persistence.DBPersister;
import com.rh.persistence.entity.PayoutHoldData;
import com.rh.remote.CallRemoteUrl;

public class PayOutHoldThreads implements Runnable {
	private DBPersister dbPersister;
	private BlockingQueue<PayoutHoldData> blockingQueue = null;
	private static Log log = LogFactory.getLog(PayOutHoldThreads.class);
	String Url = "http://api.earntalktime.com/ett/api/v2/user/getPayoutHoldData/?offerDetailsOnClickId=#offerDetailsOnClickId#&eventDetailsOnClickId=#eventDetailsOnClickId#";
	public PayOutHoldThreads(BlockingQueue<PayoutHoldData> blockingQueue, DBPersister dbPersister) {
		this.blockingQueue = blockingQueue;
		this.dbPersister = dbPersister;
	}
	public void run() {
		// TODO Auto-generated method stub
		String name = Thread.currentThread().getName();
		log.info("Thread Started:"+name);
		while(true) {
			try {
				PayoutHoldData payoutHoldData= blockingQueue.take();
				int no = dbPersister.updateId(payoutHoldData.getId(), 1);
				
				if(no>0) {
					String fUrl = Url.replace("#offerDetailsOnClickId#", payoutHoldData.getOfferDetailsOnClickId()+"").replace("#eventDetailsOnClickId#", payoutHoldData.getEventDetailsOnClickId()+"");
					CallRemoteUrl callRemoteUrl = new CallRemoteUrl();
					String resp = callRemoteUrl.sendRequest(fUrl, 20000, 20000);
					if(resp!=null) {
						no = dbPersister.updateId(payoutHoldData.getId(), 2);
						log.info("payout Done for: ["+payoutHoldData+"] [no]["+no+"]");
					}
				}
				else {
					log.info("data not updated in the table not calling the url data:-"+payoutHoldData);
				}
				
			} catch (InterruptedException e) {
				log.error("Error in PayOutHoldThreads name:-"+name+",error:-"+e);
				e.printStackTrace();
			}
		}

	}

}
