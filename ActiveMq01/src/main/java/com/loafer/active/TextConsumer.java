package com.loafer.active;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TextConsumer {
	
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
			
			destination = session.createQueue("first-mq");
			//创建消息消费者对象，在制定的目的地中获取消息
			consumer = session.createConsumer(destination);
			//获取队列中的消息
			message = (TextMessage) consumer.receive();
			//处理文本消息
			resultCode = message.getText();
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
		TextConsumer consumer = new TextConsumer();
		String messageString = consumer.receiveTextMessage();
		System.out.print("接收的消息内容是：" + messageString);
	}

}
