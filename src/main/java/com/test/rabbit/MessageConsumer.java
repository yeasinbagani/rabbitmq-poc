package com.test.rabbit;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class MessageConsumer {

	private static String QUEUE_NAME = "QUEUE_NO_1";

	public void consumeMessage() throws IOException, TimeoutException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body);
				System.out.println(message);
			}

		};

		channel.basicConsume(QUEUE_NAME, true, consumer);
	}

	public static void main(String[] args) {
		MessageConsumer consumer = new MessageConsumer();
		try {
			consumer.consumeMessage();
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}
}
