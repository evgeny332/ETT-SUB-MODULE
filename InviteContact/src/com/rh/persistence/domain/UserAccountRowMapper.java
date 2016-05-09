package com.rh.persistence.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;


public class UserAccountRowMapper  implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserAccount userAccount = new UserAccount();
		userAccount.setEttId(rs.getLong("ettId"));
		userAccount.setCurrentBalance(rs.getFloat("currentBalance"));
		return userAccount;
	}
}