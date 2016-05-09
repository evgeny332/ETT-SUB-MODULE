package com.rh.persistence.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserAccountSummaryRowMapper implements RowMapper{

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserAccountSummary accountSummary = new UserAccountSummary();
		accountSummary.setId(rs.getLong("id"));
		accountSummary.setAmount(rs.getFloat("amount"));
		accountSummary.setCoin(rs.getInt("coint"));
		accountSummary.setCreatedTime(rs.getDate("createdTime"));
		accountSummary.setDeviceIdFlage(rs.getBoolean("deviceIdFlage"));
		accountSummary.setEttId(rs.getLong("ettId"));
		accountSummary.setOfferCat(rs.getString("offerCat"));
		accountSummary.setOfferId(rs.getLong("offerId"));
		accountSummary.setOfferName(rs.getString("offerName"));
		accountSummary.setRemarks(rs.getString("remarks"));
		accountSummary.setVendor(rs.getString("vendor"));
		
		return accountSummary;
	}

}
