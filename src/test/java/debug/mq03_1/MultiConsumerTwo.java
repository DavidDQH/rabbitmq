package debug.mq03_1;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class MultiConsumerTwo {
	
	private static final String Exchange_Name="rabbit:mq03:exchange:e01";
	
	private static final String Queue_Name_02="rabbit:mq03:queue:q02";

	private static final String Routing_Key_02="rabbit:mq03:routing:key:r02";
	private static final String Routing_Key_03="rabbit:mq03:routing:key:r03";
	
	public static void main(String[] args) {
		try {
			ConnectionFactory factory = new ConnectionFactory();
		    factory.setHost("192.168.9.131");
		    Connection connection = factory.newConnection();
		    Channel channel = connection.createChannel();
		    
		    channel.exchangeDeclare(Exchange_Name, BuiltinExchangeType.DIRECT);
		    channel.queueDeclare(Queue_Name_02, true, false, false, null);
		    channel.queueBind(Queue_Name_02, Exchange_Name, Routing_Key_02);
		    channel.queueBind(Queue_Name_02, Exchange_Name, Routing_Key_03);
		    
		    Consumer consumer = new DefaultConsumer(channel) {
		      @Override
		      public void handleDelivery(String consumerTag, Envelope envelope,
		                                 AMQP.BasicProperties properties, byte[] body) throws IOException {
		        String message = new String(body, "UTF-8");
		        System.out.println("对列2中消息---> "+message);
		      }
		    };
		    
		    channel.basicConsume(Queue_Name_02, true, consumer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
