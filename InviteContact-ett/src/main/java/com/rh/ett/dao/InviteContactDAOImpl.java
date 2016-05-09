package com.rh.ett.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


public class InviteContactDAOImpl implements InviteContactDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	private PlatformTransactionManager transactionManager;
	private static Log log = LogFactory.getLog(InviteContactDAOImpl.class);
	
	@Override
	public void setDataSource(DataSource ds) {
		// TODO Auto-generated method stub
		this.dataSource=ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	@Override
	public void otpRequstInsert(final long msisdn, long ettId) {
		// TODO Auto-generated method stub
		
		
		//log.info("connection Object"+DataSourceUtils.getConnection(dataSource));
		
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
		
		
		try {
			String query="insert ignore into ETTMsisdn (msisdn) values (?)";
			jdbcTemplateObject.update(query, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setLong(1, msisdn);
				}
			});
			transactionManager.commit(transactionStatus);
		}catch(DataAccessException dataAccessException) {
			 System.out.println("Error in creating record, rolling back");
			 log.error("error in processing otpRequstInsert ettId:"+ettId+", msisdn:"+msisdn);
	         transactionManager.rollback(transactionStatus);
	         throw dataAccessException;
		}
	}

	@Override
	public void deleteInviteContact(long ettId) {
		// TODO Auto-generated method stub
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
		try {
			jdbcTemplateObject.update("delete from InviteContactMsisdn where ettIdAparty=?", new Object[]{ettId});
			transactionManager.commit(transactionStatus);
		}catch(DataAccessException dataAccessException) {
			 log.error("error in processing deleteInviteContact ettId:"+ettId);
	         transactionManager.rollback(transactionStatus);
	         throw dataAccessException;
		}

	}

	@Override
	public void updateStatusInviteContact(long msisdn) {
		// TODO Auto-generated method stub
		
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
		try {
			jdbcTemplateObject.update("update  InviteContactMsisdn set status=1 where msisdn=?", new Object[]{msisdn});
			transactionManager.commit(transactionStatus);
		}catch(DataAccessException dataAccessException) {
			 log.error("error in processing updateStatusInviteContact ettId:"+msisdn);
	         transactionManager.rollback(transactionStatus);
	         throw dataAccessException;
		}

	}
	@Override
	public List<String> listMsisdn(String msisdnData) {
		// TODO Auto-generated method stub
		String query = "select msisdn from ETTMsisdn where msisdn in("+msisdnData.replaceAll(" ", "")+")";
		List<String> allreadyMsisdnList = (List<String>)jdbcTemplateObject.queryForList(query, String.class);
		return allreadyMsisdnList;
	}

	@Override
	public int getInviteAllreadyContact(Long ettId) {
		 String query = "select count(1) from InviteContactMsisdn where ettIdAparty=?";
		 int count = jdbcTemplateObject.queryForInt(query, new Object[]{ettId});
		 return count;
	 }
	@Override
	public void insertInviteContactMsisdn(final long ettId, final List<String> msisdnList, final List<String> allreadyMsisdnList) {
		// TODO Auto-generated method stub
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
		try {
			String qr = "insert into InviteContactMsisdn (id,ettIdAparty,msisdn,status) values(0,?,?,?)";
			jdbcTemplateObject.batchUpdate(qr, new BatchPreparedStatementSetter() {
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
			transactionManager.commit(transactionStatus);
		}catch(DataAccessException dataAccessException) {
			 log.error("error in processing insertInviteContactMsisdn ettId:"+ettId);
	         transactionManager.rollback(transactionStatus);
	         throw dataAccessException;
		}


	}

}
