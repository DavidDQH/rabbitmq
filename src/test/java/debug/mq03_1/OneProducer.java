package debug.mq03_1;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * direct-exchange+routingKey�������뽻�����İ�-ps������ԵĶ��ļ�������
 * @author steadyjack
 */
public class OneProducer {

	private static final String Exchange_Name="rabbit:mq03:exchange:e01";
	
	private static final String Queue_Name_01="rabbit:mq03:queue:q01";
	private static final String Queue_Name_02="rabbit:mq03:queue:q02";
	
	private static final String Routing_Key_01="qwewq";
	private static final String Routing_Key_02="acacca";
	private static final String Routing_Key_03="deded";
	
	
	public static void main(String[] args) {
		try {
			ConnectionFactory factory = new ConnectionFactory();
		    factory.setHost("192.168.9.131");
		    Connection connection = factory.newConnection();
		    Channel channel = connection.createChannel();

		    channel.exchangeDeclare(Exchange_Name, BuiltinExchangeType.DIRECT);
		    
		    channel.queueDeclare(Queue_Name_01, true, false, false, null);
		    channel.queueBind(Queue_Name_01, Exchange_Name, Routing_Key_01);
		    
		    channel.queueDeclare(Queue_Name_02, true, false, false, null);
		    channel.queueBind(Queue_Name_02, Exchange_Name, Routing_Key_02);
		    channel.queueBind(Queue_Name_02, Exchange_Name, Routing_Key_03);
		    
		    String message01 = "directExchange-publish的消息-r01";
		    String message02 = "directExchange-publish的消息-r02";
		    String message03 = "directExchange-publish的消息-r03";
		    
		    channel.basicPublish(Exchange_Name, Routing_Key_01, null, message01.getBytes("UTF-8"));
		    channel.basicPublish(Exchange_Name, Routing_Key_02, null, message02.getBytes("UTF-8"));
		    channel.basicPublish(Exchange_Name, Routing_Key_03, null, message03.getBytes("UTF-8"));
		    
		    System.out.println("消息推送---> ");
		    channel.close();
		    connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
