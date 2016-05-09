package com.rh.persistence;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.rh.persistence.domain.DeviceToken;
import com.rh.persistence.domain.DeviceTokenRowMapper;
import com.rh.persistence.domain.RedeemValidate;
import com.rh.persistence.domain.RedeemValidateRowMapper;
import com.rh.persistence.domain.TempOtpRegId;
import com.rh.persistence.domain.TempOtpRegIdRowMapper;
import com.rh.persistence.domain.User;
import com.rh.persistence.domain.UserSource;
import com.rh.persistence.domain.UserAccount;
import com.rh.persistence.domain.UserAccountRowMapper;
import com.rh.persistence.domain.UserAccountSummary;
import com.rh.persistence.domain.UserRedeem;
import com.rh.persistence.domain.UserRedeemRowMapper;
import com.rh.persistence.domain.UserRowMapper;
import com.rh.persistence.domain.UserSourceRowMapper;

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
	
	 public User getUser(Long ettId) {
		//String query = "select * from User where ettId="+ettId+" and isVerified=1";
		String query = "select * from User where ettId="+ettId;
		User user =  (User) jdbcTemplate.queryForObject(query, new UserRowMapper());
		return user;
	}
	public void setUserFlage(Long ettId) {
		String query = "update User set flage=1 where ettId=?";
		jdbcTemplate.update(query, new Object[]{ettId});
		//jdbcTemplate.update(query);
		//log.info("query:"+query);
	}
	
	
	public void setDeviceTokenFlage(long ettId) {
		String query = "update DeviceToken set active=0,updatedTime=now() where ettId=?";
		//jdbcTemplate.update(query);
		jdbcTemplate.update(query, new Object[]{ettId});
		//log.info("query:"+query);
	}

	public void deleteUser(long ettId) {
		String query = "delete from InviteContactMsisdn where ettIdAparty=?";
		jdbcTemplate.update(query, new Object[]{ettId});
		//jdbcTemplate.update(query);
		//log.info("query:"+query.replace(arg0, arg1));
		//log.info("query:"+query);
	}
	
	public void getTempOtpRegId(Long ettId) {
		String query = "select * from TempOtpRegId where ettId="+ettId +" and status=1";
		TempOtpRegId tempOtpRegId = null;
		try{
			tempOtpRegId =  (TempOtpRegId) jdbcTemplate.queryForObject(query, new TempOtpRegIdRowMapper());
		} catch(EmptyResultDataAccessException ignoreEx){}
		if(tempOtpRegId==null) {
			query = "insert into TempOtpRegId (ettId,createdTime,status) values("+ettId+",now(),1)";
			jdbcTemplate.update(query);
		}
		
	}
}