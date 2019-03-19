package com.rabbitmq.queuedevploper;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
    public ReplyingKafkaTemplate<String,String,String> kafkaTemplate;
    @Transactional
    public  void send() throws Exception{
        ProducerRecord<String,String> producerRecord = new ProducerRecord("01343",0,"test测试数据","test测试数据",null);
        RequestReplyFuture<String, String, String> sendAndReceive = kafkaTemplate.sendAndReceive(producerRecord);
        SendResult<String, String> sendResult = sendAndReceive.getSendFuture().get();

        //打印结果记录中所有头部信息 会看到Spring自动生成的相关ID，这个ID是由消费端@SendTo 注释返回的值。
        Headers headers = sendResult.getProducerRecord().headers();
        headers.forEach(header -> System.out.println(JSONObject.toJSONString(header.key().toString()) + ":" + JSONObject.toJSONString(header.value().toString()) ));


//
//        for(int i = 0 ;i <200 ;i++){
//            ProducerRecord<String,String> producerRecord2 = new ProducerRecord("topic-kafkaTest1",0,"test"+i,"test"+i,null);
//            kafkaTemplate.send(producerRecord).addCallback(new ListenableFutureCallback() {
//                @Override
//                public void onFailure(Throwable ex) {
//                    System.out.println("kafkaTest1发送失败");
//                }
//
//                @Override
//                public void onSuccess(Object result) {
//                    System.out.println("kafkaTest1发送成功");
//                }
//            });
//        }
//
//        for(int i = 0 ;i <200 ;i++){
//            ProducerRecord<String,String> producerRecord3 = new ProducerRecord("topic-kafkaTest2",0,"hehe"+i,"hehe"+i,null);
//            kafkaTemplate.send(producerRecord).addCallback(new ListenableFutureCallback() {
//                @Override
//                public void onFailure(Throwable ex) {
//                    System.out.println("kafkaTest2发送失败");
//                }
//
//                @Override
//                public void onSuccess(Object result) {
//                    System.out.println("kafkaTest2发送成功");
//                }
//            });
//        }
//
//        for(int i = 0 ;i <200 ;i++){
//            ProducerRecord<String,String> producerRecord4 = new ProducerRecord("topic-kafkaTest2",1,"haha"+i,"haha"+i,null);
//            kafkaTemplate.send(producerRecord).addCallback(new ListenableFutureCallback() {
//                @Override
//                public void onFailure(Throwable ex) {
//                    System.out.println("kafkaTest2发送失败");
//                }
//
//                @Override
//                public void onSuccess(Object result) {
//                    System.out.println("kafkaTest2发送成功");
//                }
//            });
//        }
//
//        for(int i = 0 ;i <200 ;i++){
//            ProducerRecord<String,String> producerRecord5 = new ProducerRecord("topic-kafkaTest1",1,"didi"+i,"didi"+i,null);
//            kafkaTemplate.send(producerRecord).addCallback(new ListenableFutureCallback() {
//                @Override
//                public void onFailure(Throwable ex) {
//                    System.out.println("kafkaTest2发送失败");
//                }
//
//                @Override
//                public void onSuccess(Object result) {
//                    System.out.println("kafkaTest2发送成功");
//                }
//            });
//        }
//        //发送到两个不同的分区  ，kafka会根据键值来计算不同的hash值进行hash分片  路由到特定的partition上
//        ProducerRecord<String,String> producerRecord = new ProducerRecord("01343",0,"test1","test1",null);
//        ProducerRecord<String,String> producerRecord2 = new ProducerRecord("01343",1,"test2","test2",null);
//        ProducerRecord<String,String> producerRecord3 = new ProducerRecord("01343",1,"test3","test3",null);
//
//        kafkaTemplate.send(producerRecord).addCallback(new ListenableFutureCallback() {
//            @Override
//            public void onFailure(Throwable ex) {
//                System.out.println("发送失败");
//            }
//
//            @Override
//            public void onSuccess(Object result) {
//              System.out.println(result);
//            }
//        });
//        kafkaTemplate.send(producerRecord2).addCallback(new ListenableFutureCallback() {
//            @Override
//            public void onFailure(Throwable ex) {
//                System.out.println("发送失败");
//            }
//
//            @Override
//            public void onSuccess(Object result) {
//                System.out.println(result);
//            }
//        });
//        kafkaTemplate.send(producerRecord3).addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
//            @Override
//            public void onSuccess(SendResult<String, String> result) {
//                System.out.println("发送成功");
//            }
//
//            @Override
//            public void onFailure(Throwable ex) {
//                System.out.println("发送失败");
//            }
//
//        });
    }

}