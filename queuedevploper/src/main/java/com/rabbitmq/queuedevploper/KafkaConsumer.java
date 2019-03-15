package com.rabbitmq.queuedevploper;

import com.alibaba.fastjson.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaConsumer {
    @KafkaListener(topics = "01343" ,groupId = "group2" , containerFactory = "containerFactory")
    @SendTo
    public void getMessage(List<String> message){
        System.out.print("consumer收到的消息"+ JSONObject.toJSONString(message));
    }

    @KafkaListener(topics = "01343" ,groupId = "group2",concurrency = "3"  , containerFactory = "containerFactory")
    @SendTo
    public void getMessage2(List<String> message){
        System.out.print("-------consumer2收到的消息"+JSONObject.toJSONString(message));
    }
}
