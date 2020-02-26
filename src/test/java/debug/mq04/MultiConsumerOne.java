package debug.mq04;

import com.rabbitmq.client.*;

import java.io.IOException;

public class MultiConsumerOne {

	private static final String Exchange_Name="rabbit:mq04:exchange:e01";

	private static final String Queue_Name_01="rabbit:mq04:queue:q01";
	private static final String Routing_Key_01="rabbit:mq04:routing:key:r.*";

	public static void main(String[] args) {
		try {
			ConnectionFactory factory = new ConnectionFactory();
		    factory.setHost("192.168.9.131");
		    Connection connection = factory.newConnection();
		    Channel channel = connection.createChannel();

		    channel.exchangeDeclare(Exchange_Name, BuiltinExchangeType.TOPIC);
		    channel.queueDeclare(Queue_Name_01, true, false, false, null);
		    channel.queueBind(Queue_Name_01, Exchange_Name, Routing_Key_01);

		    Consumer consumer = new DefaultConsumer(channel) {
		      @Override
		      public void handleDelivery(String consumerTag, Envelope envelope,AMQP.BasicProperties properties, byte[] body) throws IOException {
		    	  String message = new String(body, "UTF-8");
		    	  System.out.println("队列1中的消息---> "+message);
		      }
		    };

		    channel.basicConsume(Queue_Name_01, true, consumer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
