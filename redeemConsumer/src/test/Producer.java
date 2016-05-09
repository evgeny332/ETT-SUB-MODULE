package test;
import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {
    private static String url = "tcp://localhost:61616?persistent=false";
    private static String subject = "test1";
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(subject);
        MessageProducer producer = session.createProducer(destination);
        
        String s = "1";
        TextMessage message = session.createTextMessage(s);
       
        producer.send(message);
        
        System.out.println("Sent message '" + message.getText() + "'");
        connection.close();
    }
}