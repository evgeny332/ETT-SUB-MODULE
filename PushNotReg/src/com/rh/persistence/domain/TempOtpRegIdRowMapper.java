package com.rh.persistence.domain;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class TempOtpRegIdRowMapper implements RowMapper{

		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserSource usersource = new UserSource();
			TempOtpRegId tempOtpRegId = new TempOtpRegId();
			tempOtpRegId.setEttId(rs.getLong("ettId"));
			tempOtpRegId.setCreatedTime(rs.getDate("createdTime"));
			tempOtpRegId.setStatus(rs.getInt("status"));
			return tempOtpRegId;
		}
	

}
