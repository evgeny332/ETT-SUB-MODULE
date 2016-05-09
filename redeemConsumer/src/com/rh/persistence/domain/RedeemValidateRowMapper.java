package com.rh.persistence.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class RedeemValidateRowMapper  implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		RedeemValidate redeemvalidate = new RedeemValidate();
		redeemvalidate.setEttId(rs.getString("ettId"));
		redeemvalidate.setMsisdn(rs.getString("msisdn"));
		redeemvalidate.setResp(rs.getString("resp"));
		return redeemvalidate;
	}
}