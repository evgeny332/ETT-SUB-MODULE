package com.rh.persistence.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DeviceTokenRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		DeviceToken deviceToken=new DeviceToken();
		deviceToken.setDeviceId(rs.getString("deviceId"));
		deviceToken.setDeviceToken(rs.getString("deviceToken"));
		
		return deviceToken;
	}
	
	

}
