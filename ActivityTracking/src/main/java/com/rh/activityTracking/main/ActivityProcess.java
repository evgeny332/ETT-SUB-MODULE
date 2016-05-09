package com.rh.activityTracking.main;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rh.activityTracking.persistence.DBPersister;
import com.rh.activityTracking.service.FileProcessId;
import com.rh.persistence.domain.UserAccountSummary;
public class ActivityProcess {

		DBPersister dbPersister;
		List<UserAccountSummary> uASList = null;
		long sleepTime;
		String pushMsg;
		private static final Log LOGGER = LogFactory.getLog(ActivityProcess.class);
		public ActivityProcess(DBPersister dbPersister,long sleepTime,String pushMsg){
			this.dbPersister=dbPersister;
			this.sleepTime=sleepTime;
			this.pushMsg=pushMsg;
			processActivities();
		}
		
		public void processActivities(){
			try {
				long IstId = dbPersister.getLastMinId();
				FileProcessId fileProcessId = new FileProcessId(dbPersister);
				fileProcessId.updateIdInFile(IstId);
				while(true) {
					long lastIdProcessed = fileProcessId.getLastIdProcessed();
					if(lastIdProcessed>0) {
						uASList = dbPersister.getUASData(lastIdProcessed);
						/*if(uASList !=null) {
							LOGGER.info("Data received :"+uASList);
						}*/
						if(uASList == null || uASList.size()==0) {
							continue;
						}
					
						long processId = 0;
						HashMap<Integer,Integer> prepaidFee  = dbPersister.getPrepaidFee();
						for(UserAccountSummary accountSummary:uASList){
							//processId=accountSummary.getId();
							//LOGGER.info("Id processed:"+processId);
							//activityBoosterUpdate
							/*long time1 = System.currentTimeMillis();
							if(accountSummary.getAmount()>0.0f){
								dbPersister.updateActivityBooster(accountSummary,pushMsg,prepaidFee);
							}
							LOGGER.info("update Activity Booster finish for ettId:"+accountSummary.getEttId()+",timeDiff:"+(System.currentTimeMillis()-time1));
						*/	//activityBoosterUpdate End
							
							//Cricket chance give
							/*time1 = System.currentTimeMillis();
							dbPersister.cricketT20Chance(accountSummary);
							LOGGER.info("update Cricket T20 Chance finish for ettId:"+accountSummary.getEttId()+",timeDiff:"+(System.currentTimeMillis()-time1));
*/							//Cricket chance give
							
							//UMGM (Use More Gain More) Promo
							/*time1 = System.currentTimeMillis();
							dbPersister.promoUMGM(accountSummary);
							LOGGER.info("iMGM Promo finish for ettId:"+accountSummary.getEttId()+",timeDiff:"+(System.currentTimeMillis()-time1));*/
							//UMGM (Use More Gain More) Promo
							
							
							//IMGM (Use More Gain More) Promo
							long time1 = System.currentTimeMillis();
							dbPersister.promoUMGMInvite(accountSummary);
							LOGGER.info("IMGM Promo finish for ettId:"+accountSummary.getEttId()+",timeDiff:"+(System.currentTimeMillis()-time1));
							//IMGM (Use More Gain More) Promo
							
							
							
							processId = accountSummary.getId();
						}
						//IMGM (Use More Gain More) Promo
						/*Long time1 = System.currentTimeMillis();
						dbPersister.promoIMGM(lastIdProcessed,processId);
						LOGGER.info("IMGM Promo finish for ettId:timeDiff:"+(System.currentTimeMillis()-time1));
						*///IMGM (Use More Gain More) Promo
						fileProcessId.updateIdInFile(processId);
					}else {
						LOGGER.info("something wrong id is negative id:"+lastIdProcessed);
					}
					Thread.sleep(sleepTime);
				}
				
			}
			catch(Exception ex){
				ex.printStackTrace();
				System.exit(0);
			}
		}
}
