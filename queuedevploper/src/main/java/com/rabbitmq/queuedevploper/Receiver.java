package com.rabbitmq.queuedevploper;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class Receiver {

    @RabbitListener(queues = {"queue"})
    public void on(Message message, Channel channel, @Headers Map<String,Object> map) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        System.out.print("收到："+new String(message.getBody()));
    }

    @RabbitListener(queues = {"queue2"})
    public void on2(Message message, Channel channel, @Headers Map<String,Object> map) throws IOException {
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        System.out.print("收到2："+new String(message.getBody()));
        System.out.print(message.getMessageProperties().getCorrelationId());
    }


}