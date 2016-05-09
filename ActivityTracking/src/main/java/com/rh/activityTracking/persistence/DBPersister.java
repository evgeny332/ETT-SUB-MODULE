package com.rh.activityTracking.persistence;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.rh.activityTracking.service.FileProcessId;
import com.rh.persistence.domain.RedeemAmountConfig;
import com.rh.persistence.domain.UserAccountSummary;
import com.rh.persistence.domain.UserAccountSummaryRowMapper;
import com.rh.persistence.domain.UserActivityBooster;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DBPersister {
	
	private JdbcTemplate jdbcTemplate;
	private static final Log LOGGER = LogFactory.getLog(DBPersister.class);
	public DBPersister(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate=jdbcTemplate;
		
	}
	
	public long getLastMinId(){
		 String lastMin = getLastMin();
		 String query = "select id from UserAccountSummary where createdTime>='"+lastMin+"' limit 1";
		 try {
		 long id = jdbcTemplate.queryForLong(query);
		 return id;
		 }catch(EmptyResultDataAccessException ignoreEx){
			 return -1;
		 }catch(Exception ex){
			 LOGGER.info("Error in getLastMinId query:-"+query);
			 ex.printStackTrace();
			 System.exit(0);
		 }
		return -2;
	}
	
	public String getUASTime(long processId) {
		String query = "select createdTime from UserAccountSummary where id="+processId;
		List<String> certs = jdbcTemplate.queryForList(query, String.class); 
	    if (certs.isEmpty()) {
	        return null;
	    } else {
	        return certs.get(0);
	    }
	}
	public List<UserAccountSummary> getUASData(long id) {
		List<UserAccountSummary> accountSummaries = null;
		String query = "select * from UserAccountSummary where id>?";
		try {
			accountSummaries = jdbcTemplate.query(query, new Object[]{id},new BeanPropertyRowMapper(UserAccountSummary.class));
			LOGGER.debug("data is:"+accountSummaries);
		}catch(EmptyResultDataAccessException ignoreEx) {
			return null;
		}
		catch (Exception e) {
			// TODO: handle exception
			LOGGER.info("Error in getUASData id:"+id);
			e.printStackTrace();
			System.exit(0);
		}
		return accountSummaries;
	}
	
	public List<UserAccountSummary> getUASDataInviteIstApp(long lastIdProcessed,long processId) {
		List<UserAccountSummary> accountSummaries = null;
		String time1 = getUASTime(lastIdProcessed);
		String time2 = getUASTime(processId);
		LOGGER.info("Querey:-select *  from UserAccountSummary where offerId=121 and createdTime>=date_add(date_add(createdTime,INTERVAL 19800 SECOND),INTERVAL -24 HOUR) and vendor is not null and vendor>'"+time1+"' and vendor<='"+time2+"'");
		String query = "select *  from UserAccountSummary where offerId=121 and createdTime>=date_add(date_add(createdTime,INTERVAL 19800 SECOND),INTERVAL -24 HOUR) and vendor is not null and vendor>? and vendor<=?";
		try {
			accountSummaries = jdbcTemplate.query(query, new Object[]{lastIdProcessed,processId},new BeanPropertyRowMapper(UserAccountSummary.class));
			LOGGER.debug("data is UASDataInviteIstApp :"+accountSummaries);
		}catch(EmptyResultDataAccessException ignoreEx) {
			return null;
		}
		catch (Exception e) {
			// TODO: handle exception
			LOGGER.info("Error in getUASData lastIdProcessed:"+lastIdProcessed+",processId:"+processId);
			e.printStackTrace();
			System.exit(0);
		}
		return accountSummaries;
	}
	public void updateActivityBooster(UserAccountSummary accountSummary,String pushMsg,HashMap<Integer,Integer> prepaidFee){
		try{
			String query ="select id,ettId,currentBalance,targetBalance,rechargeAmount,validityStatus from UserActivityBooster where ettId=? and validityStatus=1 and validityDate>=date(date_add(now(),INTERVAL 19800 SECOND)) and isRedeemable=0";
			//UserActivityBooster userActivityBooster = jdbcTemplate.queryForObject(query,new Object[]{accountSummary.getEttId()}, UserActivityBooster.class);
			UserActivityBooster userActivityBooster = null;
			SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(query,new Object[]{accountSummary.getEttId()});
			while(sqlRowSet.next()) {
				userActivityBooster= new UserActivityBooster();
				userActivityBooster.setCurrentBalance(sqlRowSet.getInt("currentBalance"));
				userActivityBooster.setEttId(sqlRowSet.getLong("ettId"));
				userActivityBooster.setId(sqlRowSet.getLong("id"));
				userActivityBooster.setRechargeAmount(sqlRowSet.getInt("rechargeAmount"));
				userActivityBooster.setTargetBalance(sqlRowSet.getInt("rechargeAmount"));
				userActivityBooster.setValidityStatus(sqlRowSet.getInt("validityStatus"));
			}
			if(userActivityBooster==null){return;}
			query ="select round(currentBalance) from UserAccount where ettId=? ";
			int currentBalance = jdbcTemplate.queryForInt(query,new Object[]{accountSummary.getEttId()});
			
			if((userActivityBooster.getCurrentBalance()+userActivityBooster.getTargetBalance())>0 && currentBalance>=(userActivityBooster.getCurrentBalance()+userActivityBooster.getTargetBalance())) {
				LOGGER.info("Inside the condition ettId:"+accountSummary.getEttId());
				query = "update UserActivityBooster set isRedeemable=1 where ettId=?";
				jdbcTemplate.update(query, new Object[]{accountSummary.getEttId()});
				query = "insert into SchedulePush (ettId,message,pushTime) values(?,?,now())";
				pushMsg = pushMsg.replace("#TARGETBALANCE#", userActivityBooster.getTargetBalance()+"");
				pushMsg = pushMsg.replaceAll("#RECHARGEBALANCE#", userActivityBooster.getRechargeAmount()+"");
				if(prepaidFee.get(userActivityBooster.getRechargeAmount())!=null) {
					pushMsg = pushMsg.replace("#FEE#", prepaidFee.get(userActivityBooster.getRechargeAmount())+"");
				}
				else {
					pushMsg = pushMsg.replace("convenience fees of Rs.#FEE#", "convenience fees ");
				}
				jdbcTemplate.update(query, new Object[]{accountSummary.getEttId(),pushMsg});
			}
			else {
				LOGGER.info("Inside the else ettId:"+accountSummary.getEttId());
			}
		}catch(EmptyResultDataAccessException ignoreEx) {
			LOGGER.info("not found data ettId="+accountSummary.getEttId());
			
			return ;
		}
		catch(Exception ex){
			LOGGER.error("Error in updateActivityBooster ettId:"+accountSummary.getEttId());
			ex.printStackTrace();
			return;
		}
	}
	
	public void cricketT20Chance(UserAccountSummary userAccountSummary) {
		if(userAccountSummary.getOfferId()>0 && userAccountSummary.getOfferId()<5000 && (!userAccountSummary.getRemarks().equalsIgnoreCase("PENDING_CREDIT")) && (!userAccountSummary.getRemarks().equalsIgnoreCase("DATA_USAGE")) && userAccountSummary.getOfferId()!=121l) {
			String query1 = "insert ignore into  WorldCupT20Chance(ettId,chance,updateDate) values(?,5,now())";
			String query2 = "update WorldCupT20Chance set chance=chance+3 where ettId=?";
			jdbcTemplate.update(query1, new Object[]{userAccountSummary.getEttId()});
			jdbcTemplate.update(query2, new Object[]{userAccountSummary.getEttId()});
			LOGGER.info(" Cricket T20 Chance added ettId:"+userAccountSummary.getEttId());
		}
		else if(userAccountSummary.getOfferId()==121l) {
			String query1 = "insert ignore into  WorldCupT20Chance(ettId,chance,updateDate) values(?,5,now())";
			String query2 = "update WorldCupT20Chance set chance=chance+2 where ettId=?";
			jdbcTemplate.update(query1, new Object[]{userAccountSummary.getEttId()});
			jdbcTemplate.update(query2, new Object[]{userAccountSummary.getEttId()});
			LOGGER.info(" Cricket T20 Chance added ettId:"+userAccountSummary.getEttId());
		}
		else{
			
		}
	}
	
	public void callStoredProcedure(float amount,long ettId,long offerId,String remarks,String offerName,String vendor) {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
		.withProcedureName("UserAccountUpdate");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("Ramount", amount);
		inParamMap.put("RcreatedTime", new Date());
		inParamMap.put("RettId", ettId);
		inParamMap.put("RofferId", offerId);
		inParamMap.put("Rremarks", remarks);
		inParamMap.put("RofferName", offerName);
		inParamMap.put("Rcoin", 0);
		inParamMap.put("Rvendor", vendor);
		inParamMap.put("RofferCat", "");
		inParamMap.put("RdeviceIdFlage", 1);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
		//System.out.println(simpleJdbcCallResult);
	}

	public void promoUMGM(UserAccountSummary userAccountSummary){
		try {
		if(userAccountSummary.getOfferId()>0 && userAccountSummary.getOfferId()<5000 && (!userAccountSummary.getRemarks().equalsIgnoreCase("PENDING_CREDIT")) && (!userAccountSummary.getRemarks().equalsIgnoreCase("DATA_USAGE")) && userAccountSummary.getOfferId()!=121l) {
			//String query = "select count(1) from UMGMPromo where ettId="+userAccountSummary.getEttId()+" and createdTime=date(date_add(now(),INTERVAL 19800 SECOND)) and status =1";
			String query = "select appCount from UMGMPromo where ettId="+userAccountSummary.getEttId()+" and createdTime=date(date_add(now(),INTERVAL 19800 SECOND)) and status in(0,1)";
			int count = 0;
			try {
				count = jdbcTemplate.queryForInt(query);
			}catch(EmptyResultDataAccessException ignoreEx){return;}
				if(count==3) {
					callStoredProcedure(10,userAccountSummary.getEttId(),7002l,"UMGMPromo","4th app Credit","RH");
					query = "update UMGMPromo set appCount=appCount+1,status=2 where ettId="+userAccountSummary.getEttId()+" and createdTime=date(date_add(now(),INTERVAL 19800 SECOND))";
					jdbcTemplate.execute(query);
					
					LOGGER.info("UMGM promo credited to ettId:"+userAccountSummary.getEttId());
				}
				else {
				query = "update UMGMPromo set appCount=appCount+1 where ettId="+userAccountSummary.getEttId()+" and createdTime=date(date_add(now(),INTERVAL 19800 SECOND))";
				jdbcTemplate.execute(query);
				}
				
			
		}
		}catch(Exception ex) {
			LOGGER.info("Error in UMGM promo :"+ex +",ettId:"+userAccountSummary.getEttId());
			ex.printStackTrace();
		}
	}
	
	
	public void promoUMGMInvite(UserAccountSummary userAccountSummary){
		try {
		if(userAccountSummary.getOfferId()==7021l) {
			String query = "select inviteCount from IMGMPromo where ettId="+userAccountSummary.getEttId()+" and createdTime=date(date_add(now(),INTERVAL 19800 SECOND)) and status in(0,1)";
			int count = 0;
			try {
				count = jdbcTemplate.queryForInt(query);
			}catch(EmptyResultDataAccessException ignoreEx){return;}
				if(count==1) {
					callStoredProcedure(20,userAccountSummary.getEttId(),7002l,"IMGMPromo","Ist Recharge Credit","RH");
					query = "update IMGMPromo set inviteCount=inviteCount+1,status=2 where ettId="+userAccountSummary.getEttId()+" and createdTime=date(date_add(now(),INTERVAL 19800 SECOND))";
					jdbcTemplate.execute(query);
					
					LOGGER.info("IMGM promo credited to ettId:"+userAccountSummary.getEttId());
				}
				else {
				query = "update IMGMPromo set inviteCount=inviteCount+1 where ettId="+userAccountSummary.getEttId()+" and createdTime=date(date_add(now(),INTERVAL 19800 SECOND))";
				jdbcTemplate.execute(query);
				}
				
			
		}
		}catch(Exception ex) {
			LOGGER.info("Error in UMGM promo :"+ex +",ettId:"+userAccountSummary.getEttId());
			ex.printStackTrace();
		}
	}
	
	public void promoIMGM(Long lastIdProcessed,Long processId){
		List<UserAccountSummary> uASList = getUASDataInviteIstApp(lastIdProcessed,processId);
		try {
			
			
			for(UserAccountSummary userAccountSummary:uASList){
				try {
						LOGGER.info("promoIMGM:"+userAccountSummary.getEttId());
						if(true){continue;}
						String query = "select inviteCount from UMGMPromo where ettId="+userAccountSummary.getEttId()+" and createdTime=date(date_add(now(),INTERVAL 19800 SECOND)) and status in(0,1)";
						int count = 0;
						try {
							count = jdbcTemplate.queryForInt(query);
						}catch(EmptyResultDataAccessException ignoreEx){return;}
						if((count+1)==2) {
							callStoredProcedure(10,userAccountSummary.getEttId(),7002l,"IMGMPromo","2nd Invite","RH");
							query = "update UMGMPromo set appCount=appCount+1,status=2 where ettId="+userAccountSummary.getEttId()+" and createdTime=date(date_add(now(),INTERVAL 19800 SECOND))";
							jdbcTemplate.execute(query);
							LOGGER.info("UMGM promo credited to ettId:"+userAccountSummary.getEttId());
						}
						else {
							query = "update UMGMPromo set appCount=appCount+1 where ettId="+userAccountSummary.getEttId()+" and createdTime=date(date_add(now(),INTERVAL 19800 SECOND))";
							jdbcTemplate.execute(query);
						}
				}catch(Exception exc){
					LOGGER.info("Error in UMGM promo :"+exc +",ettId:"+userAccountSummary.getEttId());
					exc.printStackTrace();
				}
			}
		}catch(Exception ex) {
			LOGGER.info("Error in UMGM promo :"+ex);
			ex.printStackTrace();
		}
	}
	
	public HashMap<Integer,Integer> getPrepaidFee(){
		String query = "select amount,fee from RedeemAmountConfig where type=?";
		//RedeemAmountConfig amountConfig = jdbcTemplate.queryForObject(query,new Object[]{"PREPAID"}, RedeemAmountConfig.class);
		SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(query,new Object[]{"PREPAID"});
		HashMap<Integer,Integer> hashMap = new HashMap<Integer, Integer>();
		while(sqlRowSet.next()) {
		String amount[] = sqlRowSet.getString("amount").split(",");
		String fee[] = sqlRowSet.getString("fee").split(",");
		
		int i = 0;
		for(String fee1:fee) {
				hashMap.put(Integer.parseInt(amount[i]), Integer.parseInt(fee[i]));
				i++;
			}		
		}
		return hashMap;
	}
	public String getLastMin(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -1);
		Date date = calendar.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date1 = format1.format(date);   
		return date1;
	}
}
