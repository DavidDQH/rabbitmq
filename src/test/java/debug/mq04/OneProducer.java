package debug.mq04;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class OneProducer {

	private static final String Exchange_Name="rabbit:mq04:exchange:e01";
	
	
	public static void main(String[] args) {
		try {
			ConnectionFactory factory = new ConnectionFactory();
		    factory.setHost("192.168.9.131");
		    Connection connection = factory.newConnection();
		    Channel channel = connection.createChannel();
		    
		    String message = "topicExchange-publish的消息";
		    
		    channel.basicPublish(Exchange_Name, "rabbit:mq04:routing:key:r.orange", null, message.getBytes("UTF-8"));
		    channel.basicPublish(Exchange_Name, "rabbit:mq04:routing:key:r.orange.apple", null, message.getBytes("UTF-8"));
		    
		    System.out.println("消息推送完成---> ");
		    channel.close();
		    connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
