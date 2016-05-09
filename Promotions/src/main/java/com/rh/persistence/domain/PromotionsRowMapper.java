package com.rh.persistence.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PromotionsRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Promotions promotions = new Promotions();
		promotions.setOfferId(rs.getLong("offerId"));
		promotions.setAppKey(rs.getString("appKey"));
		promotions.setMessage(rs.getString("message"));
		return promotions;
	}

	
}
