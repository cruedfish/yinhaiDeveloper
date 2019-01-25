package com.rabbitmq.queuedevploper;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

/**
 * @ClassName: KafkaProducer
 * @Description: TODO
 * @Author: yinhai
 * @Date: 2019/1/25 15:23
 * @Version 1.0
 **/
@Component
public class KafkaProducer {
    @Autowired
    public  KafkaTemplate<String,String> kafkaTemplate;

    public  void send(){
        ProducerRecord<String,String> producerRecord = new ProducerRecord("kafTest",0,"test1","value1",null);
        kafkaTemplate.send(producerRecord).addCallback(new ListenableFutureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("发送失败");
            }

            @Override
            public void onSuccess(Object result) {
              System.out.println("发送成功");
            }
        });
    }

}