package com.rh.persistence;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.rh.persistence.domain.DeviceToken;
import com.rh.persistence.domain.DeviceTokenRowMapper;
import com.rh.persistence.domain.SchedulePush;

public class DBPersister {

	/**
	 * @author Ankit Singh
	 */
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private static Log log = LogFactory.getLog(DBPersister.class);

	public DBPersister(JdbcTemplate jdbcTemplate,NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	 public void batchInsert(List<String> queries) { 
		 String[] query = queries.toArray(new String[queries.size()]);
		 jdbcTemplate.batchUpdate(query);
	 }

	public void getList(List<SchedulePush> pushList, String dateNow) {
		String query = "select * from SchedulePush where pushTime<='"+dateNow+"'";
		log.info(("Select Query : "+query));
		List<Map<String,Object>> listRow = jdbcTemplate.queryForList(query);
		for(Map<String,Object> cRow : listRow){
			SchedulePush schedulePush = new SchedulePush();
			schedulePush.setId((Long) cRow.get("id"));
			schedulePush.setEttId((Long) cRow.get("ettId"));
			schedulePush.setMessage((String) cRow.get("message"));
			schedulePush.setType((String) cRow.get("type"));
			pushList.add(schedulePush);
		}
		
	}

	public DeviceToken getDeviceToken(Long ettId) {
		DeviceToken deviceToken = null;
		try{
		String query = "select deviceToken from DeviceToken where ettId="+ettId;
		deviceToken = (DeviceToken) jdbcTemplate.queryForObject(query, new DeviceTokenRowMapper());
		}catch(Exception e){
			log.error("error in getting deviceToken"+ettId);
			//e.printStackTrace();
		}
		return deviceToken;
	}

	public void deletePush(List<Long> ids) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);
		int no = namedParameterJdbcTemplate.update("delete from SchedulePush where id IN (:ids)", parameters);
		
		//int no = jdbcTemplate.update("delete from SchedulePush where id IN ?", ids, Types.BIGINT);
		
		log.info(no+" No of Rows deleted");
		
		//jdbcTemplate.query("delete from SchedulePush where id IN (:ids)",parameters);
		/*String query = "delete from SchedulePush where pushTime<='"+id+"'";
		log.info("delete query : "+query);
		jdbcTemplate.execute(query);*/
	}
	 
	 
}
