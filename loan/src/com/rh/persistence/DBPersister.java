package com.rh.persistence;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.rh.persistence.domain.DeviceToken;
import com.rh.persistence.domain.DeviceTokenRowMapper;
import com.rh.persistence.domain.User;
import com.rh.persistence.domain.UserAccount;
import com.rh.persistence.domain.UserAccountRowMapper;
import com.rh.persistence.domain.UserAccountSummary;
import com.rh.persistence.domain.UserRedeem;
import com.rh.persistence.domain.UserRedeemRowMapper;
import com.rh.persistence.domain.UserRowMapper;

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
	

	public UserRedeem gerUserRedeem(String id){
		String query = "select * from UserRedeem where id="+id+" and status='PENDING' and redeemType=1";
		UserRedeem userRedeem = (UserRedeem) jdbcTemplate.queryForObject(query, new UserRedeemRowMapper());
		jdbcTemplate.execute("update UserRedeem set status='PROCESSING',updatedTime=now() where id="+id);
		return userRedeem;
	}
	
	public void updatePokktRequest(UserRedeem pokktRequestInfo) {
		
	}

	public UserAccount getUserAccount(Long ettId) {
		String query = "select * from UserAccount where ettId="+ettId;
		UserAccount userAccount = (UserAccount) jdbcTemplate.queryForObject(query, new UserAccountRowMapper());
		return userAccount;
	}
	
	public DeviceToken getDeviceId(String  deviceId) {
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

	public User getUser(Long ettId) {
		String query = "select * from User where ettId="+ettId+" and isVerified=1";
		User user =  (User) jdbcTemplate.queryForObject(query, new UserRowMapper());
		return user;
	}

	public void updateSuccessRedeem(User user,UserAccountSummary userAccountSummary, UserRedeem userRedeem) {
		String query1 = "update UserRedeem set status='SUCCESS',updatedTime=now() where id="+userRedeem.getId();
		String query2 = "insert into UserAccountSummary values(0,"+userAccountSummary.getAmount()+",now(),"+userAccountSummary.getEttId()+","+userAccountSummary.getOfferId()+",'"+userAccountSummary.getRemarks()+"','"+userAccountSummary.getOfferName()+"')";	
		List<String> queries = new ArrayList<>();
		queries.add(query1);
		queries.add(query2);
		batchInsert(queries);
	}

	public void updateFailRedeem(User user, UserRedeem userRedeem) {
		String query1 = "update UserAccount set currentBalance=currentBalance+"+userRedeem.getAmount()+" where ettId="+userRedeem.getEttId();
		String query2 = "update UserRedeem set status='FAILED',updatedTime=now() where id="+userRedeem.getId();
		List<String> queries = new ArrayList<>();
		queries.add(query1);
		queries.add(query2);
		batchInsert(queries);
	}


	


	public void setRedeemInvalid(UserRedeem userRedeem) {
		String q = "update UserRedeem set status='INVALID' where id="+userRedeem.getId();
		jdbcTemplate.update(q);
	}


	public void updateAirtelPending(UserRedeem userRedeem) {
		String q = "update UserRedeem set status='PENDING_AIRTEL' where id="+userRedeem.getId();
		jdbcTemplate.update(q);
	}


	public void updateFailRedeem(UserRedeem userRedeem) {
		String query2 = "update UserRedeem set status='WARNING',updatedTime=now() where id="+userRedeem.getId();
		List<String> queries = new ArrayList<>();
		queries.add(query2);
		batchInsert(queries);
	}
}