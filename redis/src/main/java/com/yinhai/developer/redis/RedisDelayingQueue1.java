package com.yinhai.developer.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.UUID;

/**
 * @ClassName: RedisDelayingQueue1
 * @Description: TODO
 * @Author: yinhai
 * @Date: 2018/11/21 15:14
 * @Version 1.0
 **/
public class RedisDelayingQueue1<T>{


    private final Logger logger = Logger.getLogger(RedisDelayingQueue1.class);
   static class TaskItem<T>{
        public String id;

        public T msg;

   }

    private String queueKey;

    private Jedis jedis;

    public RedisDelayingQueue1(String queueKey, Jedis jedisClient){
        this.queueKey = queueKey;
        this.jedis = jedisClient;
    }
    private Type type = new TypeReference<TaskItem<T>>(){}.getType();

   public void delay(T msg){
        String id = UUID.randomUUID().toString();
        TaskItem<T> taskItem = new TaskItem<>();
        taskItem.id = id;
        taskItem.msg = msg;
        jedis.zadd(queueKey,System.currentTimeMillis() + 5000, JSONObject.toJSONString(taskItem));
   }
   public void loop(){
       while (!Thread.interrupted()){
          Set<String> values = jedis.zrangeByScore(queueKey,0,System.currentTimeMillis(),0,1);
           if(values.isEmpty()){
               try {
                   Thread.sleep(500);
               }catch (Exception e){
                     break;
               }
               continue;
           }
           String s = values.iterator().next();
           if(jedis.zrem(queueKey,s)>0){//抢到了
               TaskItem<T> taskItem = JSON.parseObject(s,type);
               handleMsg(taskItem.msg);
           }
       }
   }
    public void handleMsg(T msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        jedis.auth("yinhai");
        RedisDelayingQueue1<String> queue = new RedisDelayingQueue1<String>( "q-demo",jedis);
        Thread producer = new Thread() {

            public void run() {
                for (int i = 0; i < 10; i++) {
                    queue.delay("codehole" + i);
                }
            }

        };
        Thread consumer = new Thread() {

            public void run() {
                queue.loop();
            }

        };
        producer.start();
        consumer.start();
        try {
            producer.join();
            Thread.sleep(6000);
            consumer.interrupt();
        } catch (InterruptedException e) {
        }
    }
}