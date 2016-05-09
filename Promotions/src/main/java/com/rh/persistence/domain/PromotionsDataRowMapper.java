package com.rh.persistence.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PromotionsDataRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int RowNum) throws SQLException {
		PromotionsData pd = new PromotionsData();
		pd.setAppKey(rs.getString("appKey"));
		return pd;
	}

}
