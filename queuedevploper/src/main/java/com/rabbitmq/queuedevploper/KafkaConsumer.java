package com.rabbitmq.queuedevploper;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @KafkaListener(topics = "foo" ,groupId = "group2")
    @SendTo
    public void getMessage(String message){
        System.out.print("consumer收到的消息"+message);
    }
}
