package com.rh.persistence.edrconsumer.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserServiceClassRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserServiceClass userServiceClass = new UserServiceClass();
		userServiceClass.setEttId(rs.getLong("ettId"));
		userServiceClass.setCompetitorAppCode(rs.getLong("competitorAppCode"));
		return userServiceClass;
	}

}
