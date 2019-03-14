package com.rabbitmq.queuedevploper;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @KafkaListener(topics = "01343" ,groupId = "group2")
    @SendTo
    public void getMessage(String message){
        System.out.print("consumer收到的消息"+message);
    }

    @KafkaListener(topics = "01343" ,groupId = "group2")
    @SendTo
    public void getMessage2(String message){
        System.out.print("-------consumer2收到的消息"+message);
    }
}
