package com.rabbitmq.queuedevploper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static Logger log = LoggerFactory.getLogger(RabbitMqConfig.class);

    @Bean
    public Queue helloQueue() {
        return new Queue("queue");
    }

    @Bean
    public Queue helloQueue3() {
        return new Queue("queue2");
    }
//在参数内设置 "x-message-ttl"设置过期时间 ,设置 x-dead-letter-exchange 可以设置死信队列，x-max-priority设置最大优先级
    @Bean
    public Queue helloQueue2() {
        return new Queue("fanout");
    }
//在参数内可以设置备份交换器  alternate-exchange 属性设置备份交换器
    @Bean
    public DirectExchange helloExechange(){
        return new DirectExchange("exchange");
    }

    @Bean
    public FanoutExchange helloFanout(){
        return new FanoutExchange("fanoutExchange");
    }
    @Bean
    public Binding binding2(){
        return BindingBuilder.bind(helloQueue()).to(helloExechange()).with("luoye");
    }
    @Bean
    public Binding binding3(){
        return BindingBuilder.bind(helloQueue3()).to(helloExechange()).with("luoye");
    }
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(helloQueue2()).to(helloFanout());
         }

     @Bean
    public AmqpTemplate amqpTemplate(){
//          使用jackson 消息转换器
         rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
         rabbitTemplate.setEncoding("UTF-8");
//        开启returncallback     yml 需要 配置    publisher-returns: true
         rabbitTemplate.setMandatory(true);
         rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
             String correlationId = message.getMessageProperties().getCorrelationId();
             log.debug("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
         });
         //        消息确认  yml 需要配置   publisher-returns: true
         rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
             if (ack) {
                 log.debug("消息发送到exchange成功,id: :");
             } else {
                 log.debug("消息发送到exchange失败,原因: {}", cause);
             }
         });
         return rabbitTemplate;
     }
}