package com.rabbitmq.queuedevploper;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: KafkaConsumer2
 * @Description: TODO
 * @Author: yinhai
 * @Date: 2019/3/15 17:00
 * @Version 1.0
 **/
//指定topic的消费
@Component
@KafkaListener(id = "topic-kafkaTest" , topicPartitions =
        { @TopicPartition(topic = "topic-kafkaTest1", partitions = { "0", "1" }),
                @TopicPartition(topic = "topic-kafkaTest2", partitions = "0")
        },containerFactory = "containerFactory")
public class KafkaConsumer2 {
    Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    @KafkaHandler
    public String listen(List<String> foo) {
       logger.info("-----------实验2收到的结果为:"+ JSONObject.toJSONString(foo));
       return "接收成功";

    }
}