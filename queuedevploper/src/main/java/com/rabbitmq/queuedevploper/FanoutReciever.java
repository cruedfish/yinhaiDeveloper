package com.rabbitmq.queuedevploper;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FanoutReciever implements Runnable{
    private static RabbitTemplate amqpTemplate;
    @Autowired
    private void setAmqpTemplate(RabbitTemplate amqpTemplate){
        FanoutReciever.amqpTemplate = amqpTemplate;
    }
    @Override
    public void run() {
        amqpTemplate.execute(new BasicGet("fanout"));

    }
}
