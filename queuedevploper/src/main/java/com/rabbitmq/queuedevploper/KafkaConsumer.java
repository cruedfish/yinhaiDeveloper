package com.rabbitmq.queuedevploper;

import com.alibaba.fastjson.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class KafkaConsumer {
    @KafkaListener(topics = "01343" ,groupId = "group2" , containerFactory = "containerFactory")
    @SendTo
    @Transactional
    public String getMessage(List<String> message){
        System.out.print("consumer收到的消息"+ JSONObject.toJSONString(message));
        return "接受成功";
    }

    @KafkaListener(topics = "01343" ,groupId = "group2",concurrency = "3"  , containerFactory = "containerFactory")
    @SendTo("topic-kafkaTest1")
    @Transactional
    public String getMessage2(List<String> message){
        System.out.print("-------consumer2收到的消息"+JSONObject.toJSONString(message));
        return "接受成功";
    }
}
