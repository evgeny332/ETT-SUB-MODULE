package com.rh.main;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rh.interfaces.APIInterface;

public class Receiver implements Runnable{
	/**
	 * @param args
	 */
	private static Log log = LogFactory.getLog(Receiver.class);
	private String queueName;
	private String url;
	APIInterface api;

	public Receiver(String url, String queueName, APIInterface api) {
		this.url = url;
		this.queueName = queueName;
		this.api = api;
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
				Message message = consumer.receive();
				if (message instanceof javax.jms.TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					try {						
						log.info("redeem_id=" + textMessage.getText());
						api.updatePokktInfo(textMessage
								.getText());
					} catch (Exception e) {
						log.error(textMessage.getText()+"|error in proceesing  "+e);
						e.printStackTrace();
					}
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