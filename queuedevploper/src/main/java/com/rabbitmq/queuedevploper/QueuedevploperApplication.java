package com.rabbitmq.queuedevploper;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QueuedevploperApplication {

	public static void main(String[] args) {
		SpringApplication.run(QueuedevploperApplication.class, args);
	}

}
