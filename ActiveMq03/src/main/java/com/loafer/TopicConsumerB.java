package com.loafer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;


public class TopicConsumerB {
	public String receiveTextMessage(){
		String resultCode = "";
		ConnectionFactory factory = null;
		Connection connection = null;
		Session session = null;
		Destination destination = null;
		//消息的消费者，用于接收消息的对象
		MessageConsumer consumer = null;
		TextMessage message = null;
		
		try{
			factory = new ActiveMQConnectionFactory("admin","admin","tcp://192.168.0.32:61616");
			
			connection = factory.createConnection();
			//消息的消费者必须启动连接，否支无法处理消息
			connection.start();
			
			session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			
			destination = session.createTopic("topicDemo");
			
			//创建消息消费者对象，在制定的目的地中获取消息
			consumer = session.createConsumer(destination);
			
		    message = (TextMessage) consumer.receive();
		    resultCode = message.getText();
			
			
			/**消费者消息监听开始*/
//			consumer.setMessageListener(new MessageListener() {
//				
//				public void onMessage(Message message) {
//					try {
//	                    System.out.println("接收消息  = [" + ((TextMessage) message).getText() + "]");
//	                } catch (JMSException e) {
//	                    e.printStackTrace();
//	                }
//				}
//			});
			//阻塞当前代码。保证listener代码未结束。如果代码结束了，监听器自动关闭
//			System.in.read();
			/**消费者消息监听结束*/
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(consumer != null){//回收消息消费者
				try {
					consumer.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			if(session != null){//回收会话对象
				try {
					session.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			if(connection != null){//回收连接对象
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
		return resultCode;
	}
	
	
	public static void main(String[] args){
		System.out.println("消费者B程序开始启动.....");
		TopicConsumerB consumer = new TopicConsumerB();
		String messageString = consumer.receiveTextMessage();
		System.out.println("接收的消息内容是：" + messageString);
		System.out.println("消费者B程序已经关闭.....");
	}
}
