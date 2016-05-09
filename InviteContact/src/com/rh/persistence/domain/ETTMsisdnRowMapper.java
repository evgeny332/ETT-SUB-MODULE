package com.rh.persistence.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class ETTMsisdnRowMapper  implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		ETTMsisdn ettMsisdn = new ETTMsisdn();
		ettMsisdn.setMsisdn(rs.getLong("msisdn"));
		return ettMsisdn;
	}
}