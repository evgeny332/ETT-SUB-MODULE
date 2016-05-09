package com.rh.persistence.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;


public class RedeemAmountConfigRowMapper  implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		RedeemAmountConfig redeemAmountConfig = new RedeemAmountConfig();
		redeemAmountConfig.setId(rs.getLong("id"));
		redeemAmountConfig.setAmount(rs.getString("amount"));
		redeemAmountConfig.setFee(rs.getString("fee"));
		redeemAmountConfig.setOperator(rs.getString("operator"));
		redeemAmountConfig.setType(rs.getString("type"));
		redeemAmountConfig.setStatus(rs.getBoolean("status"));
		return redeemAmountConfig;
	}

}