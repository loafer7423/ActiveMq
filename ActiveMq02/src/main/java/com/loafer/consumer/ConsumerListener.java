package com.loafer.consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.sun.xml.internal.org.jvnet.fastinfoset.VocabularyApplicationData;

/**
 * 使用监听器的方式，实现消息的处理【消费】
 * @author 王东
 *
 */
public class ConsumerListener {
	
	/**
	 * 处理消息
	 */
	public void consumMessage(){
		ConnectionFactory factory = null;
		Connection connection = null;
		Session session = null;
		Destination destination = null;
		MessageConsumer consumer = null;
		try{
			factory = new ActiveMQConnectionFactory("guest","guest","tcp://192.168.0.32:61616");
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("test-listener");
			consumer = session.createConsumer(destination);
			//注册监听器。注册成功后，队列中的消息变化会自动触发监听器代码。接收消息并处理
			consumer.setMessageListener(new MessageListener(){
				/*
				 *监听器一旦注册，永久有效。
				 *永久-consumer线程不关闭
				 *处理消息的方式：只要有消息未处理，自动调用onMessage方法，处理消息
				 *监听器可以注册若干。注册多个监听器，相当于集群
				 *ActiveMQ自动的循环调用多个监听器，处理队列中的消息，实现并行处理
				 *
				 * 处理消息的方法。就是监听方法
				 * 监听的事件是：消息，消息未处理
				 * 要处理的具体内容：消息处理
				 * @param message-未处理的消息
				 * 
				 */
				public void onMessage(Message message) {
					try{
						ObjectMessage om = (ObjectMessage) message;
						Object data = om.getObject();
						System.out.println(data);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
			});
			//阻塞当前代码。保证listener代码未结束。如果代码结束了，监听器自动关闭
			System.in.read();
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
	}


	public static void main(String[] args){
		ConsumerListener listener = new ConsumerListener();
		listener.consumMessage();
	}
	
}
