package com.rh.persistence.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PendingRedeemsRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		PendingRedeems pr = new PendingRedeems();
		pr.setId(rs.getLong("id"));
		pr.setTrans_id(rs.getString("trans_id"));;
		pr.setVender(rs.getString("vender"));
		pr.setStatus("status");
		pr.setCount(rs.getInt("count"));
		pr.setId(rs.getLong("id"));
		pr.setEttId(rs.getLong("ettId"));
		pr.setAmount(rs.getFloat("amount"));
		pr.setMsisdn(rs.getLong("msisdn"));
		pr.setCircle(rs.getString("circle"));
		pr.setOperator(rs.getString("operator"));
		pr.setType(rs.getString("type"));
		pr.setStatus(rs.getString("status"));
		pr.setCreatedTime(rs.getDate("createdTime"));
		pr.setRedeemType(rs.getString("redeemType"));
		pr.setVender(rs.getString("vender"));
		pr.setFee(rs.getFloat("fee"));
		pr.setPostBalance(rs.getFloat("postBalance"));
		
		return pr;
	}
	

}
