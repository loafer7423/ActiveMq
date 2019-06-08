package com.loafer.producer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.loafer.consumer.ConsumerListener;

public class ProducerSend {
	
	public void sendMessage(Object obj){
		ConnectionFactory factory = null;
		Connection connection = null;
		Session session = null;
		Destination destination = null;
		MessageProducer producer = null;
		Message message = null;
		try{
			factory = new ActiveMQConnectionFactory("guest","guest","tcp://192.168.0.32:61616");
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("test-listener");
			producer = session.createProducer(destination);
			connection.start();
			for(int i=0;i<100;i++){
				message = session.createObjectMessage("���ǵ�"+i+"����Ϣ����Ϣ������:"+obj);
				producer.send(message);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(producer != null){//������Ϣ������
				try {
					producer.close();
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
	}


	public static void main(String[] args){
		ProducerSend producer = new ProducerSend();
		producer.sendMessage("this is message!!!");
				
	}
	

}
