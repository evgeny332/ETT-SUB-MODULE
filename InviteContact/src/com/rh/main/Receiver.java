package com.rh.main;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rh.persistence.DBPersister;
import com.rh.interfaces.APIInterface;
import com.rh.interfaces.ApiImpl;

public class Receiver implements Runnable{
	/**
	 * @param args
	 */
	private static Log log = LogFactory.getLog(Receiver.class);
	private String queueName;
	private String url;
	APIInterface api;
	DBPersister dbPersister;

	public Receiver(String url, String queueName, DBPersister dbPersister) {
		this.url = url;
		this.queueName = queueName;
		this.dbPersister=dbPersister;
		new Thread(this).start();
	}

	public void run() {
		log.info("In receiver.java: starting app");
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		
		Connection connection = null;
		try {
			connection = connectionFactory.createConnection();
		
			connection.start();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
		
			Destination destination = session.createQueue(queueName);
			MessageConsumer consumer = session.createConsumer(destination);
		
			while (true) {
				Message message = null;
				try {
					message = consumer.receive();
					if (message instanceof javax.jms.MapMessage) {
						//log.info("push Message=" + message);
						MapMessage mapMessage = (MapMessage) message;
						//log.info("push Message=" + message);
						String type=mapMessage.getString("type");
						if(type.equals("OTPREQUEST")) {
							log.info("type|"+type+" msg|"+mapMessage.getLong("msg")+" ettId|"+mapMessage.getLong("ettId"));
							dbPersister.otpRequst(mapMessage.getLong("msg"),mapMessage.getLong("ettId"));
						}
						else if(type.equals("ContactFilter")) {
							APIInterface apiInterface= new ApiImpl();
							String msg = apiInterface.unCompressData(mapMessage.getString("msg"));
							//String msg = mapMessage.getString("msg");
							log.info("type|"+type+" msg|"+msg+" ettId|"+mapMessage.getLong("ettId"));
							dbPersister.ContactFilter(msg,mapMessage.getLong("ettId"));
						}
						else {
							log.info("[wrong type found]["+type+"]");
						}
					}
						
				} catch (Exception e) {
					log.error(message+"|error in proceesing  "+e);
					e.printStackTrace();
				}
			}	
		}catch (Exception e) {
			log.error("########"+e);
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}