	package com.rh.persistence;


import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.print.attribute.standard.DateTimeAtCompleted;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.rh.main.APIImpl;
import com.rh.persistence.domain.*;

public class DBPersister {

	/**
	 * @author Ankit Singh
	 */
	private JdbcTemplate jdbcTemplate;
	static Properties configFile = new Properties();
	private static Log log = LogFactory.getLog(DBPersister.class);
	
	int delayHour=0;
	
	public DBPersister(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		try {
			configFile.load(APIImpl.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		delayHour=Integer.parseInt(configFile.getProperty("PUSH_DELAY_HOUR"));
		log.info("delayHour is :"+delayHour);
	}
	
	 public void batchInsert(List<String> queries) { 
		 String[] query = queries.toArray(new String[queries.size()]);
		 jdbcTemplate.batchUpdate(query);
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
	public List<PendingCredits> getTodayList(List<PendingCredits> creditList, String startDate, String endDate,String APP_OPEN_COM_OFFERID) {
		//String query="select * from PendingCredits where date(date_add(creditDate,INTERVAL 19800 SECOND))=date(date_add(now(),INTERVAL 19800 SECOND)) and eligibleStatus=0 and amount>0 and offerId<>115";
		String query = "select * from PendingCredits where creditDate>='"+startDate+"' and creditDate<'"+endDate+"' and eligibleStatus=0 and amount>0 and offerId not in("+APP_OPEN_COM_OFFERID+")";
		log.info("Select Query : "+query);
		/*System.out.println("Select Query : "+query);
		System.exit(0);
*/		List<Map<String,Object>> listRow = jdbcTemplate.queryForList(query);
		for(Map<String,Object> cRow : listRow){
			Long ettId = (Long) cRow.get("ettId");
			/*try {
					User user = getUser(ettId);
					if(user!=null && user.getAppVersion()!=null && Float.parseFloat(user.getAppVersion())>=5.0f){
						continue;
					}
					else {
						log.info("checking the version in else part ettId-"+ettId+", appVersion-"+user.getAppVersion());
					}
			}catch(Exception ex){
				log.info("Error in getTodayList Error-"+ex+",ettId-"+ettId);
				ex.printStackTrace();
			}*/
			PendingCredits pendingCredits=new PendingCredits();
			pendingCredits.setAmount(Float.parseFloat(String.valueOf(cRow.get("amount"))));
			pendingCredits.setAppKey(String.valueOf(cRow.get("appKey")));
			pendingCredits.setCreatedTime((Date) cRow.get("createdTime"));
			pendingCredits.setEttId(ettId);
			pendingCredits.setOfferId((Long) cRow.get("offerId"));
			pendingCredits.setId((String) cRow.get("id"));
			//pendingCredits.setIsCredited((Boolean) cRow.get("isCredited"));
			pendingCredits.setVendor((String) cRow.get("vendor"));
			pendingCredits.setAutoId((Long) cRow.get("autoId"));
			creditList.add(pendingCredits);
		}
		return creditList;
	}

	@SuppressWarnings("unchecked")
	public UnInstalledApps unInstallCheck(final PendingCredits pendingCredit) {
		UnInstalledApps unInstalledApps=null;
		try{
			//String query="select * from UnInstalledApps where id='"+pendingCredit.getEttId()+"_"+pendingCredit.getAppKey()+"'";
			String query="select * from UnInstalledApps where id = ?";
			try {
				//unInstalledApps = (UnInstalledApps) jdbcTemplate.queryForObject(query, new UnInstalledAppsRowMapper());
				List<UnInstalledApps> unInstalledAppsList = jdbcTemplate.query(query, new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, pendingCredit.getEttId()+"_"+pendingCredit.getAppKey());
					}
				}, new UnInstalledAppsRowMapper());
				if(unInstalledAppsList != null && unInstalledAppsList.size()>0){
					unInstalledApps = unInstalledAppsList.get(0);
				}
			} catch(EmptyResultDataAccessException ignoreEx){}
		}catch(Exception e){
			log.error("error in UnInstall Check query :"+pendingCredit.getEttId()+" "+e);
			e.printStackTrace();
		}
		return unInstalledApps;
	}

	public User getUser(Long ettId) {
		User user=null;
		try{
			String query="select * from User where ettId="+ettId;
			try {
				user= (User) jdbcTemplate.queryForObject(query, new UserRowMapper());
			} catch(EmptyResultDataAccessException ignoreEx){}
		}catch(Exception e){
			log.error("error in query :"+ettId+" "+e);
			e.printStackTrace();
		}
		return user;
	}

	public void creditBalance(UserAccountSummary userAccountSummary, final PendingCredits penCredits, final String msg) {
		String appKey ="";
		if(penCredits.getAppKey().contains("'")){
			appKey = penCredits.getAppKey().replaceAll("'", "''");
		}
		else{
			appKey = penCredits.getAppKey();
		}
		//String query1="update UserAccount set currentBalance=currentBalance+"+penCredits.getAmount()+" where ettId="+penCredits.getEttId();
		//String query2="insert into UserAccountSummary (amount,createdTime,ettId,offerId,remarks,offerName,vendor) values("+userAccountSummary.getAmount()+",date(date_add(now(),INTERVAL 19800 SECOND)),"+userAccountSummary.getEttId()+","+userAccountSummary.getOfferId()+",'"+userAccountSummary.getRemarks()+"','"+appKey+"','"+userAccountSummary.getVendor()+"')";
		//String query3="update PendingCredits set eligibleStatus=2 where id='"+penCredits.getId()+"'";
		String query3="update PendingCredits set eligibleStatus=2 where autoId="+penCredits.getAutoId();
		//String query4="insert into SchedulePush (ettId,message,pushTime) values (?,?,date_add(now(),INTERVAL "+delayHour+" HOUR))";
		//String query4="insert into SchedulePush (ettId,message,pushTime) values (?,?,date_add(now(),INTERVAL "+delayHour+" HOUR))";
		String query4="insert into SchedulePush (ettId,message,pushTime) values (?,?,now())";
		List<String> queries = new ArrayList<>();
		//queries.add(query1);
		//queries.add(query2);
		queries.add(query3);
		
		//queries.add(query4);
		batchInsert(queries);
		callStoredProcedure(userAccountSummary.getAmount(),userAccountSummary.getEttId(),userAccountSummary.getOfferId(),userAccountSummary.getRemarks(),userAccountSummary.getOfferName(),userAccountSummary.getVendor());
		/*jdbcTemplate.update(query3, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, penCredits.getId());
			}
		});*/
		jdbcTemplate.execute(query4, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setLong(1, penCredits.getEttId());
				ps.setString(2, msg);
				return ps.execute();
			}
		});
	}

	public DeviceToken getDeviceToken(String deviceId) {
		DeviceToken deviceToken=null;
		try{
		String query = "select * from DeviceToken where deviceId='"+deviceId+"'";
		deviceToken = (DeviceToken) jdbcTemplate.queryForObject(query, new DeviceTokenRowMapper());
		}catch(Exception e){
			log.error("error in getting deviceToken"+deviceId);
			e.printStackTrace();
		}
		return deviceToken;
	}

	public void getAppKey(List<PendingCreditsReport> reportList) {
		String query1="select appKey,count(ettId) as ettCount from PendingCredits where date(date_add(creditDate,INTERVAL 19800 SECOND))=date(date_add(now(),INTERVAL 19800 SECOND)) group by appKey";
		List<Map<String,Object>> listRow = jdbcTemplate.queryForList(query1);
		for(Map<String,Object> cRow : listRow){
			PendingCreditsReport report=new PendingCreditsReport();
			report.setAppKey((String) cRow.get("appKey"));
			report.setTotalApp((Long) cRow.get("ettCount"));
			reportList.add(report);
		}
	}

	public void getReport(PendingCreditsReport report) {
		String query="select offerName,sum(amount) as amount,count(ettId) as count from UserAccountSummary where date(date_add(createdTime,INTERVAL 19800 SECOND))=date(date_add(now(),INTERVAL 19800 SECOND)) and remarks='PENDING_CREDIT' and offerName='"+report.getAppKey()+"'";
		try{
			try{
				List<Map<String, Object>> rows= jdbcTemplate.queryForList(query);
				for (Map row : rows) {
					report.setEligibleUser((Long) row.get("count"));
					report.setNotEligibleUser(report.getTotalApp()-report.getEligibleUser());
					report.setTotalAmountCredited((Double) row.get("amount"));
				}
			} catch(EmptyResultDataAccessException ignoreEx){}		
		}catch(Exception e){
			log.error("error in getting UserAccountSummary"+e);
			e.printStackTrace();
		}
	}

	public void insertReport(PendingCreditsReport report) {
		String query="insert into PendingCreditsReport (appKey,creditDate,totalApp,eligibleUser,notEligibleUser,totalAmountCredited) values ('"+report.getAppKey()+"',date(date_add(now(),INTERVAL 19800 SECOND)),"+report.getTotalApp()+","+report.getEligibleUser()+","+report.getNotEligibleUser()+","+report.getTotalAmountCredited()+")";
		log.info(query);
		jdbcTemplate.update(query);
	}

	public void getAllCreditList(List<PendingCredits> allCreditList, String startDate, int limit, int offset) {
		//String query="select * from PendingCredits where eligibleStatus=0 and amount>0 and date(date_add(creditDate,INTERVAL 19800 SECOND))>date(date_add(now(),INTERVAL 19800 SECOND)) and date(date_add(createdTime,INTERVAL 19800 SECOND))<date(date_add(now(),INTERVAL 19800 SECOND))";
		//String query="select * from PendingCredits where date(date_add(creditDate,INTERVAL 19800 SECOND))>date(date_add(now(),INTERVAL 19800 SECOND)) and date(date_add(createdTime,INTERVAL 19800 SECOND))<date(date_add(now(),INTERVAL 19800 SECOND)) and eligibleStatus=0 and amount>0 limit "+limit+" offset "+offset;
		String query="select * from PendingCredits where creditDate>'"+startDate+"' and createdTime<'"+startDate+"' and eligibleStatus=0 and amount>0 limit "+limit+" offset "+offset;
		log.info("unInstallCheck query : "+query);
		List<Map<String,Object>> listRow = jdbcTemplate.queryForList(query);
		for(Map<String,Object> cRow : listRow){
			PendingCredits pendingCredits=new PendingCredits();
			pendingCredits.setAmount(Float.parseFloat(String.valueOf(cRow.get("amount"))));
			pendingCredits.setAppKey(String.valueOf(cRow.get("appKey")));
			pendingCredits.setCreatedTime((Date) cRow.get("createdTime"));
			pendingCredits.setEttId( (Long) cRow.get("ettId"));
			pendingCredits.setOfferId((Long) cRow.get("offerId"));
			pendingCredits.setIsCredited((Boolean) cRow.get("isCredited"));
			pendingCredits.setId((String) cRow.get("id"));
			pendingCredits.setAutoId((long) cRow.get("autoId"));
			allCreditList.add(pendingCredits);
		}
		
	}

	public void updateStartedOffers(PendingCredits pendingCredit) {
		/*List<Long> offerIds = new ArrayList<>();
		String query = "select offerId from OffersStarted where ettId="+pendingCredit.getEttId();
		try{
			try{
				List<Map<String,Object>> listRow = jdbcTemplate.queryForList(query);
				for(Map<String,Object> cRow : listRow){
					offerIds.add((Long) cRow.get("offerId"));
				}
			} catch(EmptyResultDataAccessException ignoreEx){}
		}catch(Exception e){
			log.error("error in query OffersStarted : "+e);
			e.printStackTrace();
		}
		if(offerIds.size()>0 && offerIds.contains(pendingCredit.getOfferId())){
			String insertQuery="insert into OffersStartedBack select * from OffersStarted where id='"+pendingCredit.getEttId()+"_"+pendingCredit.getOfferId()+"'";
			jdbcTemplate.execute(insertQuery);
			log.info("OffersStarted/insertQuery | "+insertQuery);
			String deleteQuery="delete from OffersStarted where id='"+pendingCredit.getEttId()+"_"+pendingCredit.getOfferId()+"'";
			jdbcTemplate.execute(deleteQuery);
			log.info("OffersStarted/deletQuery | "+deleteQuery);
		}*/
		String query = "update OffersStarted set status=0,notEligibleDate=now() where id='"+pendingCredit.getEttId()+"_"+pendingCredit.getOfferId()+"'";
		try{
			int rows = jdbcTemplate.update(query);
			if(rows > 0){
				log.info("OffersStarted change to status 0 where | ettId="+pendingCredit.getEttId()+" and offerId="+pendingCredit.getOfferId());
			}
		}catch(Exception e){
			log.error("error in query OffersStarted : "+e);
			e.printStackTrace();
		}
		
	}

	public List<PendingCredits> getTodaysStatus5Users(String startDate, String endDate) {
		List<PendingCredits> status5User = new ArrayList<>();
		//String query="select * from PendingCredits where date(date_add(creditDate,INTERVAL 19800 SECOND))=date(date_add(now(),INTERVAL 19800 SECOND)) and eligibleStatus=5 and amount>0";
		String query="select * from PendingCredits where creditDate>='"+startDate+"' and creditDate<'"+endDate+"' and eligibleStatus=5 and amount>0";
		log.info("Select Query for status5 User : "+query);
		List<Map<String,Object>> listRow = jdbcTemplate.queryForList(query);
		for(Map<String,Object> cRow : listRow){
			PendingCredits pendingCredits=new PendingCredits();
			pendingCredits.setAmount(Float.parseFloat(String.valueOf(cRow.get("amount"))));
			pendingCredits.setAppKey(String.valueOf(cRow.get("appKey")));
			pendingCredits.setCreatedTime((Date) cRow.get("createdTime"));
			pendingCredits.setEttId( (Long) cRow.get("ettId"));
			pendingCredits.setOfferId((Long) cRow.get("offerId"));
			pendingCredits.setId((String) cRow.get("id"));
			//pendingCredits.setIsCredited((Boolean) cRow.get("isCredited"));
			pendingCredits.setVendor((String) cRow.get("vendor"));
			status5User.add(pendingCredits);
		}
		return status5User;
	}

	public void insertIntoPendingCreditsStatus5(PendingCredits pendingCredit) {
		String query="insert ignore into PendingCreditsStatus5User (id,ettId,offerId,offeredAmount,givenAmount,payoutDate,eligibleStatus,appKey,createdTime) values ('"+pendingCredit.getId()+"',"+pendingCredit.getEttId()+","+pendingCredit.getOfferId()+","+pendingCredit.getAmount()+",0,date_add(now(),INTERVAL 19800 SECOND),0,'"+pendingCredit.getAppKey()+"','"+pendingCredit.getCreatedTime()+"')";
		jdbcTemplate.execute(query);
	}

	
}
