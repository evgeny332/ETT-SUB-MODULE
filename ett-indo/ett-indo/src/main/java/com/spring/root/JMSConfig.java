package com.spring.root;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author Ankit
 */
@Configuration("jmsConfiguration")
@PropertySource(value = {"file:///${CONFIG_PATH_ETT}//activeMq.properties"})

public class JMSConfig {

    @Autowired
    private Environment environment;

    @Bean(name = "jmsConnectionFactory")
    public PooledConnectionFactory getPooledConnectionFactory() {
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setUseAsyncSend(true);
        activeMQConnectionFactory.setBrokerURL(environment.getProperty("BROKER_URL"));
        pooledConnectionFactory.setConnectionFactory(activeMQConnectionFactory);
        pooledConnectionFactory.setMaxConnections(70);
        pooledConnectionFactory.setMaximumActive(15);
        return pooledConnectionFactory;
    }


    @Bean(name = "jmsTempletate")
    public JmsTemplate jmsTempletate(){
        JmsTemplate jmsTemplate = new JmsTemplate(getPooledConnectionFactory());
        jmsTemplate.setDefaultDestinationName(environment.getProperty("DEFAULT_DESTINATION_NAME", "PUSH_NOTIFICATION"));
        return jmsTemplate;
    }
}
