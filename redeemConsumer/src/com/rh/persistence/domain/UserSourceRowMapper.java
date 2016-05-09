package com.rh.persistence.domain;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserSourceRowMapper implements RowMapper{

		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserSource usersource = new UserSource();
			usersource.setEttId(rs.getLong("ettId"));
			usersource.setUtmMedium(rs.getString("utmMedium"));
			usersource.setUtmSource(rs.getLong("utmSource"));
			return usersource;
		}
	

}
