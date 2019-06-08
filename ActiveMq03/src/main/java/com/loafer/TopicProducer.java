package com.loafer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;


/**
 * ������Ϣ��ActiveMQ�У��������Ϣ����Ϊ������Ϣ
 * ����JMS��ش����У�ʹ�õĽӿ����Ͷ���javax.jms���µ�����
 * @param datas ��Ϣ����
 */
public class TopicProducer {
	
	/**
	 * ������Ϣ��ActiveMQ�У��������Ϣ����Ϊ������Ϣ
	 * ����JMS��ش����У�ʹ�õĽӿ����Ͷ���javax.jms���µ�����
	 * @param datas ��Ϣ����
	 */
	public void sendTextMessage(String datas){
		//���ӹ���
		ConnectionFactory factory = null;
		//����
		Connection connection = null;
		//Ŀ�ĵ�
		Destination destination = null;
		//�Ự
		Session session = null;
		//��Ϣ������
		MessageProducer producer = null;
		//��Ϣ����
		Message message = null;
		
		try{
			//�������ӹ���������ActiveMQ��������ӹ���
			//�������������췽���������������ֱ����û��������룬���ӵ�ַ
			//�޲ι��죬��Ĭ�ϵ����ӵ�ַ����������
			//���������죬������֤ģʽ��û���û�����֤
			//���������죬����֤+ָ����ַ��Ĭ�϶˿���61616.��ActiveMQ��conf/activemq.xml�����ļ��в鿴
			factory = new ActiveMQConnectionFactory("admin","admin","tcp://192.168.0.32:61616");
			//ͨ���������������Ӷ���
			//�������ӵķ��������أ�������createConnection(String username,String password)
			//�����ٴ������ӹ���ʱ��ֻ�������ӵ�ַ���������û���Ϣ
			connection = factory.createConnection();
			//�����������ӣ���Ϣ�ķ����߲��Ǳ����������ӡ���Ϣ�������߱�����������
			//producer�ٷ�����Ϣ��ʱ�򣬻����Ƿ����������ӣ����δ�������Զ�����
			//�������������ã�����������Ϻ�����������
			connection.start();
			//ͨ�����Ӷ��󣬴����Ự����
			/*�����Ự��ʱ�򣬱��봫�������������ֱ������Ƿ�֧����������ȷ����Ϣ����
			 * transacted:�Ƿ�֧����������������boolean.true-֧��  false-��֧��
			 * 		true:֧�����񣬵ڶ���������producer��˵Ĭ������Ч�����鴫�ݵ�������Session.SESSION_TRANSACTED
			 * 		false:��֧�����񣬳��ò������ڶ����������봫�ݣ��ұ�����Ч
			 * acknowledgeMode:���ȷ����Ϣ�Ĵ���ʹ��ȷ�ϻ���ʵ�ֵ�
			 * 		AUTO_ACKNOWLEDGE:�Զ�ȷ����Ϣ����Ϣ�������ߴ�����Ϣ���Զ�ȷ�ϡ����á���ҵ�������Ƽ�
			 * 		CLIENT_ACKNOWLEDGE:�ͻ����ֶ�ȷ�ϡ���Ϣ�������ߴ���󣬱����ֹ�ȷ��
			 * 		DUPS_OK_ACKNOWLEDGE:�и����Ŀͻ����ֶ�ȷ��
			 * 				һ����Ϣ���Զ�δ���
			 * 				���Խ���Session�����ģ��ٿ��������ظ���Ϣʱʹ�ã����Ƽ�ʹ�ã�
			 */
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			//��������Ŀ�ĵء�������Ŀ�ĵ����ơ���Ŀ�ĵص�Ψһ���
			
			destination = session.createTopic("topicDemo");
			//ͨ���Ự���󣬴�����Ϣ�ķ�����producer
			//��������Ϣ�����ߣ����͵���Ϣһ����ָ����Ŀ�ĵ���
			//����producer��ʱ�򣬿��Բ��ṩĿ�ĵء��ڷ�����Ϣ��ʱ���ƶ�Ŀ�ĵ�
			producer = session.createProducer(destination);
			//�����ı���Ϣ������Ϊ�����������ݵ�����
			message = session.createTextMessage(datas);
			//ʹ��producer,������Ϣ��ActiveMQ�е�Ŀ�ĵء������Ϣ����ʧ�ܡ��׳��쳣
			producer.send(message);
			
			System.out.println("��Ϣ�Ѿ����ͳɹ�....");
			
			
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
		System.out.println("�����߳���ʼ����.....");
		TopicProducer producer = new TopicProducer();
		producer.sendTextMessage("����һ�����Ե�ActiveMQ...");
		System.out.println("�����߳����Ѿ��ر�.....");
	}


}
