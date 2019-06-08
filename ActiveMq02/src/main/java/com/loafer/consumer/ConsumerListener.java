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
 * ʹ�ü������ķ�ʽ��ʵ����Ϣ�Ĵ������ѡ�
 * @author ����
 *
 */
public class ConsumerListener {
	
	/**
	 * ������Ϣ
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
			//ע���������ע��ɹ��󣬶����е���Ϣ�仯���Զ��������������롣������Ϣ������
			consumer.setMessageListener(new MessageListener(){
				/*
				 *������һ��ע�ᣬ������Ч��
				 *����-consumer�̲߳��ر�
				 *������Ϣ�ķ�ʽ��ֻҪ����Ϣδ�����Զ�����onMessage������������Ϣ
				 *����������ע�����ɡ�ע�������������൱�ڼ�Ⱥ
				 *ActiveMQ�Զ���ѭ�����ö������������������е���Ϣ��ʵ�ֲ��д���
				 *
				 * ������Ϣ�ķ��������Ǽ�������
				 * �������¼��ǣ���Ϣ����Ϣδ����
				 * Ҫ����ľ������ݣ���Ϣ����
				 * @param message-δ�������Ϣ
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
			//������ǰ���롣��֤listener����δ�����������������ˣ��������Զ��ر�
			System.in.read();
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
	}


	public static void main(String[] args){
		ConsumerListener listener = new ConsumerListener();
		listener.consumMessage();
	}
	
}
