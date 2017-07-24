package com.test.rabbit;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MessageProducer {
	private static String QUEUE_NAME = "QUEUE_NO_1";

	public void publishMessage() throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		String message = "Hello Baganis";
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		System.out.println("sent message");
		
		channel.close();
		connection.close();
	}

	public static void main(String[] args) {
		MessageProducer producer = new MessageProducer();
		try {
			producer.publishMessage();
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}

}
