package debug.mq02;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class OneProducer {

	private static final String Exchange_Name="rabbit:mq02:exchange:e01";

	public static void main(String[] args) {
		try {
			ConnectionFactory factory = new ConnectionFactory();
		    factory.setHost("192.168.9.131");
		    Connection connection = factory.newConnection();
		    Channel channel = connection.createChannel();

		    channel.exchangeDeclare(Exchange_Name, BuiltinExchangeType.FANOUT);

		    String message = "fanoutExchange-publish的消息";
		    channel.basicPublish(Exchange_Name, "", null, message.getBytes("UTF-8"));

		    System.out.println("数据推送---> ");
		    channel.close();
		    connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
