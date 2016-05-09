package com.rh.persistence.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class PopUpSheduledRowMapper
  implements RowMapper
{
  public Object mapRow(ResultSet paramResultSet, int paramInt)
    throws SQLException
  {
    PopUpSheduled localPopUpSheduled = new PopUpSheduled();
    localPopUpSheduled.setEttId(Long.valueOf(paramResultSet.getLong("ettId")));
    localPopUpSheduled.setId(Long.valueOf(paramResultSet.getLong("id")));
    localPopUpSheduled.setPopUpTime(paramResultSet.getDate("popUpTime"));
    localPopUpSheduled.setStatus(Boolean.valueOf(paramResultSet.getBoolean("status")));
    return localPopUpSheduled;
  }
}