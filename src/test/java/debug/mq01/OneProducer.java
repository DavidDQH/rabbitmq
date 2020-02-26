package debug.mq01;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class OneProducer {

	private static final String Queue_Name="test";

	@Autowired
	private static Environment env;

	public static void main(String[] args) {
		try {
			ConnectionFactory factory = new ConnectionFactory();
		    factory.setHost("192.168.9.131");
		    
			  //factory.setUsername("guest");
		    //factory.setPassword("guest"); 
		    
		    Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
		    
		    channel.queueDeclare(Queue_Name, true, false, false, null);
		    String message = "Hello World!!!";
		    channel.basicPublish("", Queue_Name, null, message.getBytes("UTF-8"));
		    
		    System.out.println("推送结束---> ");

		    channel.close();
		    connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
