package test;
import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {
    private static String url = "tcp://54.209.220.78:61616";
    private static String subject = "EDR";
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(subject);
        MessageProducer producer = session.createProducer(destination);
        
        String s = "1";
        MapMessage message = session.createMapMessage();
        message.setString("type", "CLICK");
        message.setLong("ettId", 17);
        message.setLong("offerId",223);
        message.setString("appKeys", "test");
        producer.send(message);

        
        System.out.println("Sent message '" + message + "'");
        connection.close();
    }
}