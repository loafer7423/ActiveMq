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
		//��Ϣ�������ߣ����ڽ�����Ϣ�Ķ���
		MessageConsumer consumer = null;
		TextMessage message = null;
		
		try{
			factory = new ActiveMQConnectionFactory("admin","admin","tcp://192.168.0.32:61616");
			
			connection = factory.createConnection();
			//��Ϣ�������߱����������ӣ���֧�޷�������Ϣ
			connection.start();
			
			session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			
			destination = session.createTopic("topicDemo");
			
			//������Ϣ�����߶������ƶ���Ŀ�ĵ��л�ȡ��Ϣ
			consumer = session.createConsumer(destination);
			
		    message = (TextMessage) consumer.receive();
		    resultCode = message.getText();
			
			
			/**��������Ϣ������ʼ*/
//			consumer.setMessageListener(new MessageListener() {
//				
//				public void onMessage(Message message) {
//					try {
//	                    System.out.println("������Ϣ  = [" + ((TextMessage) message).getText() + "]");
//	                } catch (JMSException e) {
//	                    e.printStackTrace();
//	                }
//				}
//			});
			//������ǰ���롣��֤listener����δ�����������������ˣ��������Զ��ر�
//			System.in.read();
			/**��������Ϣ��������*/
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(consumer != null){//������Ϣ������
				try {
					consumer.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			if(session != null){//���ջỰ����
				try {
					session.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			if(connection != null){//�������Ӷ���
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
		System.out.println("������B����ʼ����.....");
		TopicConsumerB consumer = new TopicConsumerB();
		String messageString = consumer.receiveTextMessage();
		System.out.println("���յ���Ϣ�����ǣ�" + messageString);
		System.out.println("������B�����Ѿ��ر�.....");
	}
}
