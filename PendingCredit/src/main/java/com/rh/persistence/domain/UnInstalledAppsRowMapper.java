package com.rh.persistence.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UnInstalledAppsRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		UnInstalledApps unInstalledApps=new UnInstalledApps();
		unInstalledApps.setAppKey(rs.getString("appKey"));
		unInstalledApps.setCreatedTs(rs.getDate("createdTs"));
		unInstalledApps.setEttId(rs.getLong("ettId"));
		return unInstalledApps;
	}

}
