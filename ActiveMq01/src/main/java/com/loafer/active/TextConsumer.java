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
		//��Ϣ�������ߣ����ڽ�����Ϣ�Ķ���
		MessageConsumer consumer = null;
		TextMessage message = null;
		
		try{
			factory = new ActiveMQConnectionFactory("admin","admin","tcp://192.168.0.32:61616");
			
			connection = factory.createConnection();
			//��Ϣ�������߱����������ӣ���֧�޷�������Ϣ
			connection.start();
			
			session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			
			destination = session.createQueue("first-mq");
			//������Ϣ�����߶������ƶ���Ŀ�ĵ��л�ȡ��Ϣ
			consumer = session.createConsumer(destination);
			//��ȡ�����е���Ϣ
			message = (TextMessage) consumer.receive();
			//�����ı���Ϣ
			resultCode = message.getText();
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
		TextConsumer consumer = new TextConsumer();
		String messageString = consumer.receiveTextMessage();
		System.out.print("���յ���Ϣ�����ǣ�" + messageString);
	}

}
