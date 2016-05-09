package com.rh.edrconsumer.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.rh.persistence.edrconsumer.domain.OfferDetails;
import com.rh.persistence.edrconsumer.domain.UnInstalledApps;
import com.rh.persistence.edrconsumer.domain.UnInstalledAppsRowMapper;
import com.rh.persistence.edrconsumer.domain.User;
import com.rh.persistence.edrconsumer.domain.UserBlackList;
import com.rh.persistence.edrconsumer.domain.UserCompetitorApps;
import com.rh.persistence.edrconsumer.domain.UserPushInfo;
import com.rh.persistence.edrconsumer.domain.UserRowMapper;
import com.rh.persistence.edrconsumer.domain.DeviceToken;
import com.rh.persistence.edrconsumer.domain.DeviceTokenRowMapper;
import com.rh.persistence.edrconsumer.domain.DeviceToken;
import com.rh.persistence.edrconsumer.domain.UserServiceClass;
import com.rh.persistence.edrconsumer.domain.UserServiceClassRowMapper;


/**
 * Spring's helper for working with JDBC.
 */
public class DBPersister {
	private JdbcTemplate jdbcTemplate;
	private static Log log = LogFactory.getLog(DBPersister.class);

	public DBPersister(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	
	 public void batchInsert(List<String> queries) { 
		 String[] query = queries.toArray(new String[queries.size()]);
		 jdbcTemplate.batchUpdate(query);
	 }
	 
	 public void persistUnInstallEdr(final Long ettId,final List<String> currentUnInstalleds){
	 	jdbcTemplate.batchUpdate("insert ignore into UnInstalledApps (id,appKey,createdTs,ettId) values(?,?,now(),?)", new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, ettId +"_"+ currentUnInstalleds.get(i));
				ps.setString(2, currentUnInstalleds.get(i));
				ps.setLong(3, ettId);
			}
			@Override
			public int getBatchSize() {
				return currentUnInstalleds.size();
			}
		});
	 }
	 
	 public void moveUnInstalledApp(long ettId,String appKey) {
		 try{
			 final UnInstalledApps unInstalledApps = (UnInstalledApps) jdbcTemplate.queryForObject("select * from UnInstalledApps where id=?", new Object[]{ettId +"_"+ appKey}, new UnInstalledAppsRowMapper()); 
			 if(unInstalledApps == null)
				 return;
			 jdbcTemplate.update("insert into UnInstalledAppsMove(id,appKey,createdTs,ettId) values(?,?,now(),?)", new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, unInstalledApps.getId());
						ps.setString(2, unInstalledApps.getAppKey());
						ps.setLong(3, unInstalledApps.getEttId());
					}
				});
			 jdbcTemplate.update("delete from UnInstalledApps where id=?", new Object[]{ettId +"_"+ appKey});
		} catch (EmptyResultDataAccessException ignoreEx) {
			return;
		} catch (Exception ex) {
			log.error("error in moveUnInstalledApp|" + ex + "|" + ettId + "|appKey|" + appKey);
			ex.printStackTrace();
		}
	 }

	 public List dbUnInstallAppEdr(Long ettId){
		 String query = "select appKey from UnInstalledApps where ettId=?";
		 List<String> appKeys = jdbcTemplate.queryForList(query, new Object[]{ettId}, String.class);
		 return appKeys;
	 }
	 
	 public List<String> dbInstallAppEdr(Long ettId){
		 List<String> appKeys = jdbcTemplate.queryForList("select appKey from InstalledApps where ettId=?", new Object[]{ettId}, String.class);
		 return appKeys;
	 }
	 
	 public void insertClickCDR(final Long ettId,final Long offerId,final String appKey){
		 String query = "insert into ClickDayRecord(ettId,offerId,appKey,createdTime) values(?,?,?,now())";
		 jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, ettId);
				ps.setLong(2, offerId);
				ps.setString(3, appKey);
			}
		});
	 }
	 
	 public int getDayCount(Long ettId) {
		 String query = "select count(1) from ClickDayRecord where ettId=?";
		 int count = jdbcTemplate.queryForInt(query, new Object[]{ettId});
		 return count;
	 }
	 
	 public void deleteClickDayRecord(Long ettId) {
		 String query = "delete from ClickDayRecord where ettId=?";
		 jdbcTemplate.update(query, new Object[]{ettId});
	 }
	 
	 public boolean updateClickBalance(final Long ettId,final Long offerId,boolean isRsComplt) {
		 boolean par = false;
		 String query="select count(1) from ClickDayPayMent where ettId=? and date(DATE_ADD(createdTime,INTERVAL 19800 SECOND))=date(DATE_ADD(now(),INTERVAL 19800 SECOND))";
		 //log.info(query);
		 int count = jdbcTemplate.queryForInt(query,new Object[]{ettId});
		 if(count==0){
		 if(isRsComplt) {
				 //query = "insert into UserAccountSummary(amount,createdTime,ettId,offerId,remarks,offerName,coin) values(1,now(),"+ettId+","+offerId+","+"'Click_Offer',"+"'Click',0)";
				 query = "insert into UserAccountSummary(amount,createdTime,ettId,offerId,remarks,offerName,coin) values(1,now(),?,?,?,?,0)";
				 jdbcTemplate.update(query, new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setLong(1, ettId);
						ps.setLong(2, offerId);
						ps.setString(3, "Click_Offer");
						ps.setString(4, "Click");
					}
				});
				 
				 query = "insert into UserEarningView(UASId,amount,createdTime,ettId,offerName) values(0,1,now(),?,?)";
				 jdbcTemplate.update(query, new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setLong(1, ettId);
						ps.setString(2, "Click");
					}
				});
				
				query = "insert into ClickDayPayMent (ettId,createdTime) values(?,now())";
				jdbcTemplate.update(query, new Object[]{ettId});
				par = true;
			}
			 
		 	query = "update UserAccount set currentBalance=ROUND(currentBalance,2)+.10 where ettId=?";
			jdbcTemplate.update(query, new Object[]{ettId});
		 }
		 return par;
	 }
	 
	 public DeviceToken getDeviceToken(Long  ettId) {
		DeviceToken deviceToken = null;
		try {
			String query = "select * from DeviceToken where ettId=?";
			deviceToken = (DeviceToken) jdbcTemplate.queryForObject(query, new Object[]{ettId}, new DeviceTokenRowMapper());
		} catch (Exception e) {
			log.error("error in getting deviceToken of ettId=" + ettId);
			e.printStackTrace();
		}
		return deviceToken;
	}

	public User getUser(Long ettId) {
		String query = "select * from User where ettId=? and isVerified=1";
		User user = (User) jdbcTemplate.queryForObject(query, new Object[]{ettId}, new UserRowMapper());
		return user;
	}

	public void getCompetitorApps(HashMap<String, Long> competitorApps2, List<Long> competitorAppPointList2, List<String> competitorAppList2) {
			String query = "select * from CompetitorApps";
			SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(query);
			while(sqlRowSet.next()) {
				String appKey=sqlRowSet.getString("appKey");
				Long point = sqlRowSet.getLong("point");
				competitorApps2.put(appKey, point);
				competitorAppPointList2.add(point);
				competitorAppList2.add(appKey);
			}		
	}

	public List<String> getcCompetitorApps(long ettId) {
		List<String> cAppKeys = jdbcTemplate.queryForList("select appKey from UserCompetitorApps where ettId=?", new Object[]{ettId}, String.class);
		return cAppKeys;
	}


	public void insertUserCompApps(final long ettId, final List<String> cInstalledApps) {
		String qr="insert into UserCompetitorApps (ettId,installedTime,appKey,id) values (?,now(),?,?)";
		jdbcTemplate.batchUpdate(qr, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, ettId);
				ps.setString(2, cInstalledApps.get(i));
				ps.setString(3, ettId +"_"+ cInstalledApps.get(i));
			}
			@Override
			public int getBatchSize() {
				return cInstalledApps.size();
			}
		});
	}


	public void updateUserCompApps(long ettId, List<String> cUserCompApps) {
		List<String> ids = new ArrayList<>(cUserCompApps.size());
		for(String app : cUserCompApps){
			ids.add(ettId +"_"+ app);
		}
		Object[] param = ids.toArray();
		String qr="delete from UserCompetitorApps where id=?";
		jdbcTemplate.update(qr, param);
	}

	public void insertCompInstall(final long ettId, final List<String> appKey) {
		String qr = "insert ignore into UserCompetitorApps (ettId,installedTime,appKey,id) values(?,now(),?,?)";
		jdbcTemplate.batchUpdate(qr, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, ettId);
				ps.setString(2, appKey.get(i));
				ps.setString(3, ettId +"_"+ appKey.get(i));
			}
			@Override
			public int getBatchSize() {
				return appKey.size();
			}
		});
	}


	public int checkInstalledApps(long ettId, String appKey) {
		String query="select count(1) from InstalledApps where id='"+ettId+"_"+appKey+"'";
		int i = jdbcTemplate.queryForInt(query);
		return i;
	}


	public void insertInstalledApps(final long ettId, final List<String> appKey) {
		String installQuery = "insert ignore into InstalledApps (id,appKey,createdTs,ettId) values (?,?,now(),?)";
		jdbcTemplate.batchUpdate(installQuery, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, ettId +"_"+ appKey.get(i));
				ps.setString(2, appKey.get(i));
				ps.setLong(3, ettId);
			}
			@Override
			public int getBatchSize() {
				return appKey.size();
			}
		});
	}

	public void blackListHandle(long ettId, int blackListType) {
		int blackListCounter = 0;
		String query = "select blackListCounter from UserBlackList where ettId="+ettId;
		try{
			blackListCounter = jdbcTemplate.queryForInt(query);
		} catch(EmptyResultDataAccessException ignoreEx){}
		if(blackListCounter > 0){
			blackListCounter++;
			String query2 = "update UserBlackList set blackListCounter="+blackListCounter+" where ettId="+ettId;
			jdbcTemplate.update(query2);
			log.info("UNAUTHORIZED/[401] ettId="+ettId+" | counter increased to "+blackListCounter);
		}
		else{
			blackListCounter++;
			String query3 = "insert into UserBlackList (ettId, blackListCounter, type, updatedTime) values ("+ettId+","+blackListCounter+","+blackListType+",now())";
			jdbcTemplate.execute(query3);
			log.info("UNAUTHORIZED/[401] ettId="+ettId+" | inserted");
		}
	}


	public void updateOfferToCheck(Vector<String> offerList, ConcurrentHashMap<String, OfferDetails> offerDetails, int minOfferAmount, String offerIdsToIgnore) {
		String query = "select appKey,offerId from OfferDetails where status=1 and offerAmount>="+minOfferAmount+" and offerId NOT IN("+offerIdsToIgnore+")";
		SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(query);
		OfferDetails details;
		offerDetails.clear();
		offerList.clear();
		while(sqlRowSet.next()) {
			details = new OfferDetails();
			details.setAppKey(sqlRowSet.getString("appKey"));
			details.setOfferId(sqlRowSet.getLong("offerId"));
			offerList.add(sqlRowSet.getString("appKey"));
			offerDetails.put(sqlRowSet.getString("appKey"), details);
		}
	}

	public List<UserPushInfo> getUserPushList(long ettId) {
		String query = "select id,ettId,offerId,createdTime from UserPushInfo where ettId=? order by createdTime DESC";
		List<UserPushInfo> pushInfos = new ArrayList<>();
		SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(query, new Object[]{ettId});
		UserPushInfo pushInfo;
		while(sqlRowSet.next()) {
			pushInfo = new UserPushInfo();
			pushInfo.setId(sqlRowSet.getLong("id"));
			pushInfo.setEttId(sqlRowSet.getLong("ettId"));
			pushInfo.setOfferId(sqlRowSet.getLong("offerId"));
			pushInfo.setCreatedTime(sqlRowSet.getDate("createdTime"));
			pushInfos.add(pushInfo);
		}
		return pushInfos;
	}

	public void addInUserPushInfo(final long ettId, final Long offerId) {
		String query="insert into UserPushInfo (id,ettId,offerId,createdTime) values (0,?,?,now())";
		jdbcTemplate.update(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, ettId);
				ps.setLong(2, offerId);
			}
		});
	}

	public boolean checkInEdr(long ettId, String cApp) {
		String query="select count(1) from Edr where id=?";
		int count = jdbcTemplate.queryForInt(query, new Object[]{ettId +"_"+ cApp});
		if(count>0)
			return true;
		return false;
	}

 }