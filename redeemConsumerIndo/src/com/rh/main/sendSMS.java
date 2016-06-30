/*package com.rh.main;

import java.util.concurrent.BlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class sendSMS implements Runnable {

	private static Log log = LogFactory.getLog(sendSMS.class);
	private String queueName;
	private String url;

	BlockingQueue<String> fifo = null;
	public sendSMS(BlockingQueue<String> fifo) {

		
		this.url = paramString1;
		this.queueName = paramString2;
		this.fifo=fifo;
		new Thread(this).start();
		ConfigHolder configHolder = null;
		try {
			configHolder = new ConfigHolder();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (configHolder.getProperties().getProperty("FLIPKART_EGV_ENABLED").equals("true")) {
			if (configHolder.getProperties().getProperty("FLIPKART_EGV_SMS_ENABLED").equals("true")) {
				new Thread(this).start();
			}
		}
	}

	//@Override
	public void run() {
		log.info("In sendSMS.java: starting app");
		ActiveMQConnectionFactory localActiveMQConnectionFactory = new ActiveMQConnectionFactory(this.url);
		Connection localConnection = null;

		try {
			localConnection = localActiveMQConnectionFactory.createConnection();
			localConnection.start();
			Session localSession = localConnection.createSession(false, 1);
			Queue localQueue = localSession.createQueue(this.queueName);
			MessageConsumer localMessageConsumer = localSession.createConsumer(localQueue);
			while (true) {
				Message localMessage = localMessageConsumer.receive();
				if ((localMessage instanceof TextMessage)) {
					TextMessage localTextMessage = (TextMessage) localMessage;
					try {

						log.info("url = " + localTextMessage.getText());
						String url = localTextMessage.getText();
						SendGetRequest getRequest = new SendGetRequest();
						getRequest.SendMessage(url);

					} catch (Exception e) {
						log.error(localTextMessage.getText() + "|error in proceesing  " + e);
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in fetching sms queue element" + e);
		}
	}
	
	@Override
	public void run() {
		while(true) {
			try  {
				String data = fifo.take();
				System.out.println("data|"+data);
			}catch(Exception ex) {
				System.out.println("Error in pulling the data from queue|"+ex);
				ex.printStackTrace();
			}
		}
	}
}
*/