package com.foursoft.gpa.clientfx;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.foursoft.gpa.clientfx.controller.GpaRequestLogController;

public class GpaRequestLogConsumer implements MessageListener,ExceptionListener {

	
	public static final String TOPIC_NAME="GPA_REQUESTS_TOPIC";
	
	private static int ackMode;
 
    private boolean transacted = false;
 
    static {
        ackMode = Session.AUTO_ACKNOWLEDGE;
    }
 
    private GpaRequestLogController controller;


	public GpaRequestLogConsumer(GpaRequestLogController controller){
		this.controller=controller;
		 ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("failover:tcp://localhost:61616");
	        Connection connection;
	        try {
	            connection = connectionFactory.createConnection();
	            connection.start();
	            Session session = connection.createSession(transacted, ackMode);
	 
	            //Create a temporary queue that this client will listen for responses on then create a consumer
	            //that consumes message from this temporary queue...for a real application a client should reuse
	            //the same temp queue for each message to the server...one temp queue per client
	            Destination destination = session.createTopic(TOPIC_NAME);
	            MessageConsumer responseConsumer = session.createConsumer(destination);
	 
	            //This class will handle the messages to the temp queue as well
	            responseConsumer.setMessageListener(this);
	 
	        } catch (JMSException e) {
	            e.printStackTrace();
	        }
	  }
	 
	
	
	@Override
	public void onMessage(Message message) {
		 String messageText = null;
	        try {
	            if (message instanceof TextMessage) {
	                TextMessage textMessage = (TextMessage) message;
	                messageText = textMessage.getText();
	                System.out.println("messageText = " + messageText);
	                controller.populateGridData();
	            }
	        } catch (JMSException e) {
	            //Handle the exception appropriately
	        }

		
	}
	

	@Override
	public void onException(JMSException e) {
		 e.printStackTrace();
		
	}

}
