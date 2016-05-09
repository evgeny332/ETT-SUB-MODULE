package com.rh.persistence.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PendingCreditsRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		PendingCredits pendingCredits=new PendingCredits();
		pendingCredits.setAmount(rs.getFloat("amount"));
		pendingCredits.setAppKey(rs.getString("appKey"));
		pendingCredits.setCreatedTime(rs.getDate("createdTime"));
		pendingCredits.setEttId(rs.getLong("ettId"));
		pendingCredits.setOfferId(rs.getLong("offerId"));
		pendingCredits.setIsCredited(rs.getBoolean("isCredited"));
		return pendingCredits;
	}

	
}
