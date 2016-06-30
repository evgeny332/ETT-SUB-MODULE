package com.rh.main;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rh.interfaces.CallBack;

public class CallBackReceiver
  implements Runnable
{
  private static Log log = LogFactory.getLog(CallBackReceiver.class);
  private String queueName;
  private String url;
  CallBack api;

  public CallBackReceiver(String paramString1, String paramString2, CallBack paramCallBack)
  {
    this.url = paramString1;
    this.queueName = paramString2;
    this.api = paramCallBack;
    new Thread(this).start();
  }

  public void run()
  {
    log.info("In receiver.java: starting app");
    ActiveMQConnectionFactory localActiveMQConnectionFactory = new ActiveMQConnectionFactory(this.url);
    Connection localConnection = null;
    try
    {
      localConnection = localActiveMQConnectionFactory.createConnection();
      localConnection.start();
      Session localSession = localConnection.createSession(false, 1);
      Queue localQueue = localSession.createQueue(this.queueName);
      MessageConsumer localMessageConsumer = localSession.createConsumer(localQueue);
      while (true)
      {
        Message localMessage = localMessageConsumer.receive();
        if ((localMessage instanceof TextMessage))
        {
          TextMessage localTextMessage = (TextMessage)localMessage;
          try
          {
//            log.info("callback_id=" + localTextMessage.getText());
            this.api.handleCallBack(localTextMessage.getText());
          }
          catch (Exception localException2)
          {
            log.error(localTextMessage.getText() + "|error in proceesing  " + localException2);
            localException2.printStackTrace();
          }
        }
      }
    }
    catch (Exception localException1)
    {
      log.error("########" + localException1);
      localException1.printStackTrace();
    }
    finally
    {
      try
      {
        localConnection.close();
      }
      catch (JMSException localJMSException2)
      {
        localJMSException2.printStackTrace();
      }
    }
  }
}