package com.rh.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.rh.persistence.domain.DeviceToken;
import com.rh.persistence.domain.DeviceTokenRowMapper;
import com.rh.persistence.domain.GiftVoucher;
import com.rh.persistence.domain.PendingRedeems;
import com.rh.persistence.domain.PendingRedeemsRowMapper;
import com.rh.persistence.domain.PineLabs;
import com.rh.persistence.domain.PopUpSheduled;
import com.rh.persistence.domain.PopUpSheduledRowMapper;
import com.rh.persistence.domain.RedeemAmountConfig;
import com.rh.persistence.domain.RedeemAmountConfigRowMapper;
import com.rh.persistence.domain.RedeemValidate;
import com.rh.persistence.domain.RedeemValidateRowMapper;
import com.rh.persistence.domain.User;
import com.rh.persistence.domain.UserAccount;
import com.rh.persistence.domain.UserAccountRowMapper;
import com.rh.persistence.domain.UserAccountSummary;
import com.rh.persistence.domain.UserRedeem;
import com.rh.persistence.domain.UserRedeem.RedeemType;
import com.rh.persistence.domain.UserRedeemRowMapper;
import com.rh.persistence.domain.UserRowMapper;

/**
 * Spring's helper for working with JDBC.
 */
public class DBPersister {
	private JdbcTemplate jdbcTemplate;
	private static Log log = LogFactory.getLog(DBPersister.class);

	public DBPersister(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Map<String, String> getConfig() {
		String query = "select ckey,cvalue from RedeemConsumerConfig";
		ResultSetExtractor mapExtractor = new ResultSetExtractor() {
			   public Object extractData(ResultSet rs) throws SQLException {
			      Map<String, String> mapOfKeys = new HashMap<String, String>();
			      while (rs.next()) {
			        mapOfKeys.put(rs.getString("ckey"), rs.getString("cvalue"));
			      }
			      return mapOfKeys;
			   }
			};

			Map<String,String> map = (HashMap<String, String>) jdbcTemplate.query(query, mapExtractor);
		return map;
	}
	
	public void batchInsert(List<String> queries) {
		String[] query = queries.toArray(new String[queries.size()]);
		jdbcTemplate.batchUpdate(query);
	}

	public RedeemAmountConfig getRedeemAmountConfig(String type, String operator) {
		try {
			if (type.equalsIgnoreCase("PREPAID") || type.equalsIgnoreCase("POSTPAID") || type.equals("")) {
				operator = "All";
				type = "PREPAID";
			}else if(type.equalsIgnoreCase("EGV")){
				operator = "Flipkart";
				type= "ShoppingVoucher";
			}else if(type.equalsIgnoreCase("TVANDMUSIC")){
				operator = "NexGTV";
				type = "MobileTVandMusic";
			}
			
			String query = "select * from RedeemAmountConfig where UPPER(type)=UPPER('" + type + "') and UPPER(operator)=UPPER('" + operator + "') and status=1";
			RedeemAmountConfig redeemAmountConfig = (RedeemAmountConfig) jdbcTemplate.queryForObject(query, new RedeemAmountConfigRowMapper());
			return redeemAmountConfig;
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	public UserRedeem getUserRedeem(String id) {
		String query = "select * from UserRedeem where id=" + id + " and status='PENDING'";
		UserRedeem userRedeem = (UserRedeem) jdbcTemplate.queryForObject(query, new UserRedeemRowMapper());
		jdbcTemplate.execute("update UserRedeem set status='PROCESSING',updatedTime=now() where id=" + id);
		return userRedeem;
	}
	
	public List<UserRedeem> getTimeOuts(){
		String q = "select * from UserRedeem where updatedTime<date_sub(now(), INTERVAL 300 SECOND) and status='TIMEOUT' order by updatedTime limit 10";
		List<UserRedeem> list = new ArrayList<UserRedeem>();
		List<Map> rows = jdbcTemplate.queryForList(q);
		for (Map rs : rows) {
			UserRedeem redeem = new UserRedeem();
			redeem.setId((Long)(rs.get("id")));
			redeem.setEttId((Long)(rs.get("ettId")));
			redeem.setAmount((Float)rs.get("amount"));
			redeem.setMsisdn((Long)rs.get("msisdn"));
			redeem.setCircle((String)rs.get("circle"));
			redeem.setOperator((String)rs.get("operator"));
			if ((Integer)rs.get("redeemType") == 0){
		    	redeem.setRedeemType(UserRedeem.RedeemType.RECHARGE);
		    }  
		    else if ((Integer)rs.get("redeemType") == 1){
		    	redeem.setRedeemType(UserRedeem.RedeemType.LOAN);
		    }
		    else {
		    	redeem.setRedeemType(UserRedeem.RedeemType.EGV);
		    }
			redeem.setType((String)rs.get("type"));
			redeem.setStatus((String)rs.get("status"));
			redeem.setCreatedTime((Date)rs.get("createdTime"));
			redeem.setUpdatedTime((Date)rs.get("updatedTime"));
			redeem.setVender((String)rs.get("vender"));
			redeem.setFee((Float)rs.get("fee"));
			redeem.setPostBalance((Float)rs.get("postBalance"));
			redeem.setTrans_id_ett((String)rs.get("trans_id_ett"));

			list.add(redeem);
		}
		return list;
	}
	
	public long getIDUserActivityBooster(long ettId){
		String query = "select max(id) from UserActivityBooster where ettId="+ettId+" and validityStatus=0";
		long id = jdbcTemplate.queryForInt(query);
		return id;
	}
	
	public void updateUserActivityBooster(long id, int status){
		jdbcTemplate.execute("update UserActivityBooster set validityStatus="+status+" where id="+id);
	}
	
	public void updateThreashHoldSpecial(long id, int status){
		jdbcTemplate.execute("update ThreashHoldSpecial set status="+status+" where ettId="+id);
	}

	public void firstRedeemOffer(UserAccountSummary userAccountSummary){
		String query1 = "update UserAccount set currentBalance=currentBalance+"+(int)userAccountSummary.getAmount()+" where ettId="+userAccountSummary.getEttId();
		String query2 = "insert into UserAccountSummary (id,amount,createdTime,ettId,offerId,remarks,offerName,coin)values(0,"+userAccountSummary.getAmount()+",now(),"+userAccountSummary.getEttId()+","+userAccountSummary.getOfferId()+",'"+userAccountSummary.getRemarks()+"','"+userAccountSummary.getOfferName()+"',0)";	
		List<String> queries = new ArrayList<>();
		queries.add(query1);
		queries.add(query2);
		batchInsert(queries);
	}
	
	public Long getUserAccountSummaryEttId(String id){
		String query = "select ettId from UserAccountSummary where id="+id;
		Long ettId = jdbcTemplate.queryForLong(query);
		return ettId;
	}
	
	public UserAccount getUserAccount(Long ettId) {
		String query = "select * from UserAccount where ettId=" + ettId;
		UserAccount userAccount = (UserAccount) jdbcTemplate.queryForObject(query, new UserAccountRowMapper());
		return userAccount;
	}

	public DeviceToken getDeviceId(String deviceId, Long ettId) {
		DeviceToken deviceToken = null;
		try {
			String query = "select * from DeviceToken where ettId=" + ettId;
			deviceToken = (DeviceToken) jdbcTemplate.queryForObject(query, new DeviceTokenRowMapper());
		} catch (Exception e) {
			log.error("error in getting deviceToken" + deviceId);
			e.printStackTrace();
		}
		return deviceToken;
	}

	public String getValidResp(Long msisdn) {
		String resp = null;
		try {
			String query = "select * from RedeemValidate where msisdn='" + msisdn + "'";
			RedeemValidate redeem1 = (RedeemValidate) jdbcTemplate.queryForObject(query, new RedeemValidateRowMapper());
			if (redeem1 != null) {
				resp = redeem1.getResp();
			}
			log.info("data=" + resp);
		} catch (Exception e) {
			log.error("error in getting getValidResp" + resp);
			e.printStackTrace();
		}
		return resp;
	}

	public User getUser(Long ettId) {
		String query = "select * from User where ettId=" + ettId + " and isVerified=1";
		User user = (User) jdbcTemplate.queryForObject(query, new UserRowMapper());
		return user;
	}

	public PopUpSheduled getPopUpSheduled(Long ettId) {
		PopUpSheduled popUpSheduled = null;
		String query = "select * from PopUpSheduled where ettId=" + ettId + " and date(date_add(popUpTime,INTERVAL 19800 SECOND))>date(date_sub(date_add(now(),INTERVAL 19800 SECOND),INTERVAL 7 DAY)) limit 1";
		try {
			popUpSheduled = (PopUpSheduled) jdbcTemplate.queryForObject(query, new PopUpSheduledRowMapper());
		} catch (EmptyResultDataAccessException ex) {
			// isPopUp=true;
		}
		return popUpSheduled;
	}

	public void setPopuSheduled(Long ettId) {
		String query = "delete from PopUpSheduled where ettId=" + ettId;
		jdbcTemplate.update(query);
		query = "insert into PopUpSheduled(ettId,popUpTime,status) values(" + ettId + ",now(),1)";
		jdbcTemplate.update(query);
	}
	
	public int getTodayAmount(Long ettId) {
		String query = "select ifnull(sum(amount),0) from UserRedeem where ettId=" + ettId + " and date(DATE_ADD(createdTime,INTERVAL 19800 SECOND))=date(DATE_ADD(now(),INTERVAL 19800 SECOND)) and status='SUCCESS'";
		int amount = jdbcTemplate.queryForInt(query);
		query = "select ifnull(sum(amount),0) from UserAccountSummary where ettId=" + ettId + " and offerId=8884 and date(DATE_ADD(createdTime,INTERVAL 19800 SECOND))=date(DATE_ADD(now(),INTERVAL 19800 SECOND))";
		amount = amount + (-(jdbcTemplate.queryForInt(query)));
		return amount;
	}
	
	public int getMonthlyEGV(Long ettId){
		String query = "select ifnull(sum(amount),0) from UserRedeem where ettId=" + ettId + " and month(createdTime)=month(now()) and status='SUCCESS' and type='EGV'";
		int amount = jdbcTemplate.queryForInt(query);
		return amount;
	}
	
	public int getMonthlyAmount(Long ettId){
		String query = "select ifnull(sum(amount),0) from UserRedeem where ettId=" + ettId + " and month(createdTime)=month(now()) and status='SUCCESS'";
		int amount = jdbcTemplate.queryForInt(query);
		return amount;
	}
	
	/*********************** 27-11-2014 (Invite money from same device with different sim) ***********/
	/*
	 * public UserSource getUserSource(Long ettId) {
	 * String query = "select * from UserSource where ettId="+ettId;
	 * UserSource usersource = (UserSource) jdbcTemplate.queryForObject(query,
	 * new UserSourceRowMapper());
	 * return usersource;
	 * }
	 */
	/*************************************************************************************************/

	public void updateUserAccountFee(UserRedeem userRedeem) {
		String q = "update UserAccount set currentBalance=currentBalance-"+userRedeem.getFee()+" where ettId="+userRedeem.getEttId();
		jdbcTemplate.update(q);
	}

	public void setProcessFee(UserRedeem userRedeem) {
		String q = "update UserRedeem set fee="+userRedeem.getFee()+",postBalance="+(userRedeem.getPostBalance()-userRedeem.getFee())+" where id="+userRedeem.getId();
		//String q = "update UserRedeem set status='INVALID' where id="+userRedeem.getId();
		jdbcTemplate.update(q);	
	}

	public void updateBarredRedeem(UserRedeem userRedeem) {
		String query1 = "update UserAccount set currentBalance=currentBalance+"+(userRedeem.getAmount()+userRedeem.getFee())+" where ettId="+userRedeem.getEttId();
		String query2 = "update UserRedeem set status='BARRED',updatedTime=now() where id="+userRedeem.getId();
		//String query2 = "update UserRedeem set status='FAILED',updatedTime=now() where id="+userRedeem.getId();
		List<String> queries = new ArrayList<>();
		queries.add(query1);
		queries.add(query2);
		batchInsert(queries);
	}
	
	public void increaseRedeemCount(Long ettId) {
		String query = "update User set redeemCount=redeemCount+1 where ettId="+ettId;
		jdbcTemplate.update(query);
	}

	public void updateRedeemStatus(UserRedeem userRedeem){
		String query = "update UserRedeem set status='"+userRedeem.getStatus()+"',updatedTime=now(),vender='"+userRedeem.getVender()+"',trans_id_ett='"+userRedeem.getTrans_id_ett()+"' where id="+userRedeem.getId();
		jdbcTemplate.update(query);
	}
	
	public void updateFailRedeem(User user, UserRedeem userRedeem) {
		String query1 = "update UserAccount set currentBalance=currentBalance+"+(userRedeem.getAmount()+userRedeem.getFee())+" where ettId="+userRedeem.getEttId();
		String query2 = "update UserRedeem set status='FAILED',updatedTime=now(),vender='"+userRedeem.getVender()+"',trans_id_ett='"+userRedeem.getTrans_id_ett()+"' where id="+userRedeem.getId();
		
		//String query2 = "update UserRedeem set status='FAILED',updatedTime=now() where id="+userRedeem.getId();
		List<String> queries = new ArrayList<>();
		queries.add(query1);
		queries.add(query2);
//		if(user.getRedeemCount()>0) {
//			String query3 = "update User set redeemCount=redeemCount-1 where ettId="+user.getEttId();
//			queries.add(query3);
//		}
		batchInsert(queries);
	}

	public void updateSuccessRedeem(User user, UserAccountSummary userAccountSummary, UserRedeem userRedeem) {
		String query1 = "update UserRedeem set status='SUCCESS',updatedTime=now(),vender='"+userRedeem.getVender()+"',trans_id='"+userRedeem.getTrans_id()+"',trans_id_ett='"+userRedeem.getTrans_id_ett()+"' where id="+userRedeem.getId();
		String query6 = "update User set redeemCount=redeemCount+1 where ettId="+user.getEttId();
		String query2 = "insert into UserAccountSummary (id,amount,createdTime,ettId,offerId,remarks,offerName,coin)values(0,"+userAccountSummary.getAmount()+",now(),"+userAccountSummary.getEttId()+","+userAccountSummary.getOfferId()+",'"+userAccountSummary.getRemarks()+"','"+userAccountSummary.getOfferName()+"',0)";
		String query4 = "insert into UserEarningView (UASId,amount,createdTime,ettId,offerName) values(0,"+(int)userAccountSummary.getAmount()+",now(),"+userAccountSummary.getEttId()+",'"+userAccountSummary.getOfferName()+"')";
		List<String> queries = new ArrayList<>();
		String query3 = "";
		String query5 = "";
		if(userRedeem.getFee()>0) {
			if(userRedeem.getRedeemType().equals(RedeemType.LOAN)) {
				query3 = "insert into UserAccountSummary (id,amount,createdTime,ettId,offerId,remarks,offerName,coin)values(0,-"+userRedeem.getFee()+",now(),"+userRedeem.getEttId()+","+userAccountSummary.getOfferId()+",'convenience charge','convenience fee on loan',0)";
				query5 = "insert into UserEarningView (UASId,amount,createdTime,ettId,offerName) values(0,-"+userRedeem.getFee()+",now(),"+userRedeem.getEttId()+",'convenience fee on loan')";
			}else if(userRedeem.getType().equalsIgnoreCase("EGV")){
				query3 = "insert into UserAccountSummary (id,amount,createdTime,ettId,offerId,remarks,offerName,coin)values(0,-"+userRedeem.getFee()+",now(),"+userRedeem.getEttId()+","+userAccountSummary.getOfferId()+",'convenience charge on FGV','convenience fee',0)";
				query5 = "insert into UserEarningView (UASId,amount,createdTime,ettId,offerName) values(0,-"+userRedeem.getFee()+",now(),"+userRedeem.getEttId()+",'convenience fee')";
			}else if(userRedeem.getType().equalsIgnoreCase("TVandMusic")){
				query3 = "insert into UserAccountSummary (id,amount,createdTime,ettId,offerId,remarks,offerName,coin)values(0,-"+userRedeem.getFee()+",now(),"+userRedeem.getEttId()+","+userAccountSummary.getOfferId()+",'convenience charge on TV&M','convenience fee',0)";
				query5 = "insert into UserEarningView (UASId,amount,createdTime,ettId,offerName) values(0,-"+userRedeem.getFee()+",now(),"+userRedeem.getEttId()+",'convenience fee')";
			}else if(userRedeem.getRedeemType().equals(RedeemType.BROWSEPLAN)){
				query3 = "insert into UserAccountSummary (id,amount,createdTime,ettId,offerId,remarks,offerName,coin)values(0,-"+userRedeem.getFee()+",now(),"+userRedeem.getEttId()+","+userAccountSummary.getOfferId()+",'convenience charge on browseplans','convenience fee',0)";
				query5 = "insert into UserEarningView (UASId,amount,createdTime,ettId,offerName) values(0,-"+userRedeem.getFee()+",now(),"+userRedeem.getEttId()+",'convenience fee')";
			}else {
				query3 = "insert into UserAccountSummary (id,amount,createdTime,ettId,offerId,remarks,offerName,coin)values(0,-"+userRedeem.getFee()+",now(),"+userRedeem.getEttId()+","+userAccountSummary.getOfferId()+",'convenience charge','convenience fee on redemption',0)";
				query5 = "insert into UserEarningView (UASId,amount,createdTime,ettId,offerName) values(0,-"+userRedeem.getFee()+",now(),"+userRedeem.getEttId()+",'convenience fee on redemption')";
			}
		}
		queries.add(query1);
		queries.add(query2);
		queries.add(query4);
		queries.add(query6);
		if(!query3.equals("")){
			queries.add(query3);
			queries.add(query5);
		}
		batchInsert(queries);	
	}
	
	public void setRedeemInvalid(UserRedeem userRedeem) {
		String q = "update UserRedeem set status='INVALID',vender='" + userRedeem.getVender() + "' where id=" + userRedeem.getId();
		// String q =
		// "update UserRedeem set status='INVALID' where id="+userRedeem.getId();
		jdbcTemplate.update(q);
	}
	
	public void updateAirtelPending(UserRedeem userRedeem) {
		String q = "update UserRedeem set status='PENDING_AIRTEL',vender='" + userRedeem.getVender() + "' where id=" + userRedeem.getId();
		// String q =
		// "update UserRedeem set status='PENDING_AIRTEL' where id="+userRedeem.getId();
		jdbcTemplate.update(q);
	}
	
	public void insertGiftVoucher(GiftVoucher gift){
		
		String query = "insert into GiftVoucher (ettId,amount,createdTime,expiryDate,status,medium,pin,code,transactionId,name,dispatchStatus)values("+gift.getEttId()+","+gift.getAmount()+",now(),'"+gift.getExpiryDate()+"','"+gift.getStatus()+"','"+gift.getMedium()+"','"+gift.getPin()+"','"+gift.getCode()+"','"+gift.getTransactionId()+"','"+gift.getName()+"','"+gift.getDispatchStatus()+"')";
		jdbcTemplate.update(query);
		
	}
	
	public boolean checkMsisdn(Long msisdn){
		
		String query="select count(1) from ETTMsisdn where msisdn="+msisdn+"";
		int count = jdbcTemplate.queryForInt(query);
		
		if(count==0){
			return false;
		}
		return true;
	}
	
	public PineLabs getPineLabs(long id) {
		
		String query = "select brandId,brandName,schemeId,schemeName,purchaseValue,faceValue from PineLabsDetails where id=? and status=1";
		PineLabs labs = (PineLabs) jdbcTemplate.queryForObject(query, new Object[] {id},new BeanPropertyRowMapper(PineLabs.class));
		return labs;
		
	}
	
	
	/*########################################################INDONESIA###################################################*/
	

	public void updateSuccessRedeem(User user, UserAccountSummary userAccountSummary, PendingRedeems pendingRedeems) {
		String query1 = "update UserRedeem set status='SUCCESS',updatedTime=now(),vender='" + pendingRedeems.getVender() + "',trans_id='" + pendingRedeems.getTrans_id() + "',trans_id_ett='" + pendingRedeems.getTrans_id_ett() + "' where id=" + pendingRedeems.getId();
		// String query1 =
		// "update UserRedeem set status='SUCCESS',updatedTime=now() where id="+userRedeem.getId();
		String query2 = "insert into UserAccountSummary (id,amount,createdTime,ettId,offerId,remarks,offerName,coin)values(0," + userAccountSummary.getAmount() + ",now()," + userAccountSummary.getEttId() + "," + userAccountSummary.getOfferId() + ",'" + userAccountSummary.getRemarks() + "','" + userAccountSummary.getOfferName() + "',0)";
		List<String> queries = new ArrayList<>();
		String query3 = "";
		if (pendingRedeems.getFee() > 0) {
			if (pendingRedeems.getRedeemType().equals(RedeemType.LOAN)) {
				query3 = "insert into UserAccountSummary (id,amount,createdTime,ettId,offerId,remarks,offerName,coin)values(0,-" + pendingRedeems.getFee() + ",now()," + pendingRedeems.getEttId() + "," + userAccountSummary.getOfferId() + ",'convenience charge','convenience fee on loan',0)";
			} else {
				query3 = "insert into UserAccountSummary (id,amount,createdTime,ettId,offerId,remarks,offerName,coin)values(0,-" + pendingRedeems.getFee() + ",now()," + pendingRedeems.getEttId() + "," + userAccountSummary.getOfferId() + ",'convenience charge','convenience fee on redemption',0)";
			}
		}
		queries.add(query1);
		queries.add(query2);
		if (!query3.equals("")) {
			queries.add(query3);
		}
		batchInsert(queries);
	}

	public void updateFailRedeem(User user, PendingRedeems pendingRedeems) {
		String query1 = "update UserAccount set currentBalance=currentBalance+" + (pendingRedeems.getAmount() + pendingRedeems.getFee()) + " where ettId=" + pendingRedeems.getEttId();
		String query2 = "update UserRedeem set status='FAILED',updatedTime=now(),vender='" + pendingRedeems.getVender() + "' where id=" + pendingRedeems.getId();

		// String query2 =
		// "update UserRedeem set status='FAILED',updatedTime=now() where id="+userRedeem.getId();
		List<String> queries = new ArrayList<>();
		queries.add(query1);
		queries.add(query2);
		if (user.getRedeemCount() > 0) {
			String query3 = "update User set redeemCount=redeemCount-1 where ettId=" + user.getEttId();
			queries.add(query3);
		}
		batchInsert(queries);
	}

	public void updateBarredRedeem(PendingRedeems pendingRedeems) {
		String query1 = "update UserAccount set currentBalance=currentBalance+" + (pendingRedeems.getAmount() + pendingRedeems.getFee()) + " where ettId=" + pendingRedeems.getEttId();
		String query2 = "update UserRedeem set status='BARRED',updatedTime=now() where id=" + pendingRedeems.getId();
		// String query2 =
		// "update UserRedeem set status='FAILED',updatedTime=now() where id="+userRedeem.getId();
		List<String> queries = new ArrayList<>();
		queries.add(query1);
		queries.add(query2);
		batchInsert(queries);
	}

	public void setProcessFee(PendingRedeems pendingRedeems) {
		String q = "update UserRedeem set fee=" + pendingRedeems.getFee() + ",postBalance=" + (pendingRedeems.getPostBalance() - pendingRedeems.getFee()) + " where id=" + pendingRedeems.getId();
		// String q =
		// "update UserRedeem set status='INVALID' where id="+userRedeem.getId();
		jdbcTemplate.update(q);
	}

	public void updateUserAccountFee(PendingRedeems pendingRedeems) {
		String q = "update UserAccount set currentBalance=currentBalance-" + pendingRedeems.getFee() + " where ettId=" + pendingRedeems.getEttId();
		jdbcTemplate.update(q);
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> getPendingRedeemList(){
		String query = "select id from PendingRedeems where status='PENDING' and vender='MOBILE_PULSA' and createdTime<date_sub(now(), INTERVAL 60 SECOND)";
		List<Long> list = (List<Long>)jdbcTemplate.queryForList(query, Long.class);
		return list;
	}

	public PendingRedeems getPendingRedeems(Long long1) {
		String que = "select * from PendingRedeems where id="+long1+"";
		PendingRedeems pendingRedeems = (PendingRedeems) jdbcTemplate.queryForObject(que, new PendingRedeemsRowMapper());
		return pendingRedeems;
	}
	
	
	
	public PendingRedeems getTransIdPendingRedeems(String id) {
		String que = "select * from PendingRedeems where trans_id='" + id + "' and status='PENDING'";
		PendingRedeems pendingRedeems = (PendingRedeems) jdbcTemplate.queryForObject(que, new PendingRedeemsRowMapper());
		return pendingRedeems;
	}

	public void insertPendingRedeems(PendingRedeems pr) {
		String que = "insert into PendingRedeems(id,amount,circle,createdTime,ettId,msisdn,operator,status,type,redeemType,vender,postBalance,fee) values(?,?,?,now(),?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(que, new Object[] { pr.getId(), pr.getAmount(), pr.getCircle(), pr.getEttId(), pr.getMsisdn(), pr.getOperator(), pr.getStatus(), pr.getType(), pr.getRedeemType(), pr.getVender(), pr.getPostBalance(), pr.getFee() });
	}

	public void updatePendingRedeems(PendingRedeems pendingRedeems) {
		String que = "update PendingRedeems set vender='"+pendingRedeems.getVender()+"',trans_id='" + pendingRedeems.getTrans_id() + "',status='" + pendingRedeems.getStatus() + "' where id="+pendingRedeems.getId()+"";
		jdbcTemplate.update(que);
	}

	public void deletePendingRedeems(String id) {
		String que = "delete from PendingRedeems where id=" + id + "";
		jdbcTemplate.update(que);
		// log.info("[Deleted from PendingRedeems]id="+id);
	}

	@SuppressWarnings("unchecked")
	public String getTrangloCallBackStatus(PendingRedeems pendingRedeems) {
		String que = "select status from TrangloCallBack where trans_id='" + pendingRedeems.getTrans_id() + "'";
		List<String> status = jdbcTemplate.queryForList(que, String.class);
		if (status.isEmpty()) {
	        return null;
	    } else {
	        return status.get(0);
	    }
	}

	public void updateCount(PendingRedeems pendingRedeems) {
		String que = "update PendingRedeems set count=" + pendingRedeems.getCount() + ",status='"+pendingRedeems.getStatus()+"' where id=" + pendingRedeems.getId() + "";
		jdbcTemplate.update(que);
	}

	public int getUserLocale(Long ettId) {
		String query = "select locale from User where ettId="+ettId+"";
		int locale = jdbcTemplate.queryForInt(query);
		return locale;
	}
}