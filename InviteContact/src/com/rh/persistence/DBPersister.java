package com.rh.persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.annotation.Transactional;

import com.rh.persistence.domain.DeviceToken;
import com.rh.persistence.domain.DeviceTokenRowMapper;
import com.rh.persistence.domain.User;
import com.rh.persistence.domain.InviteContactMsisdn;;


/**
 * Spring's helper for working with JDBC.
 */
public class DBPersister {
	private JdbcTemplate jdbcTemplate;
	private static Log log = LogFactory.getLog(DBPersister.class);

	public DBPersister(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		try {
			//DataSourceUtils.getConnection(jdbcTemplate.getDataSource()).setAutoCommit(false);
		} catch (CannotGetJdbcConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	 public void batchInsert(List<String> queries) { 
		 String[] query = queries.toArray(new String[queries.size()]);
		 jdbcTemplate.batchUpdate(query);
	 }
	
	 
	@Transactional
	 public void otpRequst(final Long msisdn,final Long ettId) {
		try {
			try {
				try {
					int size = getInviteAllreadyContact(ettId);
					//log.info("Connection | "+DataSourceUtils.getConnection(jdbcTemplate.getDataSource()));
					if(size>0){
						long time1= System.currentTimeMillis();
						String queryDel = "delete from InviteContactMsisdn where ettIdAparty="+ettId;
						jdbcTemplate.update("delete from InviteContactMsisdn where ettIdAparty=?", new Object[]{ettId});
						 //jdbcTemplate.getDataSource().getConnection().commit();
						 //DataSourceUtils.getConnection(jdbcTemplate.getDataSource()).commit();
						log.info("queryDelete|"+queryDel+" ,time|"+(System.currentTimeMillis()-time1));
					}
				}catch(Exception exCon){
					log.error("Error in Delete|"+exCon);
					exCon.printStackTrace();
				}
	
				long time1=System.currentTimeMillis();
				String query="insert into ETTMsisdn (msisdn) values (?)";
				jdbcTemplate.update(query, new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setLong(1, msisdn);
					}
				});
				 //jdbcTemplate.getDataSource().getConnection().commit();
				//DataSourceUtils.getConnection(jdbcTemplate.getDataSource()).commit();
				 log.info("query|"+query+" ,time|"+(System.currentTimeMillis()-time1));
			}catch(Exception ex) {
				//log.info("primary key error");
				return;
			}
			long time1 = System.currentTimeMillis();
			String query="update  InviteContactMsisdn set status=1 where msisdn=?";
			jdbcTemplate.update(query, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setLong(1, msisdn);
				}
			});
			 //jdbcTemplate.getDataSource().getConnection().commit();
			//DataSourceUtils.getConnection(jdbcTemplate.getDataSource()).commit();
			log.info("queryDelete|"+query+" ,time|"+(System.currentTimeMillis()-time1));
		}catch(Exception ex) {
			log.info("outter exception in the otpRequst |"+ex );
			ex.printStackTrace();
		}
	}
	
	@Transactional
	public void ContactFilter(String msisdnData,Long ettId) {
		try{
			try {
				int size = getInviteAllreadyContact(ettId);
				if(size>0){
					long time1 = System.currentTimeMillis();
					String queryDel = "delete from InviteContactMsisdn where ettIdAparty="+ettId;
					 jdbcTemplate.update("delete from InviteContactMsisdn where ettIdAparty=?", new Object[]{ettId});
					 //jdbcTemplate.getDataSource().getConnection().commit(); 
					 //DataSourceUtils.getConnection(jdbcTemplate.getDataSource()).commit();
					log.info("queryDelete|"+queryDel+" ,time|"+(System.currentTimeMillis()-time1));
				}
			}catch(Exception exCon){
				log.error("Error in Delete|"+exCon);
				exCon.printStackTrace();
			}
				List<String> allreadyMsisdnList = new ArrayList<String>();
				List<String> msisdnList = Arrays.asList((msisdnData.replaceAll(" ", "").split(",")));
				String msisdnList1[] = (msisdnData.replaceAll(" ", "").split(","));
			try{
				long time1 = System.currentTimeMillis();
				String query = "select msisdn from ETTMsisdn where msisdn in("+msisdnData.replaceAll(" ", "")+")";
				//String query = "select msisdn from ETTMsisdn where msisdn=?";
				//allreadyMsisdnList = (List<String>)jdbcTemplate.queryForList(query,new Object[]{msisdnList1}, String.class);
				allreadyMsisdnList = (List<String>)jdbcTemplate.queryForList(query, String.class);
				//jdbcTemplate.getDataSource().getConnection().commit();
				//DataSourceUtils.getConnection(jdbcTemplate.getDataSource()).commit();
				log.info("allreadyMsisdnList|"+allreadyMsisdnList+" ,time|"+(System.currentTimeMillis()-time1));
			} catch(EmptyResultDataAccessException ignoreEx){ 
				
			}//return;}
			long time1 = System.currentTimeMillis();
			insertInviteContactMsisddn1(ettId,msisdnList,allreadyMsisdnList);
			//insertInviteContactMsisddn(ettId,msisdnList,allreadyMsisdnList);
			log.info("[time in insertInviteContactMsisddn] ettId ["+ettId+"] time["+(System.currentTimeMillis()-time1)+"]");
		}catch(Exception ex) {
			System.out.println("error:"+ex);
			ex.printStackTrace();
		}
	}
	
	//@Transactional
	public void insertInviteContactMsisddn(final Long ettId,final List<String> msisdnList, final List<String> allreadyMsisdnList) throws CannotGetJdbcConnectionException, SQLException {
		String qr = "insert into InviteContactMsisdn (id,ettIdAparty,msisdn,status) values(0,?,?,?)";
		jdbcTemplate.batchUpdate(qr, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, ettId);
				ps.setString(2, msisdnList.get(i));
				ps.setBoolean(3, allreadyMsisdnList.contains(msisdnList.get(i)));
			}
			@Override
			public int getBatchSize() {
				return msisdnList.size();
			}
		});
		 //jdbcTemplate.getDataSource().getConnection().commit();
		//DataSourceUtils.getConnection(jdbcTemplate.getDataSource()).commit();
	}

	
	public void insertInviteContactMsisddn1(final Long ettId,final List<String> msisdnList, final List<String> allreadyMsisdnList) {
		try {
				PreparedStatement preparedStatement = DBAccesss.getPreparedStatement();
				for(String  msisdn : msisdnList) {
					preparedStatement.setLong(1, ettId);
					preparedStatement.setLong(2, Long.parseLong(msisdn));
					preparedStatement.setBoolean(3, allreadyMsisdnList.contains(msisdn));
					preparedStatement.addBatch();
				}
				preparedStatement.executeBatch();
				DBAccesss.con.commit();
		}catch(Exception ex){
			log.info("[Error in insertInviteContactMsisddn1]:"+ex);
			ex.printStackTrace();
		}
	}
	
	//@Transactional
	public int getInviteAllreadyContact(Long ettId) {
		 String query = "select count(1) from InviteContactMsisdn where ettIdAparty=?";
		 int count = jdbcTemplate.queryForInt(query, new Object[]{ettId});
		 return count;
	 }
}