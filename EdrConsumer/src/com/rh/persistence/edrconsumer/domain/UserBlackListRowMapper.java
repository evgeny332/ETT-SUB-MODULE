package com.rh.persistence.edrconsumer.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserBlackListRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserBlackList userBlackList = new UserBlackList();
		userBlackList.setEttId(rs.getLong("ettId"));
		userBlackList.setBlackListCounter(rs.getInt("blackListCounter"));
		return userBlackList;
	}

}
