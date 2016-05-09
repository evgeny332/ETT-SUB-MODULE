package com.rh.persistence.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class UserRowMapper  implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setEttId(rs.getLong("ettId"));
		user.setDeviceId(rs.getString("deviceId"));
		user.setAppVersion(rs.getString("appVersion"));
		user.setMsisdn(rs.getString("msisdn"));		
		return user;
	}
}