package com.etxWeb.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource({ "classpath:persistence-multiple-db.properties" })
@EnableJpaRepositories(
    basePackages = "com.etxWeb.repository.passive", 
    entityManagerFactoryRef = "passiveEntityManager", 
    transactionManagerRef = "passiveTransactionManager"
)
public class PassiveDBConfig {
	@Autowired
    private Environment env;
     
    @Bean
    public LocalContainerEntityManagerFactoryBean passiveEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(passiveDataSource());
        em.setPackagesToScan(new String[] { "com.etxWeb.entity.passive" });
 
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto.passive"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect.passive"));
        em.setJpaPropertyMap(properties);
 
        return em;
    }
 
   @Bean
    public DataSource passiveDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName.passive"));
        dataSource.setUrl(env.getProperty("user.jdbc.url.passive"));
        dataSource.setUsername(env.getProperty("jdbc.user.passive"));
        dataSource.setPassword(env.getProperty("jdbc.pass.passive"));
 
        return dataSource;
    }
 
    @Bean
    public PlatformTransactionManager passiveTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(passiveEntityManager().getObject());
        return transactionManager;
    }

}
