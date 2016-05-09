package com.rh.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.RowSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.rh.main.APIImpl;
import com.rh.persistence.domain.DeviceToken;
import com.rh.persistence.domain.DeviceTokenRowMapper;
import com.rh.persistence.domain.Promotions;
import com.rh.persistence.domain.PromotionsData;
import com.rh.persistence.domain.PromotionsDataRowMapper;

public class DBPersister {
	/**
	 * @author Ankit Singh
	 */
	private JdbcTemplate jdbcTemplate;
	private JdbcTemplate jdbcTemplateActive;
	static Properties configFile = new Properties();
	private static Log log = LogFactory.getLog(DBPersister.class);
	
	public DBPersister(JdbcTemplate jdbcTemplate,JdbcTemplate jdbcTemplateActive) {
		this.jdbcTemplate = jdbcTemplate;
		this.jdbcTemplateActive = jdbcTemplateActive;
		try {
			configFile.load(APIImpl.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void batchInsert(List<String> queries) { 
		 String[] query = queries.toArray(new String[queries.size()]);
		 jdbcTemplateActive.batchUpdate(query);
	 }

	public void getMessage(List<Promotions> promoList, List<String> appKeyList) {
		String query="select * from Promotions order by id ASC";
		List<Map<String,Object>> listRow = jdbcTemplateActive.queryForList(query);
		for(Map<String,Object> cRow : listRow){
			Promotions promotions = new Promotions();
			promotions.setOfferId((Long) cRow.get("offerId"));
			promotions.setAppKey((String) cRow.get("appKey"));
			promotions.setMessage((String) cRow.get("message"));
			promotions.setAmount((Integer) cRow.get("amount"));
			promoList.add(promotions);
			appKeyList.add((String) cRow.get("appKey"));
		}
	}

	public void getUser(List<Long> ids,String query) {
		String query2=query;
		//String query2="select ettId from PromotionsBase";
		List<Map<String,Object>> listRow = jdbcTemplate.queryForList(query2);
		for(Map<String,Object> cRow : listRow){
			long id = (long) cRow.get("ettId");
			ids.add(id);
		}
	}

	public void getInstalledApps(List<String> cAppKey, Long id) {
		String query = "select appKey from InstalledApps where ettId="+id;
		List<Map<String,Object>> listRow = jdbcTemplate.queryForList(query);
		for(Map<String,Object> cRow : listRow){
			String app = (String) cRow.get("appKey");
			cAppKey.add(app);
		}
	}

	public DeviceToken getDeviceToken(Long id) {
		DeviceToken deviceToken = null;
		try{
		//String query = "select d.deviceToken from DeviceToken d,PromotionsBase t where d.deviceId=t.deviceId and active=1 and t.ettId="+id;
		String query ="select deviceToken from DeviceToken where ettId="+id;
			deviceToken = (DeviceToken) jdbcTemplate.queryForObject(query, new DeviceTokenRowMapper());
		}catch(Exception e){
			log.error("error in getting deviceToken"+id);
			//e.printStackTrace();
		}
		return deviceToken;
	}

	public void dropTempTable() {
		String query="drop table TempPromotions";
		jdbcTemplate.execute(query);
		
	}

	public int transactionTrackerCheck(String id) {
		int count = 0;
		String query = "select count(1) from TransactionTracker where id='"+id+"'";
		count = jdbcTemplate.queryForInt(query);
		return count;
	}

	public List<String> getPromotedAppKey(long id) {
		List<String> promotedAppKey = new ArrayList<>(5);
		String query = "select appKey from PromotionsData where ettId="+id;
		List<Map<String,Object>> listRow = jdbcTemplate.queryForList(query);
		for(Map<String,Object> cRow : listRow){
			promotedAppKey.add((String) cRow.get("appKey"));
		}
		return promotedAppKey;
	}

	public void insertInPromotionsData(long id, List<String> promotionsDataAll) {
		List<String> queries = new ArrayList<>(promotionsDataAll.size());
		for(String promotionsData : promotionsDataAll){
			String query = "insert ignore into PromotionsData (id,ettId,appKey,pushTime) values ('"+id+"_"+promotionsData+"',"+id+",'"+promotionsData+"',now())";
			queries.add(query);
		}
		batchInsert(queries);
	}
	

}
