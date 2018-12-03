package com.rabbitmq.queuedevploper;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RabbitProducer {
    @Autowired
    public    AmqpTemplate amqpTemplate;

    public  void send(){
        amqpTemplate.convertAndSend("exchange", "yinhai", new User("罗叶能", 24), new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setCorrelationId(UUID.randomUUID().toString()+"");
                message.getMessageProperties().setHeader("123",456);
//                message.getMessageProperties().setExpiration("1000");
//                //设置消息持久化
//                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
//                message.getMessageProperties().setReplyTo("");
               return message;
            }
        });
    }
     public void sendFanout(){
         amqpTemplate.convertAndSend("fanoutExchange","yinhai",new User("尹海",24));
     }
}
