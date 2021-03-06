package com.rabbitmq.queuedevploper;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: KafkaConfig
 * @Description: TODO kafka config
 * @Author: yinhai
 * @Date: 2019/1/28 9:43
 * @Version 1.0
 **/
@Configuration
public class KafkaConfig {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);
//    @Bean(name = "KafkaContainer")
//    public KafkaMessageListenerContainer  getKafkaMessagerListener(){
//        ContainerProperties containerProperties = new ContainerProperties("foo","kafkaTopic2");
//        containerProperties.setMessageListener(new MessageListener<String,String>() {
//            @Override
//            public void onMessage(ConsumerRecord<String, String> data) {
//                logger.info("收到的主题名"+data.topic()+"偏移量为"+data.offset()+"分区为"+data.partition()+"收到的数据为:"+data.value());
//            }
//        });
//        ConsumerFactory<String,String> consumerFactory = new DefaultKafkaConsumerFactory<String, String>(consumerProps());
//        KafkaMessageListenerContainer<String,String> kafkaMessageListenerContainer = new KafkaMessageListenerContainer<String, String>(consumerFactory,containerProperties);
//        return kafkaMessageListenerContainer;
//    }
//    @Bean
//    public KafkaTemplate<String, String> createTemplate() {
//        Map<String, Object> senderProps = senderProps();
//        KafkaTemplate<String, String> template = new KafkaTemplate<String,String>(producerFactory());
//        return template;
//    }

    private Map<String, Object> senderProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "39.107.90.7:9092");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.RETRIES_CONFIG,1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }
    private Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "39.107.90.7:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }
//创建主题用
    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
                StringUtils.arrayToCommaDelimitedString(new String[]{"39.107.90.7:9092","39.107.90.7:9093"}));
        return new KafkaAdmin(configs);
    }

//    @Bean
//    public KafkaListenerContainerFactory<?> batchFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        ConsumerFactory<String,String> consumerFactory = new DefaultKafkaConsumerFactory<String, String>(consumerProps());
//        factory.setConsumerFactory(consumerFactory);
//        factory.setBatchListener(true);
//        return factory;
//    }

    @Bean
    public AdminClient adminClient() {
        return AdminClient.create(admin().getConfig());
    }
//    @Bean
//    public NewTopic topic1() {
//        return new NewTopic("foo", 2, (short) 2);
//    }
//
//    @Bean
//    public NewTopic topic2() {
//        return new NewTopic("bar", 2, (short) 2);
//    }
    @Bean
    public NewTopic topic1() {
        return new NewTopic("01343", 2, (short) 2);
    }
//    @Bean
//    public ApplicationRunner runner(ReplyingKafkaTemplate<String, String, String> template) {
//        return args -> {
//            ProducerRecord<String, String> record = new ProducerRecord<>("kRequests", "foo");
//            RequestReplyFuture<String, String, String> replyFuture = template.sendAndReceive(record);
//            SendResult<String, String> sendResult = replyFuture.getSendFuture().get();
//            System.out.println("Sent ok: " + sendResult.getRecordMetadata());
//            ConsumerRecord<String, String> consumerRecord = replyFuture.get();
//            System.out.println("Return value: " + consumerRecord.value());
//        };
//    }

    @Bean
    public ReplyingKafkaTemplate<String, String, String> replyingTemplate(
           ProducerFactory producerFactory ) {
        ConcurrentKafkaListenerContainerFactory<String, String> containerFactory =  new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(new DefaultKafkaConsumerFactory<String,String >(consumerProps()));
        ConcurrentMessageListenerContainer<String, String> repliesContainer =
                containerFactory.createContainer("replies");
        repliesContainer.getContainerProperties().setGroupId("repliesGroup");
        repliesContainer.setAutoStartup(false);
        return new ReplyingKafkaTemplate<String,String,String>(producerFactory, repliesContainer);
    }

    //批量消费需要在factory 中设置batchListener 为true
    @Bean(name = "containerFactory")
    @ConditionalOnBean(name = "replyingTemplate")
    public ConcurrentKafkaListenerContainerFactory containerFactory(ReplyingKafkaTemplate<String,String,String>  replyingKafkaTemplate){
        ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory();
        concurrentKafkaListenerContainerFactory.setBatchListener(true);
        concurrentKafkaListenerContainerFactory.setConsumerFactory(new DefaultKafkaConsumerFactory(consumerProps()));
        concurrentKafkaListenerContainerFactory.setReplyTemplate(replyingKafkaTemplate);
        return concurrentKafkaListenerContainerFactory;
    }



    @Bean
    public NewTopic topic3() {
        return new NewTopic("topic-kafkaTest2", 2, (short) 2);
    }

    @Bean
    public NewTopic topic4(){
        return new NewTopic("topic-kafkaTest1", 2, (short) 2);
    }

//
//    //配置事务用
//    @Bean
//    public ProducerFactory<String, String> producerFactory() {
//        DefaultKafkaProducerFactory factory = new DefaultKafkaProducerFactory<>(senderProps());
//        factory.transactionCapable();
//        factory.setTransactionIdPrefix("tran-");
//        return factory;
//    }

    //好像事务和@SendTo不能共存
//   //配置事务用 KafkaTemplate和 TransactionManager用一个ProducerFactory就行
//    @Bean
//    public KafkaTransactionManager transactionManager(ProducerFactory producerFactory) {
//        KafkaTransactionManager manager = new KafkaTransactionManager(producerFactory);
//        return manager;
//    }
}