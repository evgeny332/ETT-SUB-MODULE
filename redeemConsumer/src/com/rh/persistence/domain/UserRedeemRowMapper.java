package com.rh.persistence.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserRedeemRowMapper
  implements RowMapper
{
  public Object mapRow(ResultSet paramResultSet, int paramInt)
    throws SQLException
  {
    UserRedeem localUserRedeem = new UserRedeem();
    localUserRedeem.setId(Long.valueOf(paramResultSet.getLong("id")));
    localUserRedeem.setEttId(Long.valueOf(paramResultSet.getLong("ettId")));
    localUserRedeem.setAmount(paramResultSet.getFloat("amount"));
    localUserRedeem.setMsisdn(Long.valueOf(paramResultSet.getLong("msisdn")));
    localUserRedeem.setCircle(paramResultSet.getString("circle"));
    localUserRedeem.setOperator(paramResultSet.getString("operator"));
    localUserRedeem.setType(paramResultSet.getString("type"));
    localUserRedeem.setStatus(paramResultSet.getString("status"));
    localUserRedeem.setCreatedTime(paramResultSet.getDate("createdTime"));
    if (paramResultSet.getInt("redeemType") == 0){
    	localUserRedeem.setRedeemType(UserRedeem.RedeemType.RECHARGE);
    }  
    else if (paramResultSet.getInt("redeemType") == 1){
    	localUserRedeem.setRedeemType(UserRedeem.RedeemType.LOAN);
    }
    else {
    	localUserRedeem.setRedeemType(UserRedeem.RedeemType.EGV);
    }	
    localUserRedeem.setVender(paramResultSet.getString("vender"));
    localUserRedeem.setFee(paramResultSet.getFloat("fee"));
    localUserRedeem.setPostBalance(paramResultSet.getFloat("postBalance"));
    return localUserRedeem;
  }
}