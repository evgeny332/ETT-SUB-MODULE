package com.rh.persistence.edrconsumer.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;


public class UserRedeemRowMapper  implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserRedeem userRedeem = new UserRedeem();
		userRedeem.setId(rs.getLong("id"));
		userRedeem.setEttId(rs.getLong("ettId"));
		userRedeem.setAmount(rs.getFloat("amount"));
		userRedeem.setMsisdn(rs.getLong("msisdn"));
		userRedeem.setCircle(rs.getString("circle"));
		userRedeem.setOperator(rs.getString("operator"));
		userRedeem.setType(rs.getString("type"));
		userRedeem.setStatus(rs.getString("status"));
		userRedeem.setCreatedTime(rs.getDate("createdTime"));
		return userRedeem;
	}

}