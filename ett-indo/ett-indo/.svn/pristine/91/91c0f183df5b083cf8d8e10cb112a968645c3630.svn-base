package com.web.rest.controller;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/v1")
public class ActiveMqController {

	private static Logger LOGGER = LoggerFactory
			.getLogger(ActiveMqController.class);

	@Resource
	JmsTemplate jmsTemplate;

	@RequestMapping(value = "/activemq/enqueue/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> push(@RequestParam(value = "id") long id,
			@RequestParam(value = "queueName") String queueName) {

		LOGGER.info("/activemq/enqueue/ id {}, queueName {}", id, queueName);
		enqueue(id, queueName);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private void enqueue(final Long id, final String queueName) {
		try {
			jmsTemplate.send(queueName, new MessageCreator() {
				@Override
				public Message createMessage(Session session)
						throws JMSException {
					TextMessage textMessage = session.createTextMessage();
					textMessage.setText(id + "");
					return textMessage;
				}
			});
		} catch (Exception e) {
			LOGGER.error("error in enqueue" + e);
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/activemqMap/enqueue/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> put(@RequestParam(value = "msg") String msg,
			@RequestParam(value = "queueName") String queueName) {

		LOGGER.info("/activemqMap/enqueue/ msg {}, queueName {}", msg, queueName);
		enqueueMap(msg, queueName);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private void enqueueMap(final String msg, final String queueName) {
		try {
			final String msg1[] = msg.split("^");
			jmsTemplate.send(queueName, new MessageCreator() {
				@Override
				public Message createMessage(Session session)
						throws JMSException {
					MapMessage mapMessage = session.createMapMessage();
					//textMessage.setText(id + "");
					for(String msg11:msg1) {
							String msg2[]=msg11.split("-");
							mapMessage.setString(msg2[0], msg2[1]);
						}
					return mapMessage;
				}
			});
			
		} catch (Exception e) {
			LOGGER.error("error in enqueue" + e);
			e.printStackTrace();
		}

	}

}
