package com.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import com.domain.entity.DeviceIdInstalledApps;
import com.domain.entity.InstalledApps;
import com.domain.entity.InstalledAppsFirstTime;
import com.service.CustomDatabaseService;
import com.web.rest.dto.TarrotDto;

@Service
public class CustomDatabaseServiceImpl implements CustomDatabaseService {

	private static Logger LOGGER = LoggerFactory.getLogger(CustomDatabaseServiceImpl.class);
	
	@Autowired
	javax.sql.DataSource dataSource;
	
	@PersistenceContext
	EntityManager entityManager;
	
	
	
	@Override
	public void saveInstalledAppsFirstTime(List<InstalledAppsFirstTime> installedAppsFirstTimeList) {
		
		if(installedAppsFirstTimeList.isEmpty()){
			return;
		}
		
		StringBuilder queryBuilder = new StringBuilder("INSERT IGNORE INTO InstalledAppsFirstTime (id,appKey,createdTs,ettId,createdTime) values ");
		
		String placeHolder = "(?,?,?,?,?)";
		for(int i=0;i<installedAppsFirstTimeList.size();i++){
			if(i!=0)
				queryBuilder.append(",");
			queryBuilder.append(placeHolder);
		}
		
		final String query = queryBuilder.toString();
		//LOGGER.info("1final query {}",query);
		Connection connection = null;
		//LOGGER.info("1.dataSource={},connection={}",dataSource,connection);
		try{
			
			connection = DataSourceUtils.getConnection(dataSource);
			
			//connection = dataSource.getConnection();
			//LOGGER.info("2.dataSource={},connection={}",dataSource,connection);
			if(connection!=null){
				
				PreparedStatement preparedStatement = connection.prepareStatement(query);
                int i=1;
                for(InstalledAppsFirstTime installedAppsFirstTime : installedAppsFirstTimeList){
                	//LOGGER.info("installedAppsFirstTime={}",installedAppsFirstTime);
                	preparedStatement.setString(i++, installedAppsFirstTime.getId());
                	preparedStatement.setString(i++, installedAppsFirstTime.getAppKey());
                	long cTime = (new java.util.Date()).getTime();
                	if(installedAppsFirstTime.getCreatedTs() != null) {
                		cTime = installedAppsFirstTime.getCreatedTs().getTime();
                	}
                	//preparedStatement.setDate(i++, new java.sql.Date(cTime));
                	preparedStatement.setTimestamp(i++, new java.sql.Timestamp(cTime));
                	preparedStatement.setLong(i++, installedAppsFirstTime.getEttId());
                	//preparedStatement.setDate(i++, new java.sql.Date(installedAppsFirstTime.getCreatedTime().getTime()));
                	preparedStatement.setTimestamp(i++, new java.sql.Timestamp(installedAppsFirstTime.getCreatedTime().getTime()));
                	//preparedStatement.addBatch();
                }
                long time1= System.currentTimeMillis();
                boolean checkInsert = preparedStatement.execute();
                //preparedStatement.executeBatch();
                //connection.createStatements().execute(query);
                LOGGER.info("Time in inserting InstalledAppsFirstTime : {} ms,checkInsert:{},", (System.currentTimeMillis() - time1),checkInsert	);
			}
		} catch (Exception e) {
			LOGGER.error("Error occured while inserting from CustomDataBaseService "+e);
			//LOGGER.info("Error occured while inserting from CustomDataBaseService "+e);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            DataSourceUtils.releaseConnection(connection,dataSource);
            
           /* if(connection!=null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }*/
        }
	}

	
	@Override
	public void saveInstalledApps(List<InstalledApps> installedAppsList) {
		
		if(installedAppsList.isEmpty()){
			return;
		}
		
		StringBuilder queryBuilder = new StringBuilder("INSERT IGNORE INTO InstalledApps (id,appKey,createdTs,ettId) values ");
		
		String placeHolder = "(?,?,?,?)";
		for(int i=0;i<installedAppsList.size();i++){
			if(i!=0)
				queryBuilder.append(",");
			queryBuilder.append(placeHolder);
		}
		
		final String query = queryBuilder.toString();
		//LOGGER.info("final query {}",query);
		Connection connection = null;
		try{
			connection = DataSourceUtils.getConnection(dataSource);
			if(connection!=null){
				PreparedStatement preparedStatement = connection.prepareStatement(query);
                int i=1;
                for(InstalledApps installedApps : installedAppsList){
                	preparedStatement.setString(i++, installedApps.getId());
                	preparedStatement.setString(i++, installedApps.getAppKey());
                	//preparedStatement.setDate(i++, new java.sql.Date(installedApps.getCreatedTs().getTime()));
                	preparedStatement.setTimestamp(i++, new java.sql.Timestamp(installedApps.getCreatedTs().getTime()));
                	preparedStatement.setLong(i++, installedApps.getEttId());
                	
                	//preparedStatement.addBatch();
                }
                long time1= System.currentTimeMillis();
                preparedStatement.execute();
                //preparedStatement.executeBatch();
                //connection.createStatement().execute(query);
                LOGGER.info("Time in inserting InstalledApps : {} ms", (System.currentTimeMillis() - time1));
			}
		} catch (SQLException e) {
			LOGGER.error("Error occured while inserting from CustomDataBaseService "+e);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            DataSourceUtils.releaseConnection(connection,dataSource);
           /* if(connection!=null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }*/
        }
	}

	@Override
	public void saveInstalledAppsFirstTime1(List<InstalledAppsFirstTime> installedAppsFirstTimeList) {
		
		if(installedAppsFirstTimeList.isEmpty()){
			return;
		}
		
		StringBuilder queryBuilder = new StringBuilder("INSERT IGNORE INTO InstalledAppsFirstTime (id,appKey,createdTs,ettId,createdTime) values ");
		
		String placeHolder = "(?,?,?,?,?)";
		for(int i=0;i<installedAppsFirstTimeList.size();i++){
			if(i!=0)
				queryBuilder.append(",");
			queryBuilder.append(placeHolder);
		}
		
		final String query = queryBuilder.toString();
		//LOGGER.info("final query {}",query);
		Connection connection = null;
		try{
			connection = DataSourceUtils.getConnection(dataSource);
			if(connection!=null){
				PreparedStatement preparedStatement = connection.prepareStatement(query);
                int i=1;
                for(InstalledAppsFirstTime installedAppsFirstTime : installedAppsFirstTimeList){
                	preparedStatement.setString(i++, installedAppsFirstTime.getId());
                	preparedStatement.setString(i++, installedAppsFirstTime.getAppKey());
                	long cTime = (new java.util.Date()).getTime();
                	if(installedAppsFirstTime.getCreatedTs() != null) {
                		cTime = installedAppsFirstTime.getCreatedTs().getTime();
                	}
                	//preparedStatement.setDate(i++, new java.sql.Date(installedApps.getCreatedTs().getTime()));
                	preparedStatement.setTimestamp(i++, new java.sql.Timestamp(cTime));
                	preparedStatement.setLong(i++, installedAppsFirstTime.getEttId());
                	preparedStatement.setTimestamp(i++, new java.sql.Timestamp((new java.util.Date()).getTime()));
                	//preparedStatement.addBatch();
                }
                long time1= System.currentTimeMillis();
                preparedStatement.execute();
                //preparedStatement.executeBatch();
                //connection.createStatement().execute(query);
                LOGGER.info("Time in inserting InstalledApps : {} ms", (System.currentTimeMillis() - time1));
			}
		} catch (SQLException e) {
			LOGGER.error("Error occured while inserting from CustomDataBaseService "+e);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            DataSourceUtils.releaseConnection(connection,dataSource);
           /* if(connection!=null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }*/
        }
	}


	@Override
	public void saveDeviceIdInstalledApps(List<DeviceIdInstalledApps> deviceIdInstalledAppsList) {
		
		if(deviceIdInstalledAppsList.isEmpty()){
			return;
		}
		
		StringBuilder queryBuilder = new StringBuilder("INSERT IGNORE INTO DeviceIdInstalledApps (id,appKey,createdTs,deviceId,ettId,createdTime) values ");
		
		String placeHolder = "(?,?,?,?,?,?)";
		for(int i=0;i<deviceIdInstalledAppsList.size();i++){
			if(i!=0)
				queryBuilder.append(",");
			queryBuilder.append(placeHolder);
		}
		
		final String query = queryBuilder.toString();
		//LOGGER.info("final query {}",query);
		Connection connection = null;
		try{
			connection = DataSourceUtils.getConnection(dataSource);
			if(connection!=null){
				PreparedStatement preparedStatement = connection.prepareStatement(query);
                int i=1;
                for(DeviceIdInstalledApps deviceIdInstalledApps : deviceIdInstalledAppsList){
                	preparedStatement.setString(i++, deviceIdInstalledApps.getId());
                	preparedStatement.setString(i++, deviceIdInstalledApps.getAppKey());
                	long cTime = (new java.util.Date()).getTime();
                	if(deviceIdInstalledApps.getCreatedTs() != null) {
                		cTime = deviceIdInstalledApps.getCreatedTs().getTime();
                	}
                	//preparedStatement.setDate(i++, new java.sql.Date(installedApps.getCreatedTs().getTime()));
                	preparedStatement.setTimestamp(i++, new java.sql.Timestamp(cTime));
                	
                	preparedStatement.setString(i++, deviceIdInstalledApps.getDeviceId());
                	preparedStatement.setLong(i++, deviceIdInstalledApps.getEttId());
                	if(deviceIdInstalledApps.getCreatedTime() != null) {
                		cTime = deviceIdInstalledApps.getCreatedTime().getTime();
                	}
                	preparedStatement.setTimestamp(i++, new java.sql.Timestamp(cTime));
                	//preparedStatement.addBatch();
                }
                long time1= System.currentTimeMillis();
                preparedStatement.execute();
                //preparedStatement.executeBatch();
                //connection.createStatement().execute(query);
                LOGGER.info("Time in inserting DeviceIdInstalledApps : {} ms", (System.currentTimeMillis() - time1));
			}
		} catch (SQLException e) {
			LOGGER.error("Error occured while inserting from CustomDataBaseService "+e);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            DataSourceUtils.releaseConnection(connection,dataSource);
           /* if(connection!=null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }*/
        }
	}
	
	@Override
	public List<TarrotDto> getTarrotDto(String tarrotName,List<Long> update_id) {
		StringBuilder queryBuilder = new StringBuilder("select a.id id,a.tarrotName,a.cardName cardName,a.askValue askValue,(select b.detail from Tarrot b where a.cardName=b.cardName and a.tarrotName=b.tarrotName order by rand() limit 1,1) as detail from Tarrot  a where tarrotName='"+tarrotName+"' group by 1,2");
		final String query = queryBuilder.toString();
		//LOGGER.info("final query {}",query);
		Connection connection = null;
		List<TarrotDto> dtos = new ArrayList<TarrotDto>();
		try{
			connection = DataSourceUtils.getConnection(dataSource);
			if(connection!=null){
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				long time1= System.currentTimeMillis();
				ResultSet rs = preparedStatement.executeQuery(query);
                
                while(rs.next()) {
                	TarrotDto dto = new TarrotDto();
                	dto.setId(rs.getLong("id"));
                	if(update_id.contains(rs.getLong("id"))) {
                		dtos.add(dto);
                		continue;
                	}
                	dto.setDetail(rs.getString("detail"));
                	dto.setTitle(rs.getString("cardName"));
                	dto.setValue(rs.getString("askValue"));
                	dtos.add(dto);
                }
				LOGGER.info("Time in inserting getTarrotDto : {} ms", (System.currentTimeMillis() - time1));
			}
		} catch (SQLException e) {
			LOGGER.error("Error occured while inserting from CustomDataBaseService "+e);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            DataSourceUtils.releaseConnection(connection,dataSource);
        }
		return dtos;
	}
}
