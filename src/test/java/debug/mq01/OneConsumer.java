package debug.mq01;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.IOException;

public class OneConsumer {
	private static final String Queue_Name="test";

	@Autowired
	private static  Environment env;
	
	public static void main(String[] args) {
		try {
			ConnectionFactory factory = new ConnectionFactory();
		    factory.setHost("192.168.9.131");

		    Connection connection = factory.newConnection();
		    Channel channel = connection.createChannel();
		    channel.queueDeclare(Queue_Name, true, false, false, null);
		    
		    Consumer consumer = new DefaultConsumer(channel) {
		      @Override
		      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
		          throws IOException {
		        String message = new String(body, "UTF-8");
		        System.out.println("读取数据---> "+message);
		      }
		    };
		    
		    channel.basicConsume(Queue_Name, true, consumer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
