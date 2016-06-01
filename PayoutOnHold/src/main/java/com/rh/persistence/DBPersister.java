package com.rh.persistence;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.rh.persistence.entity.PayoutHoldData;

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

	public void getList(List<PayoutHoldData> pushList, String dateNow) {
		String query = "select * from PayoutHoldData where payoutTime<='"+dateNow+"' and status=0 limit 500";
		log.info(("Select Query : "+query));
		List<Map<String,Object>> listRow = jdbcTemplate.queryForList(query);
		for(Map<String,Object> cRow : listRow){
			PayoutHoldData payoutHoldData = new PayoutHoldData();
			payoutHoldData.setId((Long) cRow.get("id"));
			payoutHoldData.setEventDetailsOnClickId((Long) cRow.get("eventDetailsOnClickId"));
			payoutHoldData.setCreatedTime((Date)cRow.get("createdTime"));
			payoutHoldData.setOfferDetailsOnClickId((Long) cRow.get("offerDetailsOnClickId"));
			payoutHoldData.setPayoutTime((Date)cRow.get("payoutTime"));
			payoutHoldData.setStatus((Integer)cRow.get("status"));
			pushList.add(payoutHoldData);
		}
		
	}

	public int updateId(Long ids,int status) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);
		parameters.addValue("status", status);
		int no = namedParameterJdbcTemplate.update("update  PayoutHoldData set status=:status where id IN (:ids)", parameters);
		//log.info(no+" No of Rows deleted");
		return no;
		//log.info("id updated to 1 -"+ids);
	}
}
