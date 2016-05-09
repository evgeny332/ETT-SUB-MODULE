package com.rh.persistence.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserAccountSummaryRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserAccountSummary userAccountSummary=new UserAccountSummary();
		userAccountSummary.setAmount(rs.getFloat("amount"));
		userAccountSummary.setEttId(rs.getLong("ettId"));
		userAccountSummary.setOfferName(rs.getString("offerName"));
		userAccountSummary.setRemarks(rs.getString("remarks"));
		return userAccountSummary;
	}

}
